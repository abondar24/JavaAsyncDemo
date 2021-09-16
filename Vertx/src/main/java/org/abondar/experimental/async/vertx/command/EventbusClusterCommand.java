package org.abondar.experimental.async.vertx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.vertx.cluster.eventbus.EventBusAdditionalInstance;
import org.abondar.experimental.async.vertx.cluster.eventbus.EventbusInstance;

public class EventbusClusterCommand implements Command {

    @Override
    public void execute() {
        EventbusInstance instance = new EventbusInstance();
        instance.runInstance();

        new Thread(()->{
            EventBusAdditionalInstance additionalInstance = new EventBusAdditionalInstance();
            additionalInstance.runInstance();
        }).start();

    }
}
