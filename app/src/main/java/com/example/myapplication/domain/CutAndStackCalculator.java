package com.example.myapplication.domain;

public class CutAndStackCalculator {

    private int[] odds;
    private int[] events;
    private int groups;
    private int rows;
    private int cols;

    private void fillOneSideArray() {
        odds = new int[groups];
        for (int i = 0; i < groups; i ++)
            odds[i] = i + 1;
    }

    private void fillArrays()
    {
        odds = new int[groups];
        events = new int[groups];
        int index = 0;
        for (int i = 1; i <= groups * 2; i ++)
            if (i % 2 != 0) odds[index] = i;
            else if (i % 2 == 0) events[index ++] = i;
    }

    private void reverseEventsArray()
    {
        int[] temp = new int[cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(events, i * cols, temp, 0, cols);
            temp = reverse(temp);
            System.arraycopy(temp, 0, events, i * cols, cols);
        }
    }

    private int[] reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }

    private String arrayToSting(int[] array) {
        StringBuilder result = new StringBuilder();
        for (int value : array) {
            result.append(value).append(" ");
        }
        return result.toString();
    }

    public String Generate(Integer cols, Integer rows, Boolean isOneSide) {
        groups = cols * rows;
        this.rows = rows;
        this.cols = cols;

        String result = "";

        if (isOneSide) {
            fillOneSideArray();
            result = arrayToSting(odds);
        } else {
            fillArrays();
            reverseEventsArray();
            result = arrayToSting(odds) + arrayToSting(events);
        }

        return result.trim();
    }
}
