package org.abondar.experimental.async.vertx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.vertx.verticle.edgeservice.EdgeServiceUtil;


public class EdgeServiceCallbackCommand implements Command {
    @Override
    public void execute() {
        EdgeServiceUtil.deployEdgeService(EdgeServiceUtil.EdgeService.Callback);
    }
}
