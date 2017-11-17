package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Taxes_check extends  CurrencyConversionApp {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taxes_layout);
        ArrayList<Currency> array_currencies_object = new ArrayList<Currency>();

        //intentArray.getParcelableArrayListExtra("currency_obj");
        //SPINNER
        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initializeSpinner();
    }

    private void initializeSpinner() {

        Spinner spinnerTaxes = findViewById(R.id.spinner_taxes);

        spinnerTaxes.setAdapter(new Taxes_check.NewAdapter());
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
                convertView = inflator.inflate(R.layout.taxes_layout, null);
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

