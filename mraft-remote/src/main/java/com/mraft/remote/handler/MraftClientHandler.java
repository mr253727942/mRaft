package com.mraft.remote.handler;

import com.mraft.common.client.BizCode;
import com.mraft.common.client.EchoBody;
import com.mraft.common.client.SerializeType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;

/**
 * Created by MaRong on 2017/9/24.
 */
public class MraftClientHandler extends ChannelInboundHandlerAdapter{

   // private Logger logger = LoggerFactory.getLogger(MraftClientHandler.class);

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print("client read");
        EchoBody echoBody = new EchoBody();
        echoBody.setBizCode(BizCode.ECHO.getBizCode());
        echoBody.setSerializeType(SerializeType.JDK);
        long time = System.currentTimeMillis();
        echoBody.setMsg(time+"");
        ctx.writeAndFlush(echoBody);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        EchoBody echoBody = new EchoBody();
        echoBody.setBizCode(BizCode.ECHO.getBizCode());
        echoBody.setSerializeType(SerializeType.JDK);
        echoBody.setMsg("12321");
        System.out.print("client active");
        ctx.write(echoBody);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.print(cause);
    }

}
