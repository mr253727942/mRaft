package com.mraft.common.protocol;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.client.BizCode;
import com.mraft.common.client.Transfertype;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by wenan.mr on 2017/11/26.
 *
 * @author wenan.mr
 * @date 2017/11/26
 */
public class Heartbeat extends BaseTransferBody{

    public Heartbeat(){
        super.setBizCode(BizCode.HEATBEAT.getBizCode());
        super.setTransfertype(Transfertype.REQUEST);
    }

    private long term;

    private long leaderId;

    private long prevLogIndex;

    private long prevLogTerm;

    private long leaderCommit;

    private long acceptTime;

    public long getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(long acceptTime) {
        this.acceptTime = acceptTime;
    }

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public long getPrevLogIndex() {
        return prevLogIndex;
    }

    public void setPrevLogIndex(long prevLogIndex) {
        this.prevLogIndex = prevLogIndex;
    }

    public long getPrevLogTerm() {
        return prevLogTerm;
    }

    public void setPrevLogTerm(long prevLogTerm) {
        this.prevLogTerm = prevLogTerm;
    }

    public long getLeaderCommit() {
        return leaderCommit;
    }

    public void setLeaderCommit(long leaderCommit) {
        this.leaderCommit = leaderCommit;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
