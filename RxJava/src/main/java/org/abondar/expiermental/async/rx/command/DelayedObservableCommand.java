package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;
import rx.subscriptions.Subscriptions;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.abondar.experimental.async.rx.util.SleeperUtil.sleep;

public class DelayedObservableCommand implements Command {
    @Override
    public void execute() {
       Observable<String> observable = delayed("delay str");
       observable.subscribe(System.out::println);
    }

    public static <T> Observable<T> delayed(T x) {
        return Observable.create(subscriber -> {
            Runnable r = () -> {
                SleeperUtil.sleep(10, SECONDS);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(x);
                    subscriber.onCompleted();
                }
            };
            final Thread thread = new Thread(r);
            thread.start();
            subscriber.add(Subscriptions.create(thread::interrupt));
        });
    }
}
