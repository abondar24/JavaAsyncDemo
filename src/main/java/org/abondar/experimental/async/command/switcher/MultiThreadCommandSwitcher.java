package org.abondar.experimental.async.command.switcher;

import org.abondar.experimental.async.command.params.MtCommands;
import org.abondar.experimental.async.multithread.command.ThreadCommand;
import org.abondar.experimental.async.multithread.command.ThreadPoolCommand;

public class MultiThreadCommandSwitcher extends CommandSwitcher {

    @Override
    public void executeCommand(String cmd) {
       try {
            switch (MtCommands.valueOf(cmd)){
                case TC:
                    ThreadCommand tc = new ThreadCommand();
                    executor.executeCommand(tc);
                    break;

                case TPC:
                    ThreadPoolCommand tpc = new ThreadPoolCommand();
                    executor.executeCommand(tpc);
                    break;

            }
       } catch (IllegalArgumentException ex){
           System.err.println("Unknown argument. Please check documentation.");
           System.exit(1);
       }
    }
}
