package com.avigail.android.testavigail;

import java.util.ArrayList;

/**
 * Created by Avigail on 25/01/2019.
 */

public class Week {

    public final static int NUM_OF_DAYS_IN_WEEK = 7;

    private int numOfBadMessagesSunday;
    private int numOfBadMessagesMonday;
    private int numOfBadMessagesTuesday;
    private int numOfBadMessagesWednesday;
    private int numOfBadMessagesThursday;
    private int numOfBadMessagesFriday;
    private int numOfBadMessagesSaturday;


    public Week(int sunday, int monday,int tuesday,
                int wednesday,int thursday,int friday,int saturday ) {
        this.numOfBadMessagesSunday = sunday;
        this.numOfBadMessagesMonday = monday;
        this.numOfBadMessagesTuesday = tuesday;
        this.numOfBadMessagesWednesday = wednesday;
        this.numOfBadMessagesThursday = thursday;
        this.numOfBadMessagesFriday = friday;
        this.numOfBadMessagesSaturday = saturday;

    }

    public float getAverage() {
        float sum = 0;
        sum += numOfBadMessagesSunday + numOfBadMessagesMonday + numOfBadMessagesTuesday + numOfBadMessagesWednesday
        + numOfBadMessagesThursday + numOfBadMessagesFriday + numOfBadMessagesSaturday;
        return sum/NUM_OF_DAYS_IN_WEEK;
    }
}
