package org.abondar.experimental.async.command.switcher;

import org.abondar.experimental.async.command.params.CommandSwitchers;

import java.util.Optional;

public class CommandSwitcherFactory {

    private final CommandSwitcher mtSwitcher;

    private final CommandSwitcher nioSwitcher;

    private final CommandSwitcher rxSwitcher;

    public CommandSwitcherFactory(){
        mtSwitcher = new MultiThreadCommandSwitcher();
        nioSwitcher = new NioCommandSwitcher();
        rxSwitcher = new RxCommandSwitcher();
    }

    public Optional<CommandSwitcher> getSwitcher(String sw){
        try {
            switch (CommandSwitchers.valueOf(sw)){
                case MT:
                    return Optional.of(mtSwitcher);

                case RX:
                    return Optional.of(rxSwitcher);

                case NIO:
                    return Optional.of(nioSwitcher);
            }
        } catch (IllegalArgumentException ex){
            return Optional.empty();
        }

        return Optional.empty();
    }
}
