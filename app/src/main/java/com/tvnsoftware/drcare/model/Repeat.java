package com.tvnsoftware.drcare.model;

/**
 * Created by Samn on 31-Jul-17.
 */

public class Repeat {
    private int repeatID;
    private String repeatDay;

    public Repeat(int repeatID, String repeatDay) {
        this.repeatID = repeatID;
        this.repeatDay = repeatDay;
    }

    public int getRepeatID() {
        return repeatID;
    }

    public String getRepeatDay() {
        return repeatDay;
    }

}
