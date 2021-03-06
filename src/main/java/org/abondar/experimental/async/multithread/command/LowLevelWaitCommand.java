package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.processor.LowLevelWaitProcessor;

public class LowLevelWaitCommand implements Command {


    @Override
    public void execute() {
        try {
            final LowLevelWaitProcessor processor = new LowLevelWaitProcessor();

            Thread t1 = new Thread(()->{
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread t2  = new Thread(()->{
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });


            t1.start();
            t2.start();

            t1.join();
            t2.join();
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
