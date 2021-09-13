package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;

public class ThreadGroupCommand implements Command {
    @Override
    public void execute() {

        ThreadGroup tg1 =new ThreadGroup("test");
        Thread t1 = new Thread(tg1,"t1");

        System.out.println(t1.getName());
        System.out.println(tg1.getName());

        Thread t2 = new Thread(tg1,"t2");

        System.out.println(t2.getName());
        System.out.println(t2.getThreadGroup().getName());
    }
}
