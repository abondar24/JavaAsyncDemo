package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.event.EventLoop;

public class EventLoopCommand implements Command {
    @Override
    public void execute() {
        EventLoop loop = new EventLoop();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                delay(1000);
                loop.dispatch(new EventLoop.Event("tick", i));
            }

            loop.dispatch(new EventLoop.Event("stop", null));
        }).start();

        new Thread(() -> {
            delay(2000);
            loop.dispatch(new EventLoop.Event("hi", "how are you?"));
        }).start();

        loop.dispatch(new EventLoop.Event("foo", "bar"));

        loop.on("tick", System.out::println)
                .on("hi", System.out::println)
                .on("foo", System.out::println)
                .on("stop",e->loop.stop())
                .run();


    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
            System.exit(2);
        }
    }
}
