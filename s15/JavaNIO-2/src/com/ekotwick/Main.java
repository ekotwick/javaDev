package com.ekotwick;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try (FileOutputStream binFile = new FileOutputStream("data.dat");
             FileChannel binChannel = binFile.getChannel()) {

            byte[] outputBytes = "Hello World!".getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(outputBytes); // the wrap method wraps the byte array into the buffer; it sets the buffer position to zero; the buffer's capacity will be set to the array length; and the buffer mark will be undefined
            int numBytes = binChannel.write(buffer);
            System.out.println("numBytes written was: " + numBytes);

            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES); // `Integer.BYTES` is the number of bytes in an integer
            intBuffer.putInt(245);
            intBuffer.flip(); // this resets the position of the buffer to zero
            numBytes = binChannel.write(intBuffer);
            System.out.println("numBytes written was: " + numBytes);

            intBuffer.flip();
            intBuffer.putInt(-98765);
            intBuffer.flip();
            numBytes = binChannel.write(intBuffer);
            System.out.println("numBytes written was: " + numBytes);


            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
            FileChannel channel = ra.getChannel();
            outputBytes[0] = 'a';
            outputBytes[1] = 'b';
            buffer.flip(); // without this, the buffer will print out the changes caused from the immediately above 2 lines; with flip(), this doesn't happen
            long numBytesRead = channel.read(buffer);
            System.out.println("outputBytes = " + new String(outputBytes));
        } catch(IOException e) {
            e.printStackTrace();
        }

	    try {
            // this way of doing this is too complicated(?), so we do it differently
//            FileInputStream file = new FileInputStream("data.txt");
//            FileChannel channel = file.getChannel();
            Path dataPath = FileSystems.getDefault().getPath("data.txt");
            // the Files.write method writes one line, such that a file is opened, written to, and then closed all at once at a time and it writes them in bytes; if you want to write mutliple lines, it's recommended to use the StringBuilder class and do it all in one shot;
            // the StandardOpenOption.APPEND means: write to an existing file; without the third argument, the program will create a file if it doesn't exist, and will overwrite any existing file
            Files.write(dataPath, "\nLine 4".getBytes("UTF-8"), StandardOpenOption.APPEND);
            List<String> lines = Files.readAllLines(dataPath);
            for(String line : lines) {
                System.out.println(line);
            }
        } catch(IOException e) {
	        e.printStackTrace();
        }
    }
}
