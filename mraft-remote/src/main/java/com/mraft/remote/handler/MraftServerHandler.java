package com.mraft.remote.handler;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.BizCode;
import com.mraft.common.client.EchoBody;
import com.mraft.remote.main.NettyServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by T460P on 2017/9/24.
 */
public class MraftServerHandler extends ChannelInboundHandlerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(MraftServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print("NettyServer handler Server read");
        if(msg instanceof BaseTransferBody){
            BaseTransferBody baseTransferBody = (BaseTransferBody)msg;
            if(!NettyServer.bizProcessorMap.containsKey((baseTransferBody.getBizCode()))){
                logger.error("MraftServerHandler | can not get this bizCode | {}",new Object[]{(baseTransferBody.getBizCode())});
                return;
            }

            BizProcessor bizProcessor = NettyServer.bizProcessorMap.get(baseTransferBody.getBizCode());
            BaseTransferBody response = bizProcessor.process((BaseTransferBody)msg,ctx);
            if(response != null){
                ctx.writeAndFlush(response);
            }


        }else{
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.print(cause.toString());
        logger.error("catch cause ",cause);
        cause.printStackTrace();

        ctx.close();
    }
}
