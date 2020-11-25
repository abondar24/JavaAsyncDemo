package org.abondar.experimental.async.command.switcher;

public class MultiThreadCommandSwitcher extends CommandSwitcher {

    @Override
    public void executeCommand(String cmd) {
       try {

       } catch (IllegalArgumentException ex){
           System.err.println("Unknown argument. Please check documentation.");
           System.exit(1);
       }
    }
}
