package com.ekotwick;

import static com.ekotwick.ThreadColor.ANSI_RED;

/**
 * Created by ekotwick on 7/31/17.
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(ANSI_RED+"Hello from the runnable class");
    }
}
