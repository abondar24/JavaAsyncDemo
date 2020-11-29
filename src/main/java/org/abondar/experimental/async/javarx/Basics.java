package org.abondar.experimental.async.javarx;

import org.abondar.experimental.async.javarx.util.SleeperUtil;
import rx.*;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import static rx.Observable.just;

/**
 * Created by abondar on 2/2/17.
 */
public class Basics {

    public static void listBuffer(){
        Observable.range(1,7)
                .buffer(3)
                .subscribe(Basics::listOP);
    }

    public static void listBufferAvg(){
        Random random = new Random();
        Observable.defer(()->just(random.nextGaussian()))
                .repeat(1000)
                .buffer(100,1)
                .map(Basics::averageOfList)
                .subscribe(System.out::println);
    }



    private static double averageOfList(List<Double> list){
        return list.stream().collect(Collectors.averagingDouble(x->x));
    }
    private static void listOP(List<Integer> list){
        list.forEach(System.out::println);
    }

    private static Callback getDataAsynchronously(String key) {
        final Callback callback = new Callback();
        new Thread(() -> {
            SleeperUtil.sleep(Duration.ofSeconds(1));
            callback.getOnResponse().accept(key + ":123");
        }).start();
        return callback;
    }
}
