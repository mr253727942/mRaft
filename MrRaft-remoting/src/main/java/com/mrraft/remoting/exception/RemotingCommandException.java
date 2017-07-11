package com.mrraft.remoting.exception;

/**
 * Created by T460P on 2016/12/29.
 */
public class RemotingCommandException extends RuntimeException{
    private static final long serialVersionUID = -6061365915274953096L;

    public RemotingCommandException(String message) {
        super(message, null);
    }


    public RemotingCommandException(String message, Throwable cause) {
        super(message, cause);
    }

}
