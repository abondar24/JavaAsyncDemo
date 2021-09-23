package org.abondar.experimental.async.vertx.command.switcher;

import org.abondar.experimental.async.command.CommandSwitcher;
import org.abondar.experimental.async.vertx.command.BlockEventLoopCommand;
import org.abondar.experimental.async.vertx.command.ConfigVerticleCommand;
import org.abondar.experimental.async.vertx.command.ContextCommand;
import org.abondar.experimental.async.vertx.command.DeployVerticleCommand;
import org.abondar.experimental.async.vertx.command.EdgeServiceCallbackCommand;
import org.abondar.experimental.async.vertx.command.EdgeServiceFutureCommand;
import org.abondar.experimental.async.vertx.command.EdgeServiceReactiveCommand;
import org.abondar.experimental.async.vertx.command.EventbusClusterCommand;
import org.abondar.experimental.async.vertx.command.EventbusCommand;
import org.abondar.experimental.async.vertx.command.HelloVerticleCommand;
import org.abondar.experimental.async.vertx.command.MixedVerticleCommand;
import org.abondar.experimental.async.vertx.command.BackPressureCommand;
import org.abondar.experimental.async.vertx.command.NotificationVerticleCommand;
import org.abondar.experimental.async.vertx.command.OffloadCommand;
import org.abondar.experimental.async.vertx.command.ReadFileCommand;
import org.abondar.experimental.async.vertx.command.VertxEchoCommand;
import org.abondar.experimental.async.vertx.command.WorkerVerticleCommand;
import org.abondar.experimental.async.vertx.command.params.VertxCommands;

public class VertxCommandSwitcher extends CommandSwitcher {
    @Override
    public void executeCommand(String cmd) {
        try {
            switch (VertxCommands.valueOf(cmd)){

                case BEL:
                    BlockEventLoopCommand belc = new BlockEventLoopCommand();
                    executor.executeCommand(belc);
                    break;

                case BP:
                    BackPressureCommand bpc = new BackPressureCommand();
                    executor.executeCommand(bpc);
                    break;

                case CO:
                    ContextCommand cc = new ContextCommand();
                    executor.executeCommand(cc);
                    break;

                case COV:
                    ConfigVerticleCommand cov = new ConfigVerticleCommand();
                    executor.executeCommand(cov);
                    break;

                case CV:
                    HelloVerticleCommand cvc = new HelloVerticleCommand();
                    executor.executeCommand(cvc);
                    break;

                case DV:
                    DeployVerticleCommand dvc = new DeployVerticleCommand();
                    executor.executeCommand(dvc);
                    break;

                case EB:
                    EventbusCommand ebc = new EventbusCommand();
                    executor.executeCommand(ebc);
                    break;

                case EBC:
                    EventbusClusterCommand ebcc = new EventbusClusterCommand();
                    executor.executeCommand(ebcc);
                    break;

                case ESC:
                    EdgeServiceCallbackCommand escc = new EdgeServiceCallbackCommand();
                    executor.executeCommand(escc);
                    break;

                case ESF:
                    EdgeServiceFutureCommand esfc = new EdgeServiceFutureCommand();
                    executor.executeCommand(esfc);
                    break;

                case ESR:
                    EdgeServiceReactiveCommand esrc = new EdgeServiceReactiveCommand();
                    executor.executeCommand(esrc);
                    break;

                case MV:
                    MixedVerticleCommand mvc = new MixedVerticleCommand();
                    executor.executeCommand(mvc);
                    break;

                case NV:
                    NotificationVerticleCommand nvc = new NotificationVerticleCommand();
                    executor.executeCommand(nvc);
                    break;

                case OV:
                    OffloadCommand oc = new OffloadCommand();
                    executor.executeCommand(oc);
                    break;

                case RF:
                    ReadFileCommand rfc = new ReadFileCommand();
                    executor.executeCommand(rfc);

                case WV:
                    WorkerVerticleCommand wvc =  new WorkerVerticleCommand();
                    executor.executeCommand(wvc);
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
