package org.abondar.experimental.async.javarx;



/**
 * Created by abondar on 2/8/17.
 */
//for better view run comment everything else except demo you need
public class Main1 {
    public static void main(String[] args) {
        SchedulersDemo schedulersDemo = new SchedulersDemo();
        //    schedulersDemo.subscribeOnDemo();
        //schedulersDemo.observeOnDemo();
        schedulersDemo.subcribeOnObserveOnDemo();

    //    BackPressure.dishesRange();
    //    BackPressure.dishesMyRange();
    }
}
