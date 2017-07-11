package com.mrraft.remoting.netty;

import io.netty.channel.Channel;

/**
 * Created by T460P on 2016/12/30.
 */
public class NettyEvent {
    private  NettyEventType type;
    private  String remoteAddr;
    private Channel channel;

    public NettyEvent(NettyEventType type, String remoteAddr, Channel channel) {
        this.type = type;
        this.remoteAddr = remoteAddr;
        this.channel = channel;
    }


    public NettyEventType getType() {
        return type;
    }


    public String getRemoteAddr() {
        return remoteAddr;
    }


    public Channel getChannel() {
        return channel;
    }


    @Override
    public String toString() {
        return "NettyEvent [type=" + type + ", remoteAddr=" + remoteAddr + ", channel=" + channel + "]";
    }

}
