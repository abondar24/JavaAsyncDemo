package org.abondar.experimental.async.vertx.command.switcher;

import org.abondar.experimental.async.command.CommandSwitcher;
import org.abondar.experimental.async.vertx.command.BlockEventLoopCommand;
import org.abondar.experimental.async.vertx.command.DeployVerticleCommand;
import org.abondar.experimental.async.vertx.command.HelloVerticleCommand;
import org.abondar.experimental.async.vertx.command.NotificationVerticleCommand;
import org.abondar.experimental.async.vertx.command.VertxEchoCommand;
import org.abondar.experimental.async.vertx.command.params.VertxCommands;
import org.abondar.experimental.async.vertx.verticle.NotificationVerticle;

public class VertxCommandSwitcher extends CommandSwitcher {
    @Override
    public void executeCommand(String cmd) {
        try {
            switch (VertxCommands.valueOf(cmd)){

                case BEL:
                    BlockEventLoopCommand belc = new BlockEventLoopCommand();
                    executor.executeCommand(belc);
                    break;

                case CV:
                    HelloVerticleCommand cvc = new HelloVerticleCommand();
                    executor.executeCommand(cvc);
                    break;

                case DV:
                    DeployVerticleCommand dvc = new DeployVerticleCommand();
                    executor.executeCommand(dvc);
                    break;

                case NV:
                    NotificationVerticleCommand nvc = new NotificationVerticleCommand();
                    executor.executeCommand(nvc);
                    break;

                case VES:
                    VertxEchoCommand ec = new VertxEchoCommand();
                    executor.executeCommand(ec);
                    break;
            }
        } catch (IllegalArgumentException ex) {
            System.err.println("Unknown argument. Please check documentation.");
            System.exit(1);
        }
    }
}
