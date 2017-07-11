package com.mrraft.remoting;

import io.netty.channel.Channel;

/**
 * Created by T460P on 2016/12/30.
 */
public interface ChannelEventListener {
    void onChannelConnect(final String remoteAddr,final Channel channel);

    void onChannelClose(final String remoteAddr,final Channel channel);

    void onChannelException(final String remoteAddr,final Channel channel);

    void onChannelIde(final String remoteAddr,final Channel channel);
}
