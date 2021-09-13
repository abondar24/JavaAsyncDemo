package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.expiermental.async.rx.util.SleeperUtil;
import rx.Observable;
import rx.subscriptions.Subscriptions;

import static java.util.concurrent.TimeUnit.SECONDS;


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
