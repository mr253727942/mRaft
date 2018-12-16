package com.mraft.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Lists;
import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.BizCode;
import com.mraft.common.client.EchoBody;
import com.mraft.common.protocol.Heartbeat;
import com.mraft.common.protocol.HeatbeatResponse;
import com.mraft.common.protocol.VoteRequest;
import com.mraft.common.store.LogEntry;
import com.mraft.common.util.IpWrapper;
import com.mraft.core.Thread.HeatbeatThread;
import com.mraft.core.leadership.MachineRole;
import com.mraft.core.leadership.VoteProccessor;
import com.mraft.core.processor.EchoProcessor;
import com.mraft.core.processor.HeartBeatProcessor;
import com.mraft.remote.main.NettyClient;
import com.mraft.remote.main.NettyServer;
import sun.nio.ch.Net;

/**
 * Created by wenan.mr on 2017/11/23.
 *
 * @author wenan.mr
 * @date 2017/11/23
 */
public class MachineInit {


    //核心状态机驱动

    /**
     * 集群其他机器
     */
    private static List<IpWrapper> machineIpList = Lists.newArrayList();

    public static  IpWrapper leader;

    public static  List<LogEntry> logList = Lists.newArrayList();

    public static  long commitIndex;

    public static LinkedBlockingQueue<HeatbeatResponse> heatbeatResponseLinkedBlockingQueue = new LinkedBlockingQueue<>();

    public static NettyClient nettyClient;

    public static  MachineRole machineRole;

    public static AtomicLong  currentTermId = new AtomicLong(0L);



    public void init(IpWrapper ipWrapper) {
        NettyServer nettyServer = new NettyServer();
        NettyServer.registerProcessor(BizCode.ECHO.getBizCode(), new EchoProcessor());
        NettyServer.registerProcessor(BizCode.HEATBEAT.getBizCode(),new HeartBeatProcessor());
        NettyServer.registerProcessor(BizCode.VOTE.getBizCode(),new VoteProccessor());
        nettyServer.start(ipWrapper);

        machineRole = MachineRole.FOLLOWER;
        nettyClient = new NettyClient();
        nettyClient.start();
        Heartbeat request = new Heartbeat();
        request.setTerm(currentTermId.get());
        leader = MachineInit.getMachineIpList().get(0);
        request.setLeaderId((leader.getIp() + leader.getPort()).hashCode());
        request.setLeaderCommit(0L);
        request.setPrevLogIndex(0L);
        request.setPrevLogTerm(0L);

        HeatbeatThread heatbeatThread =  new HeatbeatThread();
        new Thread(heatbeatThread).start();

      /*  try{
            Thread.sleep(2000L);
            BaseTransferBody baseTransferBody = nettyClient.invokeSync(machineIpList.get(0),request,2000L);
            System.out.println(ipWrapper +"receive response |" + baseTransferBody);
        }catch (Exception e){
            e.printStackTrace();
        }
*/




    }

    public static void main(String[] args) throws UnknownHostException {
       String localIp = args[0];
       MachineInit machineInit = new MachineInit();
        MachineInit.getMachineIpList().add(IpWrapper.convert(args[1]));
        MachineInit.getMachineIpList().add(IpWrapper.convert(args[2]));
       System.out.print(MachineInit.getMachineIpList());

       machineInit.init(IpWrapper.convert(localIp));



    }

    public synchronized static  List<IpWrapper>  getMachineIpList() {
        return machineIpList;
    }

    public synchronized static   void setMachineIpList(List<IpWrapper> machineIpList) {
        machineIpList = machineIpList;
    }



    public IpWrapper getLeader() {
        return leader;
    }

    public void setLeader(IpWrapper leader) {
        MachineInit.leader = leader;
    }

    public static AtomicLong getCurrentTermId() {
        return currentTermId;
    }

    public static void setCurrentTermId(AtomicLong currentTermId) {
        MachineInit.currentTermId = currentTermId;
    }


}
