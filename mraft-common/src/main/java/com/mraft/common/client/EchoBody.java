package com.mraft.common.client;

/**
 * Created by T460P on 2017/9/24.
 */
public class EchoBody extends BaseTransferBody{

    private static final long serialVersionUID = -2279028482367113654L;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
