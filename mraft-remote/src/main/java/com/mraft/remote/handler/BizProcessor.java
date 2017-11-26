package com.mraft.remote.handler;

import com.mraft.common.client.BaseTransferBody;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by T460P on 2017/10/8.
 */
public interface BizProcessor {



    /**
     * 业务处理
     * @param baseTransferBody
     * @param ctx
     */
    BaseTransferBody process(BaseTransferBody baseTransferBody, ChannelHandlerContext ctx );


}
