package com.mraft.core.Thread;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import com.mraft.common.client.BizCode;
import com.mraft.common.protocol.HeatbeatResponse;
import com.mraft.common.protocol.VoteRequest;
import com.mraft.common.util.IpWrapper;
import com.mraft.core.MachineInit;
import com.mraft.core.leadership.MachineRole;

/**
 * Created by wenan.mr on 2017/12/4.
 *
 * @author wenan.mr
 * @date 2017/12/04
 */
public class HeatbeatThread implements Runnable{

    @Override
    public void run() {
        while(true){
            try{

                Random r = new Random();
                //不阻塞
                HeatbeatResponse heatbeatResponse = MachineInit.heatbeatResponseLinkedBlockingQueue.poll(r.nextInt(1000),
                    TimeUnit.MILLISECONDS);
                //发起选举
                if(heatbeatResponse == null){
                    MachineInit.machineRole = MachineRole.CANDIDATE;



                    //给其他machine发消息发起投票
                    MachineInit.getMachineIpList().parallelStream().forEach(t->{
                        leaderOwnSelf(t);
                    });



                    //处理心跳
                }else{

                }

            }catch (Throwable e){
                System.out.println(JSON.toJSON(e));
            }
        }
    }

    /**
     * 选举自己
     */
    public void leaderOwnSelf(IpWrapper ipWrapper){
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setCandidateId(MachineInit.leader);
        voteRequest.setTerm(MachineInit.currentTermId.addAndGet(1));
        voteRequest.setLastLogIndex(MachineInit.commitIndex);
        if(MachineInit.logList.isEmpty()){
            voteRequest.setLastLogTerm(0L);
        }else{
            voteRequest.setLastLogTerm(MachineInit.logList.get(MachineInit.logList.size()-1).getLogTerm());
        }

        voteRequest.setBizCode(BizCode.VOTE.getBizCode());
        MachineInit.nettyClient.invokeSync(ipWrapper,voteRequest,2000);


    }
}
