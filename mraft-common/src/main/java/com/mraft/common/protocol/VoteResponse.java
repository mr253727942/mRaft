package com.mraft.common.protocol;

import com.mraft.common.client.BaseTransferBody;

/**
 * Created by wenan.mr on 2017/12/4.
 *
 * @author wenan.mr
 * @date 2017/12/04
 */
public class VoteResponse extends BaseTransferBody{

    private long term;

    private boolean voteGranted ;

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public boolean isVoteGranted() {
        return voteGranted;
    }

    public void setVoteGranted(boolean voteGranted) {
        this.voteGranted = voteGranted;
    }
}
