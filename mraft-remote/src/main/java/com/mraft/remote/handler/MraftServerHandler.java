package com.mraft.remote.handler;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.BizCode;
import com.mraft.common.client.EchoBody;
import com.mraft.core.processor.BizProcessor;
import com.mraft.core.processor.EchoProcessor;
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
        System.out.print("client read");
        if(msg instanceof BaseTransferBody){
            if(((BaseTransferBody) msg).getBizCode() == BizCode.ECHO.getBizCode()){
                System.out.println(msg);
                EchoProcessor echoProcessor = new EchoProcessor();
                echoProcessor.process((EchoBody)msg,ctx);
            }


        }else{
            ctx.close();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.print(cause.toString());
        logger.error("catch cause ",cause);
        cause.printStackTrace();

        ctx.close();
    }
}
