package org.abondar.experimental.javaasyncdemo.multithread;

public class MultiProcessor implements Runnable {

    private int id;

    MultiProcessor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting: "+id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Compeleted: "+id);
    }
}