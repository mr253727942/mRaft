package com.mraft.remote.main;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.ResponseFuture;
import com.mraft.common.client.Transfertype;
import com.mraft.common.util.ChannelWrapper;
import com.mraft.common.util.IpWrapper;
import com.mraft.remote.handler.MraftClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by wenan.mr on 2017/11/26.
 *
 * @author wenan.mr
 * @date 2017/11/26
 */
public class NettyClient extends AbstractNettyRemoting{

    Bootstrap bootstrap = new Bootstrap();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    private final Lock lockChannelTables = new ReentrantLock();
    public ConcurrentHashMap<IpWrapper,ChannelWrapper> channelTables = new ConcurrentHashMap<>();

    private static final long LOCK_TIMEOUT_MILLIS = 3000;


    public void start(){
        this.bootstrap.group(workerGroup).channel(NioSocketChannel.class)//
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_KEEPALIVE, false)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
            .option(ChannelOption.SO_SNDBUF, 1024)
            .option(ChannelOption.SO_RCVBUF, 1024)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                    ch.pipeline().addLast(new LengthFieldPrepender(4));
                    ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers
                        .cacheDisabled(this.getClass().getClassLoader())));
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            });
    }

    public BaseTransferBody invokeSync(final IpWrapper ipWrapper, final BaseTransferBody request,
                                       final long timeoutMillis){
        int opaque = BaseTransferBody.atomicInteger.addAndGet(1);
        request.setOpaque(opaque);
        request.setTransfertype(Transfertype.REQUEST);
        final Channel channel = this.getAndCreateChannel(ipWrapper);
        if(channel != null && channel.isActive()){
            try{
                ResponseFuture responseFuture = new ResponseFuture(opaque, timeoutMillis, null);
                this.responseTable.put(opaque,responseFuture);
                channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture f) throws Exception {
                        if (f.isSuccess()) {
                            responseFuture.setSendRequestOK(true);
                            return;
                        } else {
                            responseFuture.setSendRequestOK(false);
                        }

                        responseTable.remove(opaque);
                        responseFuture.setCause(f.cause());
                        responseFuture.putResponse(null);
                        //PLOG.warn("send a request command to channel <" + addr + "> failed.");
                    }
                });
                BaseTransferBody response = responseFuture.waitResponse(timeoutMillis);
                if (null == response) {
                    if (responseFuture.isSendRequestOK()) {
                        //throw new RemotingTimeoutException(RemotingHelper.parseSocketAddressAddr(addr), timeoutMillis,
                            //responseFuture.getCause());
                    } else {
                        //throw new RemotingSendRequestException(RemotingHelper.parseSocketAddressAddr(addr), responseFuture.getCause());
                    }
                }

                return response;

            }catch (Exception e){

            }finally {
                this.responseTable.remove(opaque);
            }
        }else{
            //关闭channel 后面再实现
            return null;
        }

        return null;

    }

    private Channel getAndCreateChannel(final IpWrapper ipWrapper){
        ChannelWrapper cw = this.channelTables.get(ipWrapper);
        if(cw != null && cw.isOK()){
            return cw.getChannel();
        }

        return this.createChannel(ipWrapper);
    }

    private Channel createChannel(IpWrapper ipWrapper){
        ChannelWrapper cw = this.channelTables.get(ipWrapper);
        if (cw != null && cw.isOK()) {
            return cw.getChannel();
        }
        try{
            //this.lockChannelTables.tryLock(LOCK_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
            if (true){
                try {
                    boolean createNewConnection = false;
                    cw = this.channelTables.get(ipWrapper);
                    if (cw != null) {

                        if (cw.isOK()) {
                            return cw.getChannel();
                        } else if (!cw.getChannelFuture().isDone()) {
                            createNewConnection = false;
                        } else {
                            this.channelTables.remove(ipWrapper);
                            createNewConnection = true;
                        }
                    } else {
                        createNewConnection = true;
                    }

                    if (createNewConnection) {
                        ChannelFuture channelFuture = this.bootstrap.connect(ipWrapper.getIp(),ipWrapper.getPort());
                        cw = new ChannelWrapper(channelFuture);
                        this.channelTables.put(ipWrapper, cw);
                    }
                } catch (Exception e) {
                } finally {
                    this.lockChannelTables.unlock();
                }
            } else {
                //log.warn("createChannel: try to lock channel table, but timeout, {}ms", LOCK_TIMEOUT_MILLIS);
            }

            if (cw != null) {
                ChannelFuture channelFuture = cw.getChannelFuture();
                if (channelFuture.awaitUninterruptibly(1000L)) {
                    if (cw.isOK()) {
                        //log.info("createChannel: connect remote host[{}] success, {}", ipWrapper, channelFuture.toString());
                        return cw.getChannel();
                    } else {
                        //log.warn("createChannel: connect remote host[" + addr + "] failed, " + channelFuture.toString(), channelFuture.cause());
                    }
                } else {
                    //log.warn("createChannel: connect remote host[{}] timeout {}ms, {}", addr, this.nettyClientConfig.getConnectTimeoutMillis(),
                        //channelFuture.toString();
                }
            }
        }catch (Exception e){

        }finally {
            //lockChannelTables.unlock();
        }


        return null;
    }


    class NettyClientHandler extends SimpleChannelInboundHandler<BaseTransferBody> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, BaseTransferBody msg) throws Exception {
            processMessageReceived(ctx, msg);
        }
    }


}
