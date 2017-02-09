package org.abondar.experimental.javarxdemo;



/**
 * Created by abondar on 2/8/17.
 */
//for better view run either subscribeOn or ObserveOn
public class Main1 {
    public static void main(String[] args) {
        Basics.scheduler1();
        SchedulersDemo schedulersDemo = new SchedulersDemo();
   //    schedulersDemo.subscribeOnDemo();
       //schedulersDemo.observeOnDemo();
       schedulersDemo.subcribeOnObserveOnDemo();
       Basics.singleDemo();
      }
}
