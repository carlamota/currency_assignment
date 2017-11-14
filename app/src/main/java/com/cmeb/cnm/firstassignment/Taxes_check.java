package com.cmeb.cnm.firstassignment;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by User on 14/11/2017.
 */

public class Taxes_check extends CurrencyConversionApp {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.taxes_layout);

        // Spinner element
        Spinner spinner_taxes = (Spinner) findViewById(R.id.spinner);


        // Spinner click listener

//        for(int i = 0; i < 30; i++)
//        {
//
//
//            Button b = new Button(this);
//            b.setText("Button "+i);
//            ll.addView(b);
//        }


    }
}
