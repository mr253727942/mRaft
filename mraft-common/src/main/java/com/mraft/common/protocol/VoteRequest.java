package com.mraft.common.protocol;

import com.mraft.common.client.BaseTransferBody;
import com.mraft.common.util.IpWrapper;

/**
 * Created by wenan.mr on 2017/12/4.
 *
 * @author wenan.mr
 * @date 2017/12/04
 */
public class VoteRequest extends BaseTransferBody{

    private long term;

    private IpWrapper candidateId;

    private Long lastLogIndex;

    private Long lastLogTerm;

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public IpWrapper getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(IpWrapper candidateId) {
        this.candidateId = candidateId;
    }

    public Long getLastLogIndex() {
        return lastLogIndex;
    }

    public void setLastLogIndex(Long lastLogIndex) {
        this.lastLogIndex = lastLogIndex;
    }

    public Long getLastLogTerm() {
        return lastLogTerm;
    }

    public void setLastLogTerm(Long lastLogTerm) {
        this.lastLogTerm = lastLogTerm;
    }
}
