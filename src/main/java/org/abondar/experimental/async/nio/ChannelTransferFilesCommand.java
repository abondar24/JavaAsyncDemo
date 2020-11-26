package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelTransferFilesCommand implements Command {



    private static void catFiles(WritableByteChannel target, String[] files) throws IOException {
        for (String file : files) {
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            channel.transferTo(0, channel.size(), target);
            channel.close();
            fis.close();
        }
    }


    @Override
    public void execute() {
        try {
            String[] files = new String[]{"pom.xml","README.md","dummy_text.txt"};
            catFiles(Channels.newChannel(System.out),files);
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
