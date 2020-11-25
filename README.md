# JavaAsyncDemo

Set of Java Multithreading, NIO and RX demos.

## Multithreading

Use arg (mt) to run demos.
1. Thread (tc) - run several threads.
2. Thread Pool (tpc) - create a thread pool via executor service. 
3. Thread Synchronization (tsc) - synchronize threads by flag.
4. Thread Interruption (tic) - Interrupt thread by random value.
5. Wait Notify (wnc) - Usage of wait and notify base methods from Object.
6. Synchronized (sc) - Usage of synchronized method.
7. Countdown Latch (clc) - Usage of CountdownLatch class.
8. Low Level Wait (lwc) - Wait Notify demo based on synchronized block.
9. Callable Future (cfc) - Callable Future usage example.
10. Lock (lc) - Basic lock usage example.
11. Reentrant lock (rlc) - Reentrant lock usage examle.
12. Semaphore (sec) - Semaphore based on executor service.
13. Try Lock (tlc) - Usage two reentrant locks in parallel.

## NIO

Use arg (nio) to run demos.

## RX

Use arg (rx) to run demos.




## Build and run
````yaml
java -jar <jar-locaiton>/async.jar <demo-type> <demo-name>
````

##Notes

- All demo names are in ()
