package org.abondar.experimental.async.vertx.command;

import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import org.abondar.experimental.async.command.Command;

public class ReadFileCommand implements Command {
    @Override
    public void execute() {
        Vertx vertx = Vertx.vertx();
        OpenOptions options = new OpenOptions();
        options.setRead(true);

        vertx.fileSystem().open("pom.xml",options,asyncResult->{
            if (asyncResult.succeeded()){
                AsyncFile file = asyncResult.result();
                file.handler(System.out::println)
                        .exceptionHandler(Throwable::printStackTrace)
                        .endHandler(done->{
                            System.out.println("\n--- DONE");
                            vertx.close();
                        });
            } else {
                asyncResult.cause().printStackTrace();
            }
        });
    }
}
