package org.abondar.experimental.async.nio.server;


import org.abondar.experimental.async.command.Command;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class MappedHttpServer implements Command {

    private static final String OUTPUT_FILE = "MappedHttp.out";
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: Web Sarvar";
    private static final String HTTP_HEADER = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HEADER = "HTTP/1.0 404 NOT FOUND" + LINE_SEP + SERVER_ID + LINE_SEP;

    private static final String MSG_404 = "Can't open file: ";




    private static byte[] bytes(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void execute() {

        String file = "dummy_text.txt";
        ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HEADER));
        ByteBuffer dynHeaders = ByteBuffer.allocate(128);
        ByteBuffer[] gather = {header, dynHeaders, null};
        String contentType;
        long contentLen;

        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer fileData = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            gather[2] = fileData;

            contentLen = fc.size();
            contentType = URLConnection.guessContentTypeFromName(file);
        } catch (IOException ex) {
            ByteBuffer buffer = ByteBuffer.allocate(128);
            String msg = MSG_404 + ex + LINE_SEP;

            buffer.put(bytes(msg));
            buffer.flip();

            //error responses
            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HEADER));
            gather[2] = buffer;

            contentLen = msg.length();
            contentType = "text/plain";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Content-Length: ");
        sb.append(contentLen);
        sb.append(LINE_SEP);
        sb.append("Content-Type: ");
        sb.append(contentType);
        sb.append(LINE_SEP).append(LINE_SEP);

        dynHeaders.put(bytes(sb.toString()));
        dynHeaders.flip();

        try {

            FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
            FileChannel out = fos.getChannel();

            //write out buffers
            while (out.write(gather)>0){

            }
            out.close();
            System.out.println("Output written to "+ OUTPUT_FILE);
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }


    }
}
