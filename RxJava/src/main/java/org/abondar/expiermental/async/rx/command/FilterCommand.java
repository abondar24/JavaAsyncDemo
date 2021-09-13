package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;

import static rx.Observable.just;

public class FilterCommand implements Command {
    @Override
    public void execute() {
        just(8, 9, 10)
                .doOnNext(i -> System.out.println("A: " + i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> System.out.println("B: " + i))
                .map(i -> "#" + i * 10)
                .doOnNext(s -> System.out.println("C: " + s))
                .filter(s -> s.length() < 4)
                .subscribe(s -> System.out.println("D: " + s));
    }

}
