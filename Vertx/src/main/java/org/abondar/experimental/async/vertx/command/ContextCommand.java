package org.abondar.experimental.async.vertx.command;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.abondar.experimental.async.command.Command;

public class ContextCommand implements Command {
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();

        Context ct = vertx.getOrCreateContext();
        ct.runOnContext(v-> System.out.println("123"));

        ct.exceptionHandler(throwable -> {
            if ("Error".equals(throwable.getMessage())){
                System.err.println("Error occured");
            } else {
                System.err.println(throwable.getMessage());
            }
        });

        ct.runOnContext(e->{
            throw new RuntimeException("Error");
        });

        ct.put("foo","bar");
        ct.runOnContext(msg -> System.out.printf("foo = %s ",(String)ct.get("foo")));



    }
}
