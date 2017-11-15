package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class CurrencyConversionApp extends Activity {
    private  ArrayList<Spinner> spinners = new ArrayList<>();
    private ArrayList<EditText> editTexts  = new ArrayList<>();
    private int[] selections;
    private Integer lastChanged = 0;

    private LayoutInflater inflator;
    private ArrayList<Currency> currencies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_layout);

        initializeCurrencies();
        initializeRows();

        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void initializeRows() {

        for (int i=0; i<4; i++){
            if(i==0) {
                spinners.add((Spinner) findViewById(R.id.countrySpinner1));
                editTexts.add((EditText) findViewById(R.id.editValue1));
            }
            else if (i==1) {
                spinners.add(i, (Spinner) findViewById(R.id.countrySpinner2));
                editTexts.add((EditText) findViewById(R.id.editValue2));
            }
            else if (i==2) {
                spinners.add(i, (Spinner) findViewById(R.id.countrySpinner3));
                editTexts.add((EditText) findViewById(R.id.editValue3));
            }
            else if (i==3) {
                spinners.add(i, (Spinner) findViewById(R.id.countrySpinner4));
                editTexts.add((EditText) findViewById(R.id.editValue4));
            }
            spinners.get(i).setAdapter(new NewAdapter());
            spinners.get(i).setSelection(selections[i]);
            spinners.get(i).setOnItemSelectedListener( new NewItemSelectedListener(i));
            editTexts.get(i).addTextChangedListener(new NewTextWatcher(i));
        }

    }

    public class NewItemSelectedListener implements AdapterView.OnItemSelectedListener {

        private int i;

        public NewItemSelectedListener(int i) { this.i = i; }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            selections[i]=pos;

            updateAvailableCurrencies();
        }

        public void onNothingSelected(AdapterView parent) {}
    }

    class NewTextWatcher implements android.text.TextWatcher {

        private int position;

        public NewTextWatcher(int i) {
            this.position = i;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            lastChanged = position;
        }
    }

    private void initializeCurrencies() {

        currencies = new ArrayList<>();
        currencies.add(new Currency("USA dollar", R.drawable.usa, 1));
        currencies.add(new Currency("Euro", R.drawable.europe, 2));
        currencies.add(new Currency("Japanese yen", R.drawable.japan, 3));
        currencies.add(new Currency("Pound sterling", R.drawable.unitedkingdom, 4));
        currencies.add(new Currency("Australian dollar", R.drawable.australia, 5));
        currencies.add(new Currency("Canadian dollar", R.drawable.canada, 6));
        currencies.add(new Currency("Swiss fran", R.drawable.switzerland, 2));
        currencies.add(new Currency("Renminbi", R.drawable.china, 2));
        currencies.add(new Currency("Swedish krona", R.drawable.sweden, 2));
        currencies.add(new Currency("New Zealand dollar", R.drawable.newzealand, 2));
        currencies.add(new Currency("Mexican pesa", R.drawable.mexico, 2));
        currencies.add(new Currency("Singapore dollar", R.drawable.singapore, 2));
        currencies.add(new Currency("Hong Kong dollar", R.drawable.hongkong, 2));
        currencies.add(new Currency("Norwegian krone", R.drawable.norway, 2));
        currencies.add(new Currency("Turkish lira", R.drawable.turkey, 2));
        currencies.add(new Currency("South Korean won", R.drawable.southkorea, 2));
        currencies.add(new Currency("Russian ruble", R.drawable.russia, 2));
        currencies.add(new Currency("Indian rupee", R.drawable.india, 2));
        currencies.add(new Currency("Brazilian real", R.drawable.brazil, 2));
        currencies.add(new Currency("South African rand", R.drawable.southafrica, 2));

        selections = new int[] {0,1,2,3};

    }


    public void onConvert(View view){

       Double taxeProportion;
       Double newValue;
       Double referenceTaxe = currencies.get(selections[lastChanged]).getRate();
       Double insertedValue = Double.parseDouble(editTexts.get(lastChanged).getText().toString());

       for (int i=0; i<4; i++){
           if( i!=lastChanged ) {
               taxeProportion = currencies.get(selections[i]).getRate() / referenceTaxe;
               newValue = insertedValue * taxeProportion;
               editTexts.get(i).setText(newValue.toString());
           }
       }
    }

    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return currencies.size();
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
                   icon.setImageResource(currencies.get(i).getFlag());
                   TextView currencyName = convertView.findViewById(R.id.countryNameLabel);
                   currencyName.setText(currencies.get(i).getName());
            }
            else{
                   ImageView icon = convertView.findViewById(R.id.flag);
                   icon.setImageResource(currencies.get(i).getFlag());
                   TextView currencyName = convertView.findViewById(R.id.countryNameLabel);
                   currencyName.setText(currencies.get(i).getName());
               }
                return convertView;
        }



    }

    public void updateAvailableCurrencies(){



    }

    public void onViewTaxes(View v){

       // setContentView(R.layout.taxes_layout);

    }


}