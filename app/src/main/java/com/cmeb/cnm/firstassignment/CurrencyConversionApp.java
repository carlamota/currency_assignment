package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CurrencyConversionApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion_app);
    }


    public void onConvert(View v){

        setContentView(R.layout.convert_layout);

    }


    public void onViewTaxes(View v){

        setContentView(R.layout.taxes_layout);

    }

    public void onBack(View v){

        setContentView(R.layout.activity_currency_conversion_app);

    }

}