package org.abondar.experimental.async.nio;


import org.abondar.experimental.async.command.Command;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.*;

public class CharsetDecodeCommand implements Command {


    private static void decodeChannel(ReadableByteChannel src, OutputStreamWriter writer)
            throws IOException, UnsupportedCharsetException {

        CharsetDecoder decoder =  StandardCharsets.ISO_8859_1.newDecoder();

        decoder.onMalformedInput(CodingErrorAction.REPLACE);
        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16 * 1024);
        CharBuffer charBuffer = CharBuffer.allocate(57);

        CoderResult result = CoderResult.UNDERFLOW;
        boolean eof = false;

        while (!eof) {
            if (result == CoderResult.UNDERFLOW) {
                byteBuffer.clear();

                eof = (src.read(byteBuffer) == -1);
                byteBuffer.flip();
            }

            result = decoder.decode(byteBuffer, charBuffer, eof);

            if (result == CoderResult.OVERFLOW) {
                drainCharBuf(charBuffer, writer);
            }
        }

        while (decoder.flush(charBuffer) == CoderResult.OVERFLOW) {
            drainCharBuf(charBuffer, writer);
        }

        drainCharBuf(charBuffer, writer);

        src.close();
        writer.flush();
    }

    private static void drainCharBuf(CharBuffer charBuffer, OutputStreamWriter writer) throws IOException {
        charBuffer.flip();
        if (charBuffer.hasRemaining()) {
            writer.write(charBuffer.toString());
        }
        charBuffer.clear();
    }

    @Override
    public void execute() {
        try {
            System.out.println("Enter something and press CTRL+D when finished:");
            decodeChannel(Channels.newChannel(System.in), new OutputStreamWriter(System.out));
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
