package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

import static rx.Observable.just;

public class FlatMapCommand implements Command {
    @Override
    public void execute() {
        Observable<Integer> numbers = just(1, 2, 3, 4,5,6,7);


        numbers.flatMap(x -> just(x * 2))
                .flatMap(x -> (x != 10) ? just(x) : Observable.create(System.out::println));
    }
}
