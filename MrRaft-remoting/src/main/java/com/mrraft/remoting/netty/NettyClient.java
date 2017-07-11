package com.mrraft.remoting.netty;

import com.mrraft.remoting.handler.JdkPiplineClientHandler;
import com.mrraft.remoting.handler.NettyClientHandler;
import com.mrraft.remoting.protocol.NettyMsgCodeUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Ma.Rong on 2017/6/25.
 */
public class NettyClient {

    public void connect(String host,int port){
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();

            b.group(workerGroup);

            b.channel(NioSocketChannel.class);

            b.option(ChannelOption.SO_KEEPALIVE,true);

            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, NettyMsgCodeUtil.MESSAGE_LENGTH, 0, NettyMsgCodeUtil.MESSAGE_LENGTH));
                    ch.pipeline().addLast(new LengthFieldPrepender(NettyMsgCodeUtil.MESSAGE_LENGTH));
                    ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        NettyClient client = new NettyClient();
        client.connect("127.0.0.1",1234);

    }
}
