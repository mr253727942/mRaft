package com.mrraft.remoting.common;

import jdk.nashorn.internal.scripts.JO;

/**
 * Created by T460P on 2016/12/30.
 */
public abstract class ServiceThread implements Runnable{
    private static final long JoinTime = 90 * 1000;
    protected  final Thread thread;
    protected volatile boolean hasNotified = false;
    protected volatile boolean stoped = false;

    public abstract String getServiceName();
    public ServiceThread(){
        this.thread = new Thread(this,this.getServiceName());
    }

    public void start(){
        this.thread.start();
    }

    public void shutdown(final boolean interrupt){
        this.stoped = true;
        synchronized (this){
            if(!this.hasNotified){
                this.hasNotified = true;
                this.notify();
            }
        }

        try {
            if(interrupt){
                this.thread.interrupt();
            }
            long beginTime = System.currentTimeMillis();
            this.thread.join(this.getJoinTime());
            long eclipseTime  = System.currentTimeMillis()-beginTime;

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void shutdown(){
        this.shutdown(false);
    }

    public long getJoinTime(){
        return JoinTime;
    }
}
