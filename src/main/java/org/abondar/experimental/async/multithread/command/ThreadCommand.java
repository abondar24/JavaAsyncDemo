package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.thread.ThreadOne;
import org.abondar.experimental.async.multithread.thread.ThreadTwo;

public class ThreadCommand implements Command {

    @Override
    public void execute() {
        ThreadOne threadOne = new ThreadOne();
        threadOne.start();

        ThreadTwo threadTwo = new ThreadTwo();
        threadTwo.run();

        Thread threadThree = new Thread(() -> System.out.println("SALO"));
        threadThree.start();
    }
}



