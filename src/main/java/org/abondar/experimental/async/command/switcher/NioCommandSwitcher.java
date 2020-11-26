package org.abondar.experimental.async.command.switcher;


import org.abondar.experimental.async.command.params.NioCommands;
import org.abondar.experimental.async.nio.AsyncClient;
import org.abondar.experimental.async.nio.BackSlashesCommand;

public class NioCommandSwitcher extends CommandSwitcher{
    @Override
    public void executeCommand(String cmd) {
          try {
              switch (NioCommands.valueOf(cmd)){
                  case AC:
                      AsyncClient ac = new AsyncClient();
                      executor.executeCommand(ac);
                      break;

                  case BSC:
                      BackSlashesCommand bsc = new BackSlashesCommand();
                      executor.executeCommand(bsc);
                      break;
              }
          } catch (IllegalArgumentException ex){
              System.err.println("Unknown argument. Please check documentation.");
              System.exit(1);
          }
    }
}
