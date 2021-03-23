package org.abondar.experimental.async.multithread.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileThread extends Thread {

    private volatile boolean runFlag = true;

    private final AtomicInteger counter=new AtomicInteger(0);

    @Override
    public void run(){
        System.out.println("Start thread");

        while (runFlag){
          counter.getAndIncrement();
        }

        System.out.println("Stop thread");
    }

    public void stopThread(){
        this.runFlag=false;
    }


    public int getCounter(){
        return counter.get();
    }
}
