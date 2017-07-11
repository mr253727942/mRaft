package com.mrraft.remoting.netty;

import com.google.common.collect.MutableClassToInstanceMap;
import com.mrraft.remoting.handler.JdkPiplineServerHandler;
import com.mrraft.remoting.protocol.RpcSerailizeType;
import io.netty.channel.ChannelPipeline;

/**
 * Created by Ma.Rong on 2017/6/26.
 */
public class NettySerializerSelector {


    public NettySerializerSelector(){

    }

    private static MutableClassToInstanceMap<NettyPiplineHandler> handler = MutableClassToInstanceMap.create();

    static{
        handler.putInstance(JdkPiplineServerHandler.class,new JdkPiplineServerHandler());
    }

    public void selectProtocol(RpcSerailizeType rpcSerailizeType, ChannelPipeline channelPipeline){
        switch (rpcSerailizeType){
            case JDK:{
                handler.getInstance(JdkPiplineServerHandler.class).handle(channelPipeline);
                break;
            }


        }
    }
}
