package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import org.apache.commons.lang3.tuple.Pair;
import rx.Observable;

public class TrueFalseCommand  implements Command {
    @Override
    public void execute() {
        Observable<Boolean> trueFalse = Observable.just(true, false).repeat();
        Observable<Integer> upstream = Observable.range(30, 8);
        Observable<Integer> downstream = upstream
                .zipWith(trueFalse, Pair::of)
                .filter(Pair::getRight)
                .map(Pair::getLeft);

        downstream.subscribe(System.out::println);

    }
}
