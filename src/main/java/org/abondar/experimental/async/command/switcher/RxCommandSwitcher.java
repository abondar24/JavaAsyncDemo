package org.abondar.experimental.async.command.switcher;

import org.abondar.experimental.async.command.params.RxCommands;
import org.abondar.experimental.async.javarx.command.HelloCommand;

public class RxCommandSwitcher extends CommandSwitcher{
    @Override
    public void executeCommand(String cmd) {
        try {
            switch (RxCommands.valueOf(cmd)){
                case HC:
                    HelloCommand hc = new HelloCommand();
                    executor.executeCommand(hc);
            }
        } catch (IllegalArgumentException ex){
            System.err.println("Unknown argument. Please check documentation.");
            System.exit(1);
        }
    }
}
