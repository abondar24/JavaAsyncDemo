package org.abondar.experimental.async;

import org.abondar.experimental.async.command.CommandSwitcher;
import org.abondar.experimental.async.command.CommandSwitchers;
import org.abondar.experimental.async.multithread.command.switcher.MultiThreadCommandSwitcher;
import org.abondar.experimental.async.nio.command.switcher.NioCommandSwitcher;
import org.abondar.experimental.async.vertx.command.switcher.VertxCommandSwitcher;
import org.abondar.expiermental.async.rx.command.switcher.RxCommandSwitcher;

import java.util.Optional;

public class CommandSwitcherFactory {

    private final CommandSwitcher mtSwitcher;

    private final CommandSwitcher nioSwitcher;

    private final CommandSwitcher rxSwitcher;

    private final CommandSwitcher vertxSwitcher;

    public CommandSwitcherFactory(){
        this.mtSwitcher = new MultiThreadCommandSwitcher();
        this.nioSwitcher = new NioCommandSwitcher();
        this.rxSwitcher = new RxCommandSwitcher();
        this.vertxSwitcher = new VertxCommandSwitcher();
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

                case VERTX:
                    return Optional.of(vertxSwitcher);
            }
        } catch (IllegalArgumentException ex){
            return Optional.empty();
        }

        return Optional.empty();
    }
}
