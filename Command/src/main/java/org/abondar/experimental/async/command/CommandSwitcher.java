package org.abondar.experimental.async.command;


import org.abondar.experimental.async.command.CommandExecutor;

public  abstract  class CommandSwitcher {

    protected final CommandExecutor executor;

    public CommandSwitcher() {
        this.executor = new CommandExecutor();
    }


    public abstract void executeCommand(String cmd);
}
