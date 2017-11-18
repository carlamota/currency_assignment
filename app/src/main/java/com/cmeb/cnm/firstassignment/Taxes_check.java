package com.cmeb.cnm.firstassignment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public class Taxes_check extends Activity {

    ArrayList<Currency> currencies_global;
    LayoutInflater inflator;
    Intent intent;
    Spinner chooseCurrency;
    ListView currencyView;
    NewListAdapter listAdapter;
    ArrayList<Double> convertedTaxes = new ArrayList<>();
    Button button_list;
    Intent back_button_intent;


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
        currencyView = findViewById(R.id.lt_view);
        currencyView.setOnItemClickListener( new NewItemClick());
        listAdapter = new NewListAdapter();
        currencyView.setAdapter(listAdapter);

        convertTaxes(0);

        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        button_list = findViewById(R.id.button_list);
        button_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                back_button_intent = new Intent(Taxes_check.this, CurrencyConversionApp.class);
                startActivity(back_button_intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class NewItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
           convertTaxes(pos);
        }

        public void onNothingSelected(AdapterView parent) {}
    }

    public class NewItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            convertTaxes(i);
            chooseCurrency.setSelection(i);
        }
    }

    public void convertTaxes(int pos) {
        convertedTaxes.clear();
       for (int i = 0; i<currencies_global.size(); i++) {
           convertedTaxes.add(currencies_global.get(i).getRate()/currencies_global.get(pos).getRate());
       }
    listAdapter.notifyDataSetChanged();
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


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflator.inflate(R.layout.taxes_row, null);

                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(currencies_global.get(i).getFlag());
                TextView currencyName = convertView.findViewById(R.id.currencyNameLabel);
                TextView rateValue = convertView.findViewById(R.id.taxe_label);
                rateValue.setText(String.format(Locale.CANADA,"%.2f", convertedTaxes.get(i)));
                currencyName.setText(currencies_global.get(i).getName() );

                if (convertedTaxes.get(i)==1){
                    currencyName.setTextColor(Color.parseColor("#ff0099cc"));
                    rateValue.setTextColor(Color.parseColor("#ff0099cc"));
                }
                else{
                    currencyName.setTextColor(Color.GRAY);
                    rateValue.setTextColor(Color.GRAY);
                }
            } else {
                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(currencies_global.get(i).getFlag());
                TextView currencyName = convertView.findViewById(R.id.currencyNameLabel);
                TextView rateValue = convertView.findViewById(R.id.taxe_label);
                rateValue.setText(String.format(Locale.CANADA,"%.2f", convertedTaxes.get(i)));
                currencyName.setText(currencies_global.get(i).getName() );

                if (convertedTaxes.get(i)==1){
                    currencyName.setTextColor(Color.parseColor("#ff0099cc"));
                    rateValue.setTextColor(Color.parseColor("#ff0099cc"));
                }
                else{
                    currencyName.setTextColor(Color.BLACK);
                    rateValue.setTextColor(Color.BLACK);}
            }
            return convertView;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

