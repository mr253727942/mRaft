package com.mrraft.remoting.protocol;

import java.io.Serializable;

/**
 * Created by T460P on 2017/6/26.
 */
public class RemoteObject implements Serializable{

    private int code;


    private String remark;

    private Object obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "RemoteObject{" +
                "code=" + code +
                ", remark='" + remark + '\'' +
                ", obj=" + obj +
                '}';
    }
}
