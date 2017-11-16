package com.cmeb.cnm.firstassignment;

import android.graphics.drawable.Icon;

/**
 * Created by ASUS on 10/11/2017.
 */

public class Currency {

    private String name;
    private int flag;
    private double rate;


    public Currency(String name, int flag, double rate) {
        this.name = name;
        this.flag = flag;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public double getRate() {
        return rate;
    }

    public void setTaxe(double rate) {
        this.rate = rate;
    }
}
