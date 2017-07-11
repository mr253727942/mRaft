package com.mrraft.remoting.protocol;

/**
 * Created by T460P on 2016/12/29.
 */
public enum SerializeType {
    JSON((byte) 0);

    private byte code;

    SerializeType(byte code){
        this.code = code;
    }

    public static SerializeType valueOf(byte code){
        for(SerializeType serializeType : SerializeType.values()){
            if (serializeType.getCode() == code){
                return serializeType;
            }
        }
        return null;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
