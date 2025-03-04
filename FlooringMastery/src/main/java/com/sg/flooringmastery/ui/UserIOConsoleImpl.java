package com.sg.flooringmastery.ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO{
    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);

        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);

        return sc.nextInt();
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt + min + " - " + max);

        return sc.nextInt();
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);

        return sc.nextDouble();
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt + min + " - " + max);

        return sc.nextDouble();
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);

        return sc.nextFloat();
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt + min + " - " + max);

        return sc.nextFloat();
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);

        return sc.nextLong();
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt + min + " - " + max);

        return sc.nextLong();
    }
}
