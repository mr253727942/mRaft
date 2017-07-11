package com.mrraft.election;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ma.Rong on 2017/6/28.
 *
 * 选举超时定时器
 */
public class ElectionIdleTimer {

    private static Timer timer = new Timer();

    public boolean isIdle;

    public boolean isIdle() {
        return isIdle;
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
    }
}
