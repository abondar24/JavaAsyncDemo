package org.abondar.experimental.async.command.switcher;


import org.abondar.experimental.async.command.CommandExecutor;

public  abstract  class CommandSwitcher {

    private final CommandExecutor executor;

    public CommandSwitcher() {
        this.executor = new CommandExecutor();
    }


    public abstract void executeCommand(String cmd);
}
