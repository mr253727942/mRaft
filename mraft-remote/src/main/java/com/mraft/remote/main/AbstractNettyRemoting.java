package com.mraft.remote.main;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.ResponseFuture;
import com.mraft.common.client.SerializeType;
import com.mraft.common.client.Transfertype;
import com.mraft.remote.handler.BizProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wenan.mr on 2017/11/26.
 *
 * @author wenan.mr
 * @date 2017/11/26
 */
public abstract class AbstractNettyRemoting {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNettyRemoting.class);


    protected final ConcurrentMap<Integer /* opaque */, ResponseFuture> responseTable =
        new ConcurrentHashMap<Integer, ResponseFuture>(256);



    public void processMessageReceived(ChannelHandlerContext ctx, BaseTransferBody msg) throws Exception {
        final BaseTransferBody cmd = msg;
        if (cmd != null) {
            switch (cmd.getTransfertype()) {
                case REQUEST:
                    processRequestCommand(ctx, cmd);
                    break;
                case RESPONSE:
                    processResponseCommand(ctx, cmd);
                    break;
                default:
                    break;
            }
        }
    }

    public void processRequestCommand(final ChannelHandlerContext ctx, final BaseTransferBody msg) {
        System.out.print("NettyServer handler client read");

        if(msg instanceof BaseTransferBody){
            BaseTransferBody baseTransferBody = (BaseTransferBody)msg;
            if(!NettyServer.bizProcessorMap.containsKey((baseTransferBody.getBizCode()))){
                logger.error("MraftServerHandler | can not get this bizCode | {}",new Object[]{(baseTransferBody.getBizCode())});
                return;
            }

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    int opaque = baseTransferBody.getOpaque();

                    try{
                        BizProcessor bizProcessor = NettyServer.bizProcessorMap.get(baseTransferBody.getBizCode());
                        BaseTransferBody response = bizProcessor.process((BaseTransferBody)msg,ctx);
                        if(response != null){
                            response.setOpaque(opaque);
                            response.setTransfertype(Transfertype.RESPONSE);
                            response.setSerializeType(SerializeType.JDK);
                            try{
                                ctx.writeAndFlush(response);
                            }catch (Exception e){
                                //todo log
                            }

                        }
                    }catch (Throwable e){
                        //todo 异常处理
                    }


                }
            };

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(r);

        }else{
            ctx.close();
        }
    }


    public void processResponseCommand(ChannelHandlerContext ctx,BaseTransferBody cmd) {
        final int opaque = cmd.getOpaque();
        final ResponseFuture responseFuture = responseTable.get(opaque);
        if (responseFuture != null) {
            responseFuture.setResponseCommand(cmd);

            responseFuture.release();

            responseTable.remove(opaque);


            responseFuture.putResponse(cmd);

        } else {
            //PLOG.warn("receive response, but not matched any request, " + RemotingHelper.parseChannelRemoteAddr(ctx.channel()));
            //PLOG.warn(cmd.toString());
        }
    }
}
