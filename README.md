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
14. File Hole (fhc) - Create a temp file and write to it.
15. Gathering Write (gwc) - Write data from three arrays to three columns in file.
16. Map File (mfc) - Create temp file and read its contents to different map buffers.
17. Mapped HTTP Server (mhs) - Write file contents with HTTP headers to output file.
18. Pipe (pic) - Create pipe from read and write buffers.
19. Poodle (poc) - Generate XML style table based on input and regex patterns.
20. Regex Append (rac) - Append string contents by regex.
21. Regex Replace (rrc) - Replace string contents by regex.
22. Simple Grep (sgc)  - Simple Grep from file input.
23. Socket Server (ss) - Tiny socket server. Access via localhost:1818.
24. Socket Selector Server (ses) - Server with socket selector. Access via localhost:1234.
25. Socket Selector Thread pool Server (sest) - Server with socket selector and multithreaded worker.
 Access via localhost:1234.
26. Time Server - Tiny time server. Access via localhost:1037.
27. Time Client - Tiny client for time server on port 1037.

## RX

Use arg (rx) to run demos.

1. Hello (hc) - JavaRX Hello World.
2. In Memory (imc) - Read data from fake cache via observable and subscriber.
3. Synchronous Computation (syc) - Synchronous computation via observable and subscriber.
4. Asychronous Computation (asyc) - Asychronous computation via observable and subscriber.
5. Two Threads (ttc) - Create two observables with two threads inside,
merge them to one observable and subscribe to it.
6. Single - usage of single with error handling.
7. Merge Singles (msic) - Merge two singles into one observable and subscribe to it. 
8. Completable (cc) - Usage of Completable class for asynchronous data writing.
9. Notifications (nc) - Dummy example of subscription to tweets stream via JavaRX.
10. Observable (oc) - Basic Usage of observable.
11. Multiple Subscribers (msc) - Usage of observable with multiple subscribers.
12. Natural Numbers (nnc) - Count natural numbers in loop while subscribed.
13. Delayed Observable (doc) - Custom observable with delay.
14. Parallel Load (plc) - Load data in parallel from two different observables.
15. Timed Observable (toc) - Thread sleep functionality in JavaRX style.
16. Interval (ic) - Observable which prints data within an interval.
17. Filter (fc) - Filter data in observable.
18. Morse (mc) - Morse encoding via flat map usage in JavaRX.
19. Day command (dc) - Usage of concatmap in observable.
20. Tweet Command (tc) - Dummy reactive client for Twitter.
21. Shakespaere (shc) - Split strings with Shakespaere quotes ,map,zip and merge/concat results.
22. True False (tfc) - Compare numbers in range.
23. Scheduler Worker (swc) - Reactive worker with a scheduler.
24. Delay Command (dec) - Observable calculating the timestamp after delay.
25. Delay Names (dnc) - Usage of some operators with observable.
26. List Buffer (lbc) - Usage of list buffer and calculation of average in the list.
27. Back Pressure (bpc) - Back pressure functionality implemented via JavaRX.
28. Scheduler Subscribe (ssc) - Usage of  subscription to scheduler.
29. Scheduler Observe (soc) - Usage of observe on scheduler.
30. Scheduler Combo (scc) - Combination of  Scheduler Subscribe and Observe.
31. Netty HTTP Server (nhs) - Reactive HTTP server based on Netty. Access via: localhost:8086
32. Netty TCP Server (nts) -  Reactive TCP server based on Netty. Access via: localhost:8084 (use tcp client e.g netcat)
33. Rest Currency Server (rcs) - Reactive Rest server which converts EUR to USD (use any rest client).
```yaml
Request: GET localhost:8088/convert/100
Response: {"EUR": 100, "USD": 106.55500}
```
## Build and run
````yaml
java -jar <jar-locaiton>/async.jar <demo-type> <demo-name>
````

##Notes

- All demo names are in ().
- Async Client requires a web server(nginx or apache).
- File lock demos must run in parallel.
