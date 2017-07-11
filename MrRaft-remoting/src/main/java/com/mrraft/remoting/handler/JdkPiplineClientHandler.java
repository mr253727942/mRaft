package com.mrraft.remoting.handler;

import com.mrraft.remoting.netty.*;
import com.mrraft.remoting.protocol.NettyMsgCodeUtil;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by T460P on 2017/6/27.
 */
public class JdkPiplineClientHandler implements NettyPiplineHandler {
    @Override
    public void handle(ChannelPipeline pipeline) {
        System.out.print(3333);
        //pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, NettyMsgCodeUtil.MESSAGE_LENGTH, 0, NettyMsgCodeUtil.MESSAGE_LENGTH));
       // pipeline.addLast(new LengthFieldPrepender(NettyMsgCodeUtil.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new NettyClientHandler());

    }
}
