package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class AsyncComputationCommand implements Command {
    @Override
    public void execute() {
        Observable.<Integer>create(subscriber -> new Thread(() -> subscriber.onNext(42), "Blackjack Thread")
                .start())
                .doOnNext(i -> System.out.println(Thread.currentThread()))
                .filter(i -> i % 2 == 0)
                .map(i -> "Value " + i + " processed on " + Thread.currentThread())
                .subscribe(subscriber -> System.out.println("Some Value -> " + subscriber));
        System.out.println("Values are not emmited yet");
    }
}
