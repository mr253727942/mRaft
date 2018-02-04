package com.mraft.core.processor;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.BizCode;
import com.mraft.common.client.EchoBody;
import com.mraft.remote.handler.BizProcessor;
import com.sun.xml.internal.rngom.parse.host.Base;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wenan.mr on 2017/10/8.
 */
public class EchoProcessor implements BizProcessor {
    @Override
    public BaseTransferBody process(BaseTransferBody baseTransferBody, ChannelHandlerContext ctx) {
        BaseTransferBody response = new EchoBody();
        if(baseTransferBody != null && baseTransferBody instanceof EchoBody){
            EchoBody echoBody = (EchoBody) baseTransferBody;
            echoBody.setBizCode(BizCode.ECHO.getBizCode());
            echoBody.setMsg("服务端收到");
            return echoBody;
        }else{
            response.setBizCode(BizCode.ERROR.getBizCode());
            return response;
        }


    }


}
