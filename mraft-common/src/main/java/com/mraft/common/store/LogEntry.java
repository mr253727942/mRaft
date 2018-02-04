package com.mraft.common.store;

/**
 * Created by wenan.mr on 2017/12/4.
 *
 * @author wenan.mr
 * @date 2017/12/04
 */
public class LogEntry {

    private String logMsg;

    private long logTerm;

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public long getLogTerm() {
        return logTerm;
    }

    public void setLogTerm(long logTerm) {
        this.logTerm = logTerm;
    }
}
