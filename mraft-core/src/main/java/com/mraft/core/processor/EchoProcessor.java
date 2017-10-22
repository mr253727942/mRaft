package com.mraft.core.processor;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.BizCode;
import com.mraft.common.client.EchoBody;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by T460P on 2017/10/8.
 */
public class EchoProcessor implements BizProcessor{
    @Override
    public void process(BaseTransferBody baseTransferBody, ChannelHandlerContext ctx) {
        if(baseTransferBody != null && baseTransferBody instanceof EchoBody){
            EchoBody echoBody = (EchoBody) baseTransferBody;
            System.out.println(echoBody);
            echoBody.setMsg("收到");
            ctx.writeAndFlush(echoBody);
        }
    }

    @Override
    public void register() {
        if(bizProcessorMap.get(BizCode.ECHO.getBizCode()) == null){
            bizProcessorMap.put(BizCode.ECHO.getBizCode(),new EchoProcessor());
        }
    }
}
