package com.ekotwick;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {

    public static void main(String[] args) {
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
