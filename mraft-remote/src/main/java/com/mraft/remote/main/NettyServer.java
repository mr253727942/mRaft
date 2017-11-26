package com.mraft.remote.main;

import java.util.concurrent.ConcurrentHashMap;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.util.IpWrapper;
import com.mraft.remote.handler.BizProcessor;
import com.mraft.remote.handler.MraftServerHandler;
import com.sun.xml.internal.rngom.parse.host.Base;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by T460P on 2017/10/8.
 */
public class NettyServer extends AbstractNettyRemoting{


    public static ConcurrentHashMap<Integer,BizProcessor> bizProcessorMap = new ConcurrentHashMap<>();



    public static void registerProcessor(int requestCode,BizProcessor bizProcessor){
        if(requestCode <= 0 || bizProcessor == null){
            return;
        }
        bizProcessorMap.put(requestCode,bizProcessor);
    }



    public void start(IpWrapper ipWrapper){
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
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));

                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new NettyServerHandler());
                        }


                    });

            ChannelFuture f = b.bind(ipWrapper.getIp(),ipWrapper.getPort()).sync();

            //f.channel().closeFuture().sync();


        }catch (Exception e){
            e.printStackTrace();
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        NettyServer nettyServer = new NettyServer();

    }

    class NettyServerHandler extends SimpleChannelInboundHandler<BaseTransferBody> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, BaseTransferBody msg) throws Exception {
            processMessageReceived(ctx, msg);
        }
    }



}
