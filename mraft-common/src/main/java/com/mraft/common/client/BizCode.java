package com.mraft.common.client;

/**
 * Created by wenan.mr on 2017/9/25.
 */
public enum BizCode {

    ECHO(1,"echo"),ERROR(400,"error"),HEATBEAT(2,"heatbeat"),VOTE(3,"vote");


    private String bizMsg;

    private int bizCode;

    BizCode(int bizCode,String bizMsg){
        this.bizCode = bizCode;
        this.bizMsg = bizMsg;
    }

    public String getBizMsg() {
        return bizMsg;
    }

    public void setBizMsg(String bizMsg) {
        this.bizMsg = bizMsg;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }
}
