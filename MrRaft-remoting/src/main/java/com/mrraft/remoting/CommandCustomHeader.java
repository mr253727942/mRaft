package com.mrraft.remoting;

import com.mrraft.remoting.exception.RemotingCommandException;

/**
 * Created by T460P on 2016/12/29.
 */
public interface CommandCustomHeader {

    void checkFields() throws RemotingCommandException;
}
