package org.abondar.experimental.async.vertx.command.switcher;

import org.abondar.experimental.async.command.CommandSwitcher;
import org.abondar.experimental.async.vertx.command.params.VertxCommands;

public class VertxCommandSwitcher extends CommandSwitcher {
    @Override
    public void executeCommand(String cmd) {
        try {
            switch (VertxCommands.valueOf(cmd)){

            }
        } catch (IllegalArgumentException ex) {
            System.err.println("Unknown argument. Please check documentation.");
            System.exit(1);
        }
    }
}