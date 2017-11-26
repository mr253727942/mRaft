package com.mraft.common.client;

/**
 * Created by wenan.mr on 2017/11/26.
 *
 * @author wenan.mr
 * @date 2017/11/26
 */
public enum  Transfertype {

    REQUEST("request"),RESPONSE("response");

    private String type;

    Transfertype(String type){
        this.type = type;
    }

}
