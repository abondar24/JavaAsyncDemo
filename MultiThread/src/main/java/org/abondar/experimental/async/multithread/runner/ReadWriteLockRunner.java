package org.abondar.experimental.async.multithread.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockRunner {

    private List<String> data;

    private ReentrantReadWriteLock lock ;

    public ReadWriteLockRunner(){
        data = new ArrayList<>();
        lock = new ReentrantReadWriteLock();
    }

    public void readThread(){
         Lock readLock = lock.readLock();

         readLock.lock();
         try {
             System.out.println(data);
         } finally {
             readLock.unlock();
         }
    }

    public void writeThread(){
        Lock writeLock = lock.writeLock();

        writeLock.lock();
        try {
            for (int i=0;i<10000;i++){
                data.add("Some string "+i);
            }
        } finally {
            writeLock.unlock();
        }
    }
}
