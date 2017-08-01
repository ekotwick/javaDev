package com.ekotwick;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import static com.ekotwick.Main.EOF;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        // the ArrayBlockingQueue doesn't require the bufferLock;
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6); // like other Arrays, their capacity has to be declared in the beginning

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // once we create this, we need to change how we create the new threads

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_GREEN);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_BLUE);

        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        executorService.shutdown();
    }
}

class MyProducer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for(String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                buffer.put(num);
                Thread.sleep(random.nextInt(1000));
            } catch(InterruptedException e) {
                System.out.println("producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting...");
        try {
            buffer.put("EOF"); // this is a threadSafe method (it's ArrayLists that aren't threadSafe)
        } catch (InterruptedException e) {
        }
    }
}

class MyConsumer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    // this is a simplification: typically, we would have the method wait for a notifaction rather than run continuously like this; but this is a contrived example and designed for simplicity's sake
    @Override
    public void run() {
        while(true) {
            // we don't the producer/consumer to change the arraylist once a consumer thread has checked to see whether it is empty; so we want all method calls to the ArrayList to happen as a unit and at once in this `critical section`.
            synchronized (buffer) { // we have added the synchonized block here, because it can still happen that a method checks to see if the thing is empty, gets suspended, and then calls an operation that will now throw an exception, because some other method has modified the source object
                try {
                    if(buffer.isEmpty()) {// we need other `bufferLock.lock()` calls here; if the buffer is empty, we hit the `continue` to the next loop, where another `lock()` call is made, and so on with no releases of the lock; same with the `break`: we have locked the lock without ever releasing it.
                        continue;
                    }
                    if(buffer.peek().equals(EOF)) {
                        System.out.println(color+"Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
