package com.mraft.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Lists;
import com.mraft.common.client.BizCode;
import com.mraft.common.util.IpWrapper;
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

    List<IpWrapper> machineIpList = Lists.newArrayList();


    private MachineRole machineRole;

    private Long lastLeaderElectTime;


    public void init(IpWrapper ipWrapper) {
        NettyServer nettyServer = new NettyServer();
        NettyServer.registerProcessor(BizCode.ECHO.getBizCode(), new EchoProcessor());
        nettyServer.start(ipWrapper);

        machineRole = MachineRole.FOLLOWER;


    }

    public static void main(String[] args) throws UnknownHostException {
       String localIp = args[0];
       MachineInit machineInit = new MachineInit();
       machineInit.getMachineIpList().add(IpWrapper.convert(args[1]));
       machineInit.getMachineIpList().add(IpWrapper.convert(args[2]));
       System.out.print(machineInit.getMachineIpList());

       machineInit.init(IpWrapper.convert(localIp));



    }

    public List<IpWrapper> getMachineIpList() {
        return machineIpList;
    }

    public void setMachineIpList(List<IpWrapper> machineIpList) {
        this.machineIpList = machineIpList;
    }

    public MachineRole getMachineRole() {
        return machineRole;
    }

    public void setMachineRole(MachineRole machineRole) {
        this.machineRole = machineRole;
    }
}
