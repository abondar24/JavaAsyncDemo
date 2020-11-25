package org.abondar.experimental.async.multithread;


public class Processor extends Thread{

    private boolean shutdown = true;

    @Override
    public void run() {
        while (shutdown){
            System.out.println("Hi");

            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    void shutdown(){
        shutdown = false;
    }
}
