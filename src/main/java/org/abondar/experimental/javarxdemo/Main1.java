package org.abondar.experimental.javarxdemo;



/**
 * Created by abondar on 2/8/17.
 */
//for better view run comment everything else except demo you need
public class Main1 {
    public static void main(String[] args) {
        Basics.scheduler1();
        SchedulersDemo schedulersDemo = new SchedulersDemo();
        //    schedulersDemo.subscribeOnDemo();
        //schedulersDemo.observeOnDemo();
        schedulersDemo.subcribeOnObserveOnDemo();
        Basics.singleDemo();
        //Basics.delays();
        //Basics.delayedNamesSample();
        //Basics.delayedNamesConcatWith();
        //  Basics.delayedNamesThrottleFirst();
        Basics.listBuffer();
        // Basics.listBufferAvg();
        Basics.delayedNamesBuffered();
    //    BackPressure.dishesRange();
    //    BackPressure.dishesMyRange();
    }
}
