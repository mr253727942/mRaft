package com.mraft.core.processor;

import com.mraft.common.client.BaseTransferBody;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by T460P on 2017/10/8.
 */
public interface BizProcessor {

    ConcurrentHashMap<Integer,BizProcessor> bizProcessorMap = new ConcurrentHashMap<>();

    /**
     * 业务处理
     * @param baseTransferBody
     * @param ctx
     */
    void process(BaseTransferBody baseTransferBody, ChannelHandlerContext ctx );

    void register();

}
