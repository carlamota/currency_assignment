package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrencyConversionApp extends Activity {

    private Spinner selectCountrySpinner;
    private LayoutInflater inflator;
    private int flags[];
    ArrayList<String> countriesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion_app);

        initialize();



    }

    private void initialize() {
        flags = new int[] {R.drawable.algeria, R.drawable.bahamas, R.drawable.belgium, R.drawable.bangladesh};

        countriesNames  = new ArrayList();
        countriesNames.add("algeria");
        countriesNames.add("bahamas");
        countriesNames.add("belgium");
        countriesNames.add("bangladesh");

    }


    public void onConvert(View v){

        setContentView(R.layout.convert_layout);

        selectCountrySpinner = findViewById(R.id.countrySpinner);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.country_row, R.id.flag, R.drawable.algeria);// R.id.countryNameLabel, countries);
        // selectCountrySpinner.setAdapter(adapter);

        selectCountrySpinner.setAdapter(new NewAdapter());
        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return flags.length;
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
                convertView = inflator.inflate(R.layout.country_row, null);
                ImageView icon =  convertView.findViewById(R.id.flag);
                icon.setImageResource(flags[i]);
                TextView countryName = convertView.findViewById(R.id.countryNameLabel);
                countryName.setText(countriesNames.get(i));
            }
            return convertView;
        }

    }


    public void onViewTaxes(View v){

        setContentView(R.layout.taxes_layout);

    }

    public void onBack(View v){

        setContentView(R.layout.activity_currency_conversion_app);

    }



}