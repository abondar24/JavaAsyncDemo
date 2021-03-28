package org.abondar.experimental.async.multithread.task;

import java.time.LocalDateTime;

public class ScheduledTask implements Runnable{

    private final int taskId;

    public ScheduledTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("Task #%d run at %s\n",taskId,now.toString());
    }
}
