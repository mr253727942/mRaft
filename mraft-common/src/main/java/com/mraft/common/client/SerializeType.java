package com.mraft.common.client;

/**
 * Created by T460P on 2017/9/24.
 */
public enum SerializeType {

    JDK(1);

    public int code;

    SerializeType(int code){
        this.code = code;
    }
}
