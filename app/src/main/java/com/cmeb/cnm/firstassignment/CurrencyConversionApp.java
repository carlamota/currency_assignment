package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrencyConversionApp extends Activity {
    private static String url = "https://api.androidhive.info/contacts/";
    private ListView lview;

    ArrayList<HashMap<String, String>> currencies_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion_app);

        currencies_list = new ArrayList<>();
        lview = (ListView) findViewById(R.id.list);
    }
}

