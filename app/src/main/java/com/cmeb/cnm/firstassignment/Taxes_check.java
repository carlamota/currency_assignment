package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Taxes_check extends Activity {

    ArrayList<Currency> currencies_global;
    LayoutInflater inflator;
    Intent intent;
    Spinner chooseCurrency;
    ArrayList<Double> convertedTaxes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taxes_layout);

        intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        currencies_global = (ArrayList<Currency>) args.getSerializable("CURRENCIES");

        chooseCurrency = findViewById(R.id.spinner_taxes);
        chooseCurrency.setAdapter(new NewAdapter());
        chooseCurrency.setOnItemSelectedListener(new NewItemSelectedListener());
        ListView currencyView = findViewById(R.id.lt_view);
        currencyView.setAdapter(new NewListAdapter());

        convertTaxes(0);

        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class NewItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
           convertTaxes(pos);
       //    chooseCurrency.setAdapter(new NewAdapter());
        }

        public void onNothingSelected(AdapterView parent) {}
    }

    public void convertTaxes(int pos) {
        convertedTaxes.clear();
       for (int i = 0; i<currencies_global.size(); i++) {
           convertedTaxes.add(currencies_global.get(i).getRate()/currencies_global.get(pos).getRate());
       }

    }

    class NewListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return currencies_global.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            String rateValue;
            if (convertView == null) {
                convertView = inflator.inflate(R.layout.dropdown_layout, null);

                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(currencies_global.get(i).getFlag());
                TextView currencyName = convertView.findViewById(R.id.countryNameLabel);
                rateValue = String.valueOf(convertedTaxes.get(i));
                currencyName.setText(currencies_global.get(i).getName() + ": " + rateValue);
            } else {
                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(currencies_global.get(i).getFlag());
                TextView currencyName = convertView.findViewById(R.id.countryNameLabel);
                rateValue = String.valueOf(convertedTaxes.get(i));
                currencyName.setText(currencies_global.get(i).getName() + ": " + rateValue);
            }
            return convertView;
        }

    }



    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return currencies_global.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflator.inflate(R.layout.dropdown_layout, null);

                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(currencies_global.get(i).getFlag());
                TextView currencyName = convertView.findViewById(R.id.countryNameLabel);
                currencyName.setText(currencies_global.get(i).getName());
            } else {
                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(currencies_global.get(i).getFlag());
                TextView currencyName = convertView.findViewById(R.id.countryNameLabel);
                currencyName.setText(currencies_global.get(i).getName());
           }
            return convertView;
        }
    }

}

