package org.abondar.experimental.async.command.switcher;

public class MultiThreadCommandSwitcher extends CommandSwitcher {

    @Override
    public void executeCommand(String cmd) {
        System.out.println(cmd);
    }
}
