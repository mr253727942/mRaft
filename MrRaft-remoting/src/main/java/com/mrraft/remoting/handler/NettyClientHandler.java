package com.mrraft.remoting.handler;

import com.mrraft.remoting.processor.DefaultMessageProcessor;
import com.mrraft.remoting.processor.MessageProcessor;
import com.mrraft.remoting.protocol.RemoteObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;

/**
 * Created by Ma.Rong on 2017/6/27.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
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
        if(msg instanceof RemoteObject){
            RemoteObject remoteObject = (RemoteObject) msg;
            MessageProcessor messageProcessor = new DefaultMessageProcessor();
            messageProcessor.process(ctx,remoteObject);

        }else{
            ctx.close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RemoteObject remoteObject = new RemoteObject();
        System.out.print("client active");
        remoteObject.setCode(100);
        remoteObject.setRemark("sss");
        remoteObject.setObj(new Integer(12222));
        ctx.write(remoteObject);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.print(cause);
    }
}
