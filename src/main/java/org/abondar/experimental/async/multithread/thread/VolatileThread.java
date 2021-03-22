package org.abondar.experimental.async.multithread.thread;

public class VolatileThread extends Thread {

    private volatile boolean runFlag = true;

    private volatile int counter;

    @Override
    public void run(){
        System.out.println("Start thread");

        while (runFlag){
          counter+=20000;
        }

        System.out.println("Stop thread");
    }

    public void stopThread(){
        this.runFlag=false;
    }


    public int getCounter(){
        return counter;
    }
}
