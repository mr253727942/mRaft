package com.mraft.common.client;

/**
 * Created by T460P on 2017/9/25.
 */
public enum BizCode {

    ECHO(1,"echo");


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
