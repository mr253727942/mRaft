package com.mraft.common.store;

import java.util.concurrent.LinkedBlockingQueue;

import com.mraft.common.protocol.Heartbeat;

/**
 * Created by wenan.mr on 2018/2/4.
 *
 * @author wenan.mr
 * @date 2018/02/04
 */
public class DistributeDataStore {

    public static LinkedBlockingQueue<Heartbeat> heartbeatLinkedBlockingQueue = new LinkedBlockingQueue<>();

}
