package com.mrraft.remoting.netty;

import io.netty.channel.ChannelPipeline;

/**
 * Created by T460P on 2017/6/26.
 */
public interface NettyPiplineHandler {

    void handle(ChannelPipeline pipeline);
}
