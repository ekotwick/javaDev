package com.ekotwick;

import java.util.Random;

// this application will have two threads, one that creates messages and one that consumes messages
public class Main {

    public static void main(String[] args) {

    }
}

class Message {
    private String message;
    private boolean empty = true;

    // loop until there is a message to read, then read it
    public synchronized  String read() {
        while(empty) {

        }
        empty = true;
        return message;
    }

    // loop until there is no message to read, then write one
    public synchronized void write(String message) {
        while(!empty) {

        }
        empty = false;
        this.message = message;
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String messages[] = {
                "Alpha",
                "Beta",
                "Gamma",
                "Delta"
        };

        Random random = new Random();

        for(int i = 0; i < messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch(InterruptedException e) {

            }
        }

        message.write("Finished");
    }
}