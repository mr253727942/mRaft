package com.mraft.core.processor;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.BizCode;
import com.mraft.common.client.EchoBody;
import com.mraft.common.protocol.Heartbeat;
import com.mraft.common.store.DistributeDataStore;
import com.mraft.remote.handler.BizProcessor;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wenan.mr on 2017/12/4.
 *
 * @author wenan.mr
 * @date 2017/12/04
 */
public class HeartBeatProcessor implements BizProcessor{
    @Override
    public BaseTransferBody process(BaseTransferBody baseTransferBody, ChannelHandlerContext ctx) {
        BaseTransferBody response = new EchoBody();
        if(baseTransferBody != null && baseTransferBody instanceof Heartbeat){
            try{
                Heartbeat heartbeat = (Heartbeat) baseTransferBody;
                heartbeat.setBizCode(BizCode.HEATBEAT.getBizCode());
                DistributeDataStore.heartbeatLinkedBlockingQueue.put(heartbeat);
                return null;
            }catch (Exception e){

            }

        }
        return null;
    }
}
