package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static rx.Observable.just;

public class ListBufferCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Buffer");
        Observable.range(1,7)
                .buffer(3)
                .subscribe(ListBufferCommand::printList);

        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

        System.out.println("Average of buffer");
        Random random = new Random();
        Observable.defer(()->just(random.nextGaussian()))
                .repeat(1000)
                .buffer(100,1)
                .map(ListBufferCommand::averageOfList)
                .subscribe(System.out::println);
    }

    private static void printList(List<Integer> list){
        list.forEach(System.out::println);
    }

    private static double averageOfList(List<Double> list){
        return list.stream().collect(Collectors.averagingDouble(x->x));
    }
}
