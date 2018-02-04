package com.mraft.core.Thread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.mraft.common.protocol.HeatbeatResponse;
import com.mraft.core.MachineInit;
import com.mraft.core.leadership.MachineRole;

/**
 * Created by wenan.mr on 2017/12/4.
 *
 * @author wenan.mr
 * @date 2017/12/04
 */
public class HeatbeatThread implements Runnable{

    @Override
    public void run() {
        while(true){
            try{

                Thread.sleep(1000L);
                Random r = new Random(1000);
                //不阻塞
                HeatbeatResponse heatbeatResponse = MachineInit.heatbeatResponseLinkedBlockingQueue.poll(r.nextInt(),
                    TimeUnit.MILLISECONDS);
                //发起选举
                if(heatbeatResponse == null){
                    MachineInit.machineRole = MachineRole.CANDIDATE;

                    Thread.sleep(r.nextInt());
                    long term = MachineInit.currentTermId.incrementAndGet();


                    //给其他machine发消息发起投票


                //处理心跳
                }else{

                }

            }catch (Throwable e){

            }
        }
    }
}
