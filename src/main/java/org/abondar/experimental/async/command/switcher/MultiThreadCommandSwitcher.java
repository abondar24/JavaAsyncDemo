package org.abondar.experimental.async.command.switcher;

import org.abondar.experimental.async.command.params.MtCommands;
import org.abondar.experimental.async.multithread.command.CountdownLatchCommand;
import org.abondar.experimental.async.multithread.command.LowLevelWaitCommand;
import org.abondar.experimental.async.multithread.command.SynchronizedCommand;
import org.abondar.experimental.async.multithread.command.ThreadCommand;
import org.abondar.experimental.async.multithread.command.ThreadInterruptionCommand;
import org.abondar.experimental.async.multithread.command.ThreadPoolCommand;
import org.abondar.experimental.async.multithread.command.ThreadSyncCommand;
import org.abondar.experimental.async.multithread.command.WaitNotifyCommand;

public class MultiThreadCommandSwitcher extends CommandSwitcher {

    @Override
    public void executeCommand(String cmd) {
       try {
            switch (MtCommands.valueOf(cmd)){
                case CLC:
                    CountdownLatchCommand clc = new CountdownLatchCommand();
                    executor.executeCommand(clc);
                    break;

                case LWC:
                    LowLevelWaitCommand lwc = new LowLevelWaitCommand();
                    executor.executeCommand(lwc);
                    break;

                case SC:
                    SynchronizedCommand sc = new SynchronizedCommand();
                    executor.executeCommand(sc);
                    break;

                case TC:
                    ThreadCommand tc = new ThreadCommand();
                    executor.executeCommand(tc);
                    break;

                case TIC:
                    ThreadInterruptionCommand tic = new ThreadInterruptionCommand();
                    executor.executeCommand(tic);
                    break;

                case TPC:
                    ThreadPoolCommand tpc = new ThreadPoolCommand();
                    executor.executeCommand(tpc);
                    break;

                case TSC:
                    ThreadSyncCommand tsc = new ThreadSyncCommand();
                    executor.executeCommand(tsc);
                    break;

                case WNC:
                    WaitNotifyCommand wnc = new WaitNotifyCommand();
                    executor.executeCommand(wnc);
                    break;

            }
       } catch (IllegalArgumentException ex){
           System.err.println("Unknown argument. Please check documentation.");
           System.exit(1);
       }
    }
}
