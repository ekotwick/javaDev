package com.ekotwick;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by ekotwick on 7/26/17.
 */
// there are two kinds of exceptions we can expect for this program: (1) an Arithmetic error, as when we divide by 0; or (2) a mismatch exception, as when we try dividing by something that is not an `int`
public class Example {

    public static void main(String[] args) {
        try {
            int result = divide();
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println(e.toString());
            System.out.println("Unable to perform division");
        }
    }

    private static int divide() {
        int x;
        int y;

        try {
            x = getInt();
            y = getInt();
            System.out.println("x is " + x + ", y is " + y);
            return x / y;
        } catch(NoSuchElementException e) {
            throw new ArithmeticException("no suitable input");
        } catch(ArithmeticException e) {
            throw new ArithmeticException("attempt to divide by 0");
        }

    }

    private static int getInt() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter an integer " );
        // here we have a loop that will check to see whether an exception occurs; if the exception is of the type we are watching out for, we will request a new integer input
        while(true) {
            try {
                return s.nextInt();
            } catch (InputMismatchException e) {
                s.nextLine(); // this reads past the line to start over again
                System.out.println("Please enter a number using only the digits 0 to 9");
            }
        }
//        return s.nextInt();
    }
}
