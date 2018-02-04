package com.mraft.core.leadership;

import java.util.List;

import com.mraft.common.client.BizCode;
import com.mraft.common.protocol.Heartbeat;
import com.mraft.common.protocol.HeatbeatResponse;
import com.mraft.common.protocol.VoteRequest;
import com.mraft.common.protocol.VoteResponse;
import com.mraft.common.store.LogEntry;
import com.mraft.core.MachineInit;

/**
 * Created by wenan.mr on 2017/11/29.
 *
 * @author wenan.mr
 * @date 2017/11/29
 */
public class LeaderElection {

    public HeatbeatResponse ackHeatBeatRequest(Heartbeat heartbeat){
        HeatbeatResponse heatbeatResponse = new HeatbeatResponse();
        heatbeatResponse.setBizCode(BizCode.HEATBEAT.getBizCode());
        if(heartbeat.getTerm() < MachineInit.currentTermId.get()){
            heatbeatResponse.setSuccess(false);
            heatbeatResponse.setTerm(MachineInit.currentTermId.get());
            return heatbeatResponse;
        }

        List<LogEntry> logList  = MachineInit.logList;
        if(logList.get((int)heartbeat.getPrevLogIndex()).getLogTerm() !=  heartbeat.getPrevLogTerm()){
            heatbeatResponse.setSuccess(false);
            heatbeatResponse.setTerm(MachineInit.currentTermId.get());
            return heatbeatResponse;
        }


        if(heartbeat.getLeaderCommit() > MachineInit.commitIndex){
            MachineInit.commitIndex  = Math.min(heartbeat.getLeaderCommit(),MachineInit.commitIndex);
        }

        heatbeatResponse.setTerm(MachineInit.currentTermId.get());
        heatbeatResponse.setSuccess(true);
        return heatbeatResponse;

    }


    public VoteResponse ackVoteResponse(VoteRequest voteRequest){
        VoteResponse voteResponse = new VoteResponse();
        voteResponse.setBizCode(BizCode.VOTE.getBizCode());

        if(voteRequest.getTerm() < MachineInit.currentTermId.get()){
            voteResponse.setVoteGranted(false);
            voteResponse.setTerm(MachineInit.currentTermId.get());
            return voteResponse;
        }

        if(voteRequest.getTerm() > MachineInit.currentTermId.get()){
            MachineInit.currentTermId.set(voteRequest.getTerm());
        }

        int lastIndex = MachineInit.logList.size()-1;
        long lastTerm = MachineInit.logList.get(lastIndex).getLogTerm();

        if((MachineInit.leader == null || MachineInit.leader == voteRequest.getCandidateId()) && lastIndex <= voteRequest.getLastLogIndex() && lastTerm <= voteRequest.getLastLogTerm() ){
            MachineInit.leader = voteRequest.getCandidateId();
            voteResponse.setVoteGranted(true);
            voteResponse.setTerm(MachineInit.currentTermId.get());

            return voteResponse;
        }

        voteResponse.setTerm(MachineInit.currentTermId.get());
        voteResponse.setVoteGranted(false);
        return voteResponse;
    }
}
