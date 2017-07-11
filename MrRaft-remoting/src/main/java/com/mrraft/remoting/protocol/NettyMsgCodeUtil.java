package com.mrraft.remoting.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by T460P on 2017/6/26.
 */
public interface NettyMsgCodeUtil {

    final static int MESSAGE_LENGTH = 4;

    void encode(final ByteBuf out, final Object message) throws IOException;

    Object decode(byte[] body) throws IOException;
}
