package com.mrraft.remoting.netty;

import com.mrraft.remoting.protocol.RpcSerailizeType;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by T460P on 2017/6/27.
 */
public class NettyChannelInitalizer extends ChannelInitializer<SocketChannel>{

    private RpcSerailizeType rpcSerailizeType;

    private NettySerializerSelector nettySerializerSelector;

    NettyChannelInitalizer buildRpcType(RpcSerailizeType rpcSerailizeType){
        this.rpcSerailizeType = rpcSerailizeType;
        return this;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.print(123);
        ChannelPipeline pipeline = socketChannel.pipeline();
        nettySerializerSelector.selectProtocol(rpcSerailizeType,pipeline);
    }




}
