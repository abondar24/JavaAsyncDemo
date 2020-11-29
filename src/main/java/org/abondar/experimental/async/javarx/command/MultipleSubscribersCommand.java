package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;
import static org.abondar.experimental.async.javarx.util.LogUtil.log;

public class MultipleSubscribersCommand implements Command {
    @Override
    public void execute() {
        String logKey = "MSC";
        Observable<Integer> ints = Observable.<Integer>create(subscriber -> {
                            log(logKey,"Create");
                            subscriber.onNext(42);
                            subscriber.onCompleted();
                        }
                ).cache();

        log(logKey,"Starting");
        ints.subscribe(i -> log(logKey,"Element A: " + i));
        ints.subscribe(i -> log(logKey,"Element B: " + i));
        log(logKey,"Exit");
    }


}
