package com.mrraft.remoting.handler;

import com.google.common.collect.Maps;
import com.mrraft.remoting.processor.DefaultMessageProcessor;
import com.mrraft.remoting.processor.MessageProcessor;
import com.mrraft.remoting.protocol.RemoteObject;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

/**
 * Created by Ma.Rong on 2017/6/26.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter{

    private Map<Integer,MessageProcessor> processorMap = Maps.newHashMap();




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server recieve");
        if(msg instanceof RemoteObject){
            RemoteObject remoteObject = (RemoteObject) msg;
            MessageProcessor messageProcessor = new DefaultMessageProcessor();
            messageProcessor.process(ctx,remoteObject);

        }else{
            ctx.close();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.print(cause);

        ctx.close();
    }
}
