package com.ekotwick;

public class Main {

    public static void main(String[] args) {
        Countdown countdown = new Countdown();

        CountdownThread t1 = new CountdownThread(countdown);
        t1.setName("Thread 1");
        CountdownThread t2 = new CountdownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }


}

class Countdown {

    private int i;
    // METHOD ONE TO SYNCH: synch the method
//    public synchronized void doCountdown() {
    // METHOD TWO TO SYN: syn the code blocks
    public void doCountdown() {
        String color;

        switch(Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }
        // for METHOD ONE
//        for(i = 10; i > 0; i--) {
//            System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
//        }
        // for METHOD TWO
        synchronized (this) {
            for(i = 10; i > 0; i--) {
                System.out.println(color+Thread.currentThread().getName()+ ": i = " + i);
            }
        }

        //=> the whole method is executed before any other method gets access to the data; and this is without a local variable;
        /*
            Thread 1: i = 10
            Thread 1: i = 9
            Thread 1: i = 8
            Thread 1: i = 7
            Thread 1: i = 6
            Thread 1: i = 5
            Thread 1: i = 4
            Thread 1: i = 3
            Thread 1: i = 2
            Thread 1: i = 1
            Thread 2: i = 10
            Thread 2: i = 9
            Thread 2: i = 8
            Thread 2: i = 7
            Thread 2: i = 6
            Thread 2: i = 5
            Thread 2: i = 4
            Thread 2: i = 3
            Thread 2: i = 2
            Thread 2: i = 1
         */

    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }
}
