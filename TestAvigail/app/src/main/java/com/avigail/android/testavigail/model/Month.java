package com.avigail.android.testavigail;

import java.util.ArrayList;

/**
 * Created by Avigail on 25/01/2019.
 */

public class Month {

    private String nameChild;

    private String month;

    private ArrayList<Week> monthList;

    public Month(){

    }

    public Month(ArrayList<Week> monthList, String month){
        this.month = month;
        this.monthList = monthList;
    }

    public String getNameChild(){
        return nameChild;
    }

    public void setNameChild(String nameChild){
        this.nameChild = nameChild;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public ArrayList<Week> getMonthList() {
        return monthList;
    }

    public void setMonthList(ArrayList<Week> monthList) {
        this.monthList = monthList;
    }

    public void add(Month month) {
    }

    public void clear() {
    }


}