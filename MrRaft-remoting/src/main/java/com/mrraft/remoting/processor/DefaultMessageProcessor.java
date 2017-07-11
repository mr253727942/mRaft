package com.mrraft.remoting.processor;

import com.mrraft.remoting.protocol.RemoteObject;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by T460P on 2017/6/26.
 */
public class DefaultMessageProcessor implements MessageProcessor{


    @Override
    public void process(ChannelHandlerContext ctx, RemoteObject remoteObject) {
        System.out.print(remoteObject);
    }
}
