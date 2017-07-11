/*
package com.mrraft.remoting.netty;

import com.mrraft.remoting.ChannelEventListener;
import com.mrraft.remoting.common.Pair;
import com.mrraft.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

*/
/**
 * Created by T460P on 2016/12/30.
 *//*

public abstract  class NettyRemotingAbstract {
    private static final Logger plog = LoggerFactory.getLogger(RemotingHelper.RemotingLogName);

    //信号量
    protected  Semaphore semaphoreOneway = null ;


    protected  Semaphore semaphoreAsync = null;

    //requestid--> ResponseFuture
    protected final ConcurrentHashMap<Integer */
/* opaque *//*
, ResponseFuture> responseTable =
            new ConcurrentHashMap<Integer, ResponseFuture>(256);

    protected final HashMap<Integer*/
/* request code *//*
, Pair<NettyRequestProcessor, ExecutorService>> processorTable =
            new HashMap<Integer, Pair<NettyRequestProcessor, ExecutorService>>(64);

    */
/*protected final NettyEventExecuter nettyEventExecuter = new NettyEventExecuter();*//*


    protected Pair<NettyRequestProcessor, ExecutorService> defaultRequestProcessor;


    public NettyRemotingAbstract(final int permitsOneway, final int permitsAsync) {
        this.semaphoreOneway = new Semaphore(permitsOneway, true);
        this.semaphoreAsync = new Semaphore(permitsAsync, true);
    }

    public abstract ChannelEventListener getChannelEventListener();


}
*/
