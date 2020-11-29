package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class MultipleSubscribersCommand implements Command {
    @Override
    public void execute() {
        Observable<Integer> ints = Observable.<Integer>create(subscriber -> {
                            log("Create");
                            subscriber.onNext(42);
                            subscriber.onCompleted();
                        }
                ).cache();

        log("Starting");
        ints.subscribe(i -> log("Element A: " + i));
        ints.subscribe(i -> log("Element B: " + i));
        log("Exit");
    }

    private static void log(Object msg) {
        System.out.println("MSC: " + msg);
    }
}
