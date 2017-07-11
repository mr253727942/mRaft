package com.mrraft.remoting;

import com.mrraft.remoting.netty.ResponseFuture;

/**
 * Created by T460P on 2016/12/30.
 */
public interface InvokeCallback {
    public void operationComplete(final ResponseFuture responseFuture);


}
