package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;

public class DaemonThreadCommand implements Command {
    @Override
    public void execute() {
        Thread t = new Thread(DaemonThreadCommand::print);
        t.setDaemon(true);
        t.start();

    }


    private static void print(){
        int counter =1;
        while (true){
            try {
                System.out.printf("Counter: %d\n",counter++);
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                System.err.println(ex.getMessage());
                System.exit(2);
            }
        }
    }
}
