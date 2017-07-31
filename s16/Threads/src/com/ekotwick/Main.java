package com.ekotwick;

import static com.ekotwick.ThreadColor.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE+"Hello from the main thread");

        Thread anotherThread = new AnotherThread();
        anotherThread.start(); // this command enables the JVM to run the `run` method in this class

        new Thread() {
            public void run() {
                System.out.println(ANSI_GREEN+"Hello from the anonymous class");
            }
        }.start();

        Thread myRunnableThread = new Thread(new MyRunnable());
        myRunnableThread.start();

        myRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ANSI_CYAN+"Hello from anonmyous my runnable class");
                try {
                    anotherThread.join(4000);
                    System.out.println(ANSI_RED+"AnotherThread terminated");
                } catch(InterruptedException e) {
                    System.out.println(ANSI_RED+"I couldn't wait after all");
                }
            }
        });
        myRunnableThread.start();

        System.out.println(ANSI_PURPLE+"Hello again from the main thread");
    }
}
