package org.abondar.experimental.async.multithread;


public class ThreadDemo {
    public static void main(String[] args) {
        ThreadOne threadOne = new ThreadOne();
        threadOne.start();

        ThreadTwo threadTwo = new ThreadTwo();
        threadTwo.run();

        Thread threadThree = new Thread(() -> System.out.println("SALO"));
        threadThree.start();

    }
}



