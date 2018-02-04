package com.mraft.common.protocol;

import com.mraft.common.client.BaseTransferBody;

/**
 * Created by wenan.mr on 2017/11/29.
 *
 * @author wenan.mr
 * @date 2017/11/29
 */
public class HeatbeatResponse extends BaseTransferBody{

    private Long term;

    private boolean success;

    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
