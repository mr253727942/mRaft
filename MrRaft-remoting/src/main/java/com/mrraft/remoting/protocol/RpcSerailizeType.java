package com.mrraft.remoting.protocol;

/**
 * Created by Ma.Rong on 2017/6/27.
 */
public enum RpcSerailizeType {
    JDK("jdk"),KRyO("kryo");

    private String protocol;

    RpcSerailizeType(String protocol){
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}
