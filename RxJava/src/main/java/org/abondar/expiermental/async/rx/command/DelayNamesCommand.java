package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.just;

public class DelayNamesCommand implements Command {
    @Override
    public void execute() {
        Observable<String> delayedNames = delayedNames();

        System.out.println("Sample");
        delayedNames.sample(1, SECONDS)
                .subscribe(System.out::println);

        System.out.println("Concat");
        delayedNames
                .concatWith(Observable.<String>empty().delay(1, SECONDS))
                .sample(1, SECONDS)
                .subscribe(System.out::println);

        System.out.println("Throttle");
        delayedNames
                .throttleFirst(1, SECONDS)
                .subscribe(System.out::println);

        System.out.println("Buffer");
        delayedNames.buffer(1,SECONDS)
                .subscribe(System.out::println);

    }

    private Observable<String> delayedNames() {
        Observable<String> names =
                just("Mary", "Patricia", "Linda",
                        "Barbara",
                        "Elizabeth", "Jennifer", "Maria", "Susan",
                        "Margaret", "Dorothy");

        Observable<Long> delays =
                just(0.1, 0.6, 0.9,
                        1.1,
                        3.3, 3.4, 3.5, 3.6,
                        4.4, 4.8)
                        .map(d -> (long) (d * 1_000));
        return names
                .zipWith(delays,
                        (n, d) -> just(n).delay(d, MILLISECONDS))
                .flatMap(o -> o);
    }


}
