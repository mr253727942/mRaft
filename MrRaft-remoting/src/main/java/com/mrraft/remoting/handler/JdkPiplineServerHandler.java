package com.mrraft.remoting.handler;

import com.mrraft.remoting.netty.NettyPiplineHandler;
import com.mrraft.remoting.protocol.NettyMsgCodeUtil;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Ma.Rong on 2017/6/26.
 */
public class JdkPiplineServerHandler implements NettyPiplineHandler {
    public void handle(ChannelPipeline pipeline) {
        //pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, NettyMsgCodeUtil.MESSAGE_LENGTH, 0, NettyMsgCodeUtil.MESSAGE_LENGTH));
        //pipeline.addLast(new LengthFieldPrepender(NettyMsgCodeUtil.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        pipeline.addLast(new NettyServerHandler());
    }

}
