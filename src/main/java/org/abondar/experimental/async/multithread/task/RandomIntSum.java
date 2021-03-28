package org.abondar.experimental.async.multithread.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class RandomIntSum extends RecursiveTask<Integer> {

    private static final Random rand = new Random();
    private final int count;

    public RandomIntSum(int count) {
        this.count=count;
    }

    @Override
    protected Integer compute() {
        int result = 0;

        if (count<=0){
            return result;
        }

        if (count==1){
            return getRandomInteger();
        }

        List<RecursiveTask<Integer>> forks = new ArrayList<>();

        for (int i=0;i<count;i++){
            RandomIntSum subTask = new RandomIntSum(1);
            subTask.fork();

            forks.add(subTask);
        }

        for (RecursiveTask<Integer> fork : forks){
            result+= fork.join();
        }


        return result;
    }

    public int getRandomInteger(){
        int n = rand.nextInt(1000) +1;

        System.out.printf("Generated integer: %d\n",n);
        return n;
    }
}
