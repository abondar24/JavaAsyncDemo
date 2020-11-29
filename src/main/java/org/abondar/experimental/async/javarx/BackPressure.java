package org.abondar.experimental.async.javarx;

import org.abondar.experimental.async.javarx.data.Dish;
import org.abondar.experimental.async.javarx.util.SleeperUtil;
import rx.Observable;
import rx.internal.operators.OnSubscribeRange;


import java.time.Duration;

/**
 * Created by alexabon on 2/9/2017.
 */
public class BackPressure {

    public static void dishesRange() {
        Observable.range(1, 1_00)
                .map(Dish::new)
                .subscribe(x -> {
                    System.out.println("Washing: " + x);
                    SleeperUtil.sleep(Duration.ofMillis(50));
                });
    }


    public static void dishesMyRange() {
        myRange(1, 1_00)
                .map(Dish::new)
                .subscribe(x -> {
                    System.out.println("Washing: " + x);
                    SleeperUtil.sleep(Duration.ofMillis(50));
                });
    }



    private static Observable<Integer> myRange(int from, int count) {
        return   Observable.create(new OnSubscribeRange(from, count));
    }




}
