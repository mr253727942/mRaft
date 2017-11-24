package com.mraft.core;

import java.net.InetAddress;
import java.util.List;

import com.google.common.collect.Lists;
import com.mraft.common.client.BizCode;
import com.mraft.core.leadership.MachineRole;
import com.mraft.core.processor.EchoProcessor;
import com.mraft.remote.main.NettyServer;

/**
 * Created by wenan.mr on 2017/11/23.
 *
 * @author wenan.mr
 * @date 2017/11/23
 */
public class MachineInit {

    List<InetAddress> machineIpList = Lists.newArrayList();

    private MachineRole machineRole;

    private Long lastLeaderElectTime;


    public void init() {
        NettyServer nettyServer = new NettyServer();
        nettyServer.registerProcessor(BizCode.ECHO.getBizCode(), new EchoProcessor());
        nettyServer.start();

        machineRole = MachineRole.FOLLOWER;
    }

}
