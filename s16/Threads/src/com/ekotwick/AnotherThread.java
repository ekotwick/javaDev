package com.ekotwick;

import static com.ekotwick.ThreadColor.ANSI_BLUE;

/**
 * Created by ekotwick on 7/31/17.
 */
public class AnotherThread extends Thread {
    @Override
    public void run() {
        System.out.println(ANSI_BLUE+"Hello from the other thread");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){
            System.out.println(ANSI_BLUE+"Another whole thread woke me up");
            return; // this will terminate the thread; if we do not include this, than we get the following output: "Another whole thread woke me up" / "Another whole thread woke me up" —— so what happens is this:
        }

        System.out.println(ANSI_BLUE+"Three seconds have passed and I'm awake");
    }
}
