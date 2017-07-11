package com.mrraft.remoting.netty;

import com.mrraft.remoting.handler.NettyServerHandler;
import com.mrraft.remoting.protocol.NettyMsgCodeUtil;
import com.mrraft.remoting.protocol.RpcSerailizeType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Ma.Rong on 2017/6/25.
 */
public class NettyServer {

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                   /* .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_BACKLOG,1024)*/
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, NettyMsgCodeUtil.MESSAGE_LENGTH, 0, NettyMsgCodeUtil.MESSAGE_LENGTH));
                            pipeline.addLast(new LengthFieldPrepender(NettyMsgCodeUtil.MESSAGE_LENGTH));

                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new NettyServerHandler());
                        }


                    });

            ChannelFuture f = b.bind("127.0.0.1",1234).sync();

            f.channel().closeFuture().sync();


        }catch (Exception e){
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}
