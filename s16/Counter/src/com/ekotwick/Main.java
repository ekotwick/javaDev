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

        for(i = 10; i > 0; i--) {
            System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
        }
        //=>
        /*
            Thread 1: i = 10
            Thread 2: i = 10
            Thread 2: i = 8
            Thread 2: i = 7
            Thread 1: i = 9
            Thread 2: i = 6
            Thread 1: i = 5
            Thread 1: i = 3
            Thread 1: i = 2
            Thread 2: i = 4
            Thread 1: i = 1
         */
        // it doesn't happen that the two count independently down from ten; this is because they are working with a variable stored in the heap rather than in the thread stack
        // which means that each time one thread decrements i, the other will work with a decremented i
        // but why, then, do both start with 10? because each step in the for-loop has the effect of suspending the both threads. The are several steps in the for loop: look up the variable, checking the condition, executing the code, modifing the variable; at each step each thread is suspended, and that means that both threads can look at and use the initial value of the variable on the heap *before* either has a chance to change that value;
        // NOW, these two threads keep interfering with each other;
        // we also see here a RACE CONDITION, where two or more processes are trying to write the same resource
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
