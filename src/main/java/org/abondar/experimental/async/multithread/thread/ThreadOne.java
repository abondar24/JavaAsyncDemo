package org.abondar.experimental.async.multithread.thread;


public class ThreadOne extends Thread {
   @Override
   public void run() {

       try {
           for (int i =0;i<10;i++){
               System.out.println("Hiiii "+i);
               Thread.sleep(1000);
           }
       } catch (InterruptedException ex){
           System.err.println(ex.getMessage());
       }

   }
}
