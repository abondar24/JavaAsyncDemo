package org.abondar.experimental.async.multithread.runner;

import org.abondar.experimental.async.multithread.Account;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TryLockRunner {

    private Account account1 = new Account();
    private Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLocks(Lock lock1, Lock lock2) throws InterruptedException {
        while (true) {

            boolean gotLock1 = false;
            boolean gotLock2 = false;
            try {
                gotLock1 = lock1.tryLock();
                gotLock2 = lock2.tryLock();
            } finally {
                if (gotLock1 && gotLock2) {
                    return;
                }

                if (gotLock1) {
                    lock1.unlock();
                }

                if (gotLock2) {
                    lock2.unlock();
                }
            }

            //locks aren't acquired
            Thread.sleep(1);
        }
    }

    public void firstThread() throws InterruptedException {
        Random amount = new Random();
        for (int i = 0; i < 10000; i++) {

            acquireLocks(lock1, lock2);

            try {
                Account.transfer(account1, account2, amount.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }

        }

    }


    public void secondThread() throws InterruptedException {
        Random amount = new Random();
        for (int i = 0; i < 10000; i++) {
            acquireLocks(lock2, lock1);

            try {
                Account.transfer(account2, account1, amount.nextInt(200));
            } finally {
                lock2.unlock();
                lock1.unlock();
            }

        }

    }

    public void finished() {
        System.out.println("Account 1 balance: " + account1.getBalance());
        System.out.println("Account 2 balance: " + account2.getBalance());
        System.out.println("Total balance: " + (account1.getBalance() + account2.getBalance()));
    }
}
