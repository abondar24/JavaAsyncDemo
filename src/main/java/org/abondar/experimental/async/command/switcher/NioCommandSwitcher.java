package org.abondar.experimental.async.command.switcher;


import org.abondar.experimental.async.command.params.NioCommands;
import org.abondar.experimental.async.nio.AsyncClient;
import org.abondar.experimental.async.nio.BackSlashesCommand;
import org.abondar.experimental.async.nio.BufferCharViewCommand;
import org.abondar.experimental.async.nio.BufferFillDrainCommand;
import org.abondar.experimental.async.nio.ChannelCopyCommand;
import org.abondar.experimental.async.nio.ChannelTransferFilesCommand;
import org.abondar.experimental.async.nio.CharSeqCommand;
import org.abondar.experimental.async.nio.CharsetDecodeCommand;
import org.abondar.experimental.async.nio.ComplexGrepCommand;
import org.abondar.experimental.async.nio.EmailFinderCommand;
import org.abondar.experimental.async.nio.EncodeTextCommand;
import org.abondar.experimental.async.nio.FileHoleCommand;
import org.abondar.experimental.async.nio.FileLockQueryCommand;
import org.abondar.experimental.async.nio.FileLockUpdateCommand;

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

                  case BCV:
                      BufferCharViewCommand bcv = new BufferCharViewCommand();
                      executor.executeCommand(bcv);
                      break;

                  case BFD:
                      BufferFillDrainCommand bfd = new BufferFillDrainCommand();
                      executor.executeCommand(bfd);
                      break;

                  case CDC:
                      CharsetDecodeCommand cdc = new CharsetDecodeCommand();
                      executor.executeCommand(cdc);
                      break;

                  case CGC:
                      ComplexGrepCommand cgc = new ComplexGrepCommand();
                      executor.executeCommand(cgc);
                      break;

                  case CHC:
                      ChannelCopyCommand chc = new ChannelCopyCommand();
                      executor.executeCommand(chc);
                      break;

                  case CTF:
                      ChannelTransferFilesCommand ctf = new ChannelTransferFilesCommand();
                      executor.executeCommand(ctf);
                      break;

                  case CSC:
                      CharSeqCommand csc = new CharSeqCommand();
                      executor.executeCommand(csc);
                      break;

                  case EFC:
                      EmailFinderCommand efc = new EmailFinderCommand();
                      executor.executeCommand(efc);
                      break;

                  case ETC:
                      EncodeTextCommand etc = new EncodeTextCommand();
                      executor.executeCommand(etc);
                      break;

                  case FHC:
                      FileHoleCommand fhc = new FileHoleCommand();
                      executor.executeCommand(fhc);
                      break;

                  case FLQ:
                      FileLockQueryCommand flq = new FileLockQueryCommand();
                      executor.executeCommand(flq);
                      break;

                  case FLU:
                      FileLockUpdateCommand flu = new FileLockUpdateCommand();
                      executor.executeCommand(flu);
                      break;
              }
          } catch (IllegalArgumentException ex){
              System.err.println("Unknown argument. Please check documentation.");
              System.exit(1);
          }
    }
}
