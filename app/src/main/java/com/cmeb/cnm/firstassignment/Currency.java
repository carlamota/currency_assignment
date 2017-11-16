package com.cmeb.cnm.firstassignment;

import android.graphics.drawable.Icon;

/**

 * Created by User on 14/11/2017.
 */

public class Currency {
    public String name;
    private int flag;
    public double rate;


    Currency(String n, int f, double r) {
        name = n;
        flag = f;
        rate = r;
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
