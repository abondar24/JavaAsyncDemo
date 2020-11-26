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
14. Producer Consumer (pcc) - Producer and Consumer synchronization via blocking queue.

## NIO

Use arg (nio) to run demos.

1. Async Client (ac) - Tiny client for web server.
2. Back Slashes (bsc) - Change Windows-style back slashes to Unix-style. 
3. Buffer Char View (bcv) - Fill buffer and print it.
4. Buffer Fill Drain (bfd) - Fill char buffer and drain it.
5. Channel Copy (chc) - Enter data to channel and copy it to another channel.
6. Channel Transfer Files (ctf) - Read the list of files, put their contents to the same channel and print it.
7. Char sequence (csc) - Char sequence and char buffer usage.
8. Charset Decode (cdc) - Enter a string and decode it to another encoding
9. Complex Grep (cgc) - Grep from multiple files in different modes(one by one, ignore case)
10. Email finder (efc) - Check if a string from list matches an email-regex.
11. Encode Text (etc) - Encode incoming string to different encodings.
12. File Lock Update (flu) - Lock file for updates.
13. File Lock Query (flq) - Lock file for reading.

## RX

Use arg (rx) to run demos.




## Build and run
````yaml
java -jar <jar-locaiton>/async.jar <demo-type> <demo-name>
````

##Notes

- All demo names are in ().
- Async Client requires a web server(nginx or apache).
- File lock demos must run in parallel.
