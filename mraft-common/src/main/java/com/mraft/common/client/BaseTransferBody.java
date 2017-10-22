package com.mraft.common.client;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by T460P on 2017/9/24.
 */
public abstract class BaseTransferBody implements Serializable{

    private static final long serialVersionUID = -2294409463453143328L;
    /**
     * 序列化方式
     */
    private SerializeType serializeType;

    /**
     * 业务类型
     */
    private int bizCode;


    public SerializeType getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(SerializeType serializeType) {
        this.serializeType = serializeType;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
