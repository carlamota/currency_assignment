package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class CurrencyConversionApp extends Activity {

    Button taxesButton;

    private  ArrayList<Spinner> spinners = new ArrayList<>();
    private ArrayList<EditText> editTexts  = new ArrayList<>();
    private int[] selections;
    private Integer lastChanged;
    private boolean initialized;
    private boolean internetConnection;
    private boolean userSelection;
    private boolean withDatabase;
    private ArrayList<Currency> currencies_global = null;
    LayoutInflater inflator;
    // URL to get contacts JSON
    private static String url = "https://api.fixer.io/latest";
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_layout);
        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userSelection = false;

        if (savedInstanceState == null){
            selections = new int[] {0, 29, 8, 3};
            lastChanged = 0;
            initialized = false;
            withDatabase = false;

            if (currencies_global==null) {
                GetCurrencies getCurrencies = new GetCurrencies();
                getCurrencies.execute();

                if(internetConnection){
                    write();
                    Toast.makeText(getApplicationContext(),
                            "Currency Taxes Updated!",
                            Toast.LENGTH_SHORT)
                            .show();}
                else
                    read();
            }
        }
        else {
            if(withDatabase == false)
            {



            }
            else{
                read();
                onRestoreInstanceState(savedInstanceState);
                initializeRows();
                updateTextColors();
            }
        }


        taxesButton = findViewById(R.id.viewTaxesButton);
        taxesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(withDatabase) {
                    Intent i = new Intent("second_filter");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CURRENCIES", currencies_global);

                    i.putExtra("BUNDLE", bundle);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Empty Database!",Toast.LENGTH_SHORT).show();
                }
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

    private void initializeRows() {

        for (int i=0; i<4; i++){
            if(i==0) {
                spinners.add((Spinner) findViewById(R.id.countrySpinner1));
                editTexts.add((EditText) findViewById(R.id.editValue1));
                if (!initialized)
                   editTexts.get(0).setText("1.00");
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
            spinners.get(i).setSelection(selections[i], false);
            NewItemSelectedListener listener = new NewItemSelectedListener(i);
            spinners.get(i).setOnItemSelectedListener( listener);
            spinners.get(i).setOnTouchListener(listener);
            editTexts.get(i).setOnTouchListener(new NewOnTouchListener(i));
            editTexts.get(i).setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        }
    }

   public class NewOnTouchListener implements View.OnTouchListener {
       int i;

       public NewOnTouchListener(int i) {
           this.i = i;
       }

       @Override
       public boolean onTouch(View view, MotionEvent motionEvent) {
            lastChanged = i;
            initialized = true;
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            return false;
        }
    }

    public class NewItemSelectedListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        private int j;

        public NewItemSelectedListener(int j) { this.j = j; }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelection = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if(userSelection) {
                userSelection = false;
                selections[j] = pos;
                lastChanged = j;

                if (!initialized) {

                        initialized = true;

                }
            }
        }

        public void onNothingSelected(AdapterView parent) {}
    }

    public void updateTextColors(){
        for (int j = 0; j < 4; j++){
            if (j!=lastChanged)
              editTexts.get(j).setTextColor(Color.parseColor("#ff0099cc"));
            else  editTexts.get(j).setTextColor(Color.GRAY);
         }
    }


    public void onConvert(View v) {
        if(withDatabase) {
            Double taxeProportion;
            Double newValue;
            Double insertedValue;
            Double referenceTaxe = currencies_global.get(selections[lastChanged]).getRate();

            if (editTexts.get(lastChanged).getText().toString().length() > 0)
                insertedValue = Double.parseDouble(editTexts.get(lastChanged).getText().toString());
            else {
                insertedValue = 1.00;
                editTexts.get(lastChanged).setText("1.00");
            }
            for (int j = 0; j < 4; j++) {
                if (j != lastChanged) {
                    taxeProportion = currencies_global.get(selections[j]).getRate() / referenceTaxe;
                    newValue = insertedValue * taxeProportion;
                    editTexts.get(j).setText(String.format("%.2f", newValue).replaceAll(",", "."));
                }
            }

            updateTextColors();
        }
        else {
            Toast.makeText(getApplicationContext(),"Empty Database!",Toast.LENGTH_SHORT).show();
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
            }
            return convertView;
        }


        @Override
        public boolean isEnabled(int position){
            if(position == selections[0] || position == selections[1] || position == selections[2] || position == selections[3])
            {
                //Disable the third item of spinner.
                return false;
            }
            else
            {
                return true;
            }
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent)
        {
            convertView = inflator.inflate(R.layout.dropdown_layout, null);
            ImageView icon = convertView.findViewById(R.id.flag);
            TextView currencyName = convertView.findViewById(R.id.countryNameLabel);

            if(position == selections[0] || position == selections[1] || position == selections[2] || position == selections[3]) {

                icon.setImageResource(currencies_global.get(position).getFlag());
                currencyName.setText(currencies_global.get(position).getName());
                currencyName.setTextColor(Color.parseColor("#bcbcbb"));
            }
            else {

                icon.setImageResource(currencies_global.get(position).getFlag());
                currencyName.setText(currencies_global.get(position).getName());

            }
            return convertView;
        }

    }





    private class GetCurrencies extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    internetConnection = true;
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Object node
                    JSONObject currencies = jsonObj.getJSONObject("rates");

                    JSONArray names = currencies.names();
                    withDatabase = true;
                    currencies_global = new ArrayList<>();
                    currencies_global.add(new Currency("Euro", (R.drawable.europe), 1));

                    for (int k = 0; k < names.length(); k++) {

                        switch (names.getString(k)) {
                            case ("BGN"): {
                                Currency cur = new Currency("Bulgarian Lev", (R.drawable.bulgaria), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case ("BRL"): {
                                Currency cur = new Currency("Brazilian Real", (R.drawable.brazil), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case ("CAD"): {
                                Currency cur = new Currency("Canadian Dollar", (R.drawable.canada), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("CHF"):{
                                Currency cur = new Currency("Swiss Franc", (R.drawable.switzerland), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);

                                break;
                            }
                            case("CNY"):{
                                Currency cur = new Currency("Chinese Yuan", (R.drawable.china), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("CZK"):{
                                Currency cur = new Currency("Czech Koruna", (R.drawable.czech), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("GBP"):{
                                Currency cur = new Currency("British Pound", (R.drawable.unitedkingdom), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("HKD"):{
                                Currency cur = new Currency("Hong Kong Dollar", (R.drawable.hongkong), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);

                                break;
                            }
                            case("HRK"):{
                                Currency cur = new Currency("Croatian Kuna", (R.drawable.croatia), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("HUF"):{
                                Currency cur = new Currency("Hungarian Forint", (R.drawable.hungary), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("IDR"):{
                                Currency cur = new Currency("Indesian Rupiah", (R.drawable.indonesia), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("ILS"):{
                                Currency cur = new Currency("Isreali New Shekel", (R.drawable.israel), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("INR"):{
                                Currency cur = new Currency("Indian Rupee", (R.drawable.india), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("JPY"):{
                                Currency cur = new Currency("Japanese Yen", (R.drawable.japan), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("KRW"):{
                                Currency cur = new Currency("South Korean Won", (R.drawable.southkorea), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("MXN"):{
                                Currency cur = new Currency("Mexican Peso", (R.drawable.mexico), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("MYR"):{
                                Currency cur = new Currency("Malaysian Ringgit", (R.drawable.malaysia), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                           case("NOK"):{
                               Currency cur = new Currency("Norwegian Krone", (R.drawable.norway), currencies.getDouble(names.getString(k)));
                               currencies_global.add(cur);
                                break;
                            }
                            case("NZD"):{
                                Currency cur = new Currency("New Zeeland Dollar", (R.drawable.newzealand), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("PHP"):{
                                Currency cur = new Currency("Philippine Piso", (R.drawable.philippines), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("PLN"):{
                                Currency cur = new Currency("Polish Zloty", (R.drawable.poland), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("RON"):{
                                Currency cur = new Currency("Romanian Leu", (R.drawable.romania), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("RUB"):{
                                Currency cur = new Currency("Russian Rubble", (R.drawable.russia), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);
                                break;
                            }
                            case("SEK"):{
                                currencies_global.add(new Currency("Swedish Krona", (R.drawable.sweden), currencies.getDouble(names.getString(k))));
                                break;
                            }


                            case("SGD"):{
                                currencies_global.add(new Currency("Singapore Dollar", (R.drawable.singapore), currencies.getDouble(names.getString(k))));
                                break;
                            }

                            case("THB"):{
                                currencies_global.add(new Currency("Thai Baht", (R.drawable.thailand), currencies.getDouble(names.getString(k))));
                                break;
                            }

                            case("TRY"):{
                                currencies_global.add(new Currency("Turkish Lira", (R.drawable.turkey), currencies.getDouble(names.getString(k))));
                                break;
                            }
                            case("USD"):{
                                currencies_global.add(new Currency("USA Dollar", (R.drawable.usa), currencies.getDouble(names.getString(k))));
                                break;
                            }
                            case("ZAR"): {
                                currencies_global.add(new Currency("South African Rand", (R.drawable.southafrica), currencies.getDouble(names.getString(k))));
                                break;
                            }
                            case("AUD"):{
                                currencies_global.add(new Currency("Australian Dollar", (R.drawable.australia), currencies.getDouble(names.getString(k))));
                                break;
                            }
                        }

                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "No Internet Connection!",
                                Toast.LENGTH_SHORT)
                                .show();
                        internetConnection = false;
                    }
                });

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(internetConnection) {
                        initializeRows();
                        updateTextColors();
                    }
                }
            });

            return null;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if(withDatabase) {
            savedInstanceState.putBoolean("initialized", initialized);
            savedInstanceState.putIntArray("selections", selections);
            savedInstanceState.putInt("lastChanged", lastChanged);
            super.onSaveInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
if(withDatabase) {
    initialized = savedInstanceState.getBoolean("initialized");
    selections = savedInstanceState.getIntArray("selections");
    lastChanged = savedInstanceState.getInt("lastChanged");
}
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        private final int decimalDigits;

        /**
         * Constructor.
         *
         * @param decimalDigits maximum decimal digits
         */
        public DecimalDigitsInputFilter(int decimalDigits) {
            this.decimalDigits = decimalDigits;
        }

        @Override
        public CharSequence filter(CharSequence source,
                                   int start,
                                   int end,
                                   Spanned dest,
                                   int dstart,
                                   int dend) {


            int dotPos = -1;
            int len = dest.length();
            for (int i = 0; i < len; i++) {
                char c = dest.charAt(i);
                if (c == '.' || c == ',') {
                    dotPos = i;
                    break;
                }
            }
            if (dotPos >= 0) {

                // protects against many dots
                if (source.equals(".") || source.equals(","))
                {
                    return "";
                }
                // if the text is entered before the dot
                if (dend <= dotPos) {
                    return null;
                }
                if (len - dotPos > decimalDigits) {
                    return "";
                }
            }

            return null;
        }

    }

    public void onRefresh (View v){

        GetCurrencies getCurrencies = new GetCurrencies();
        getCurrencies.execute();
        if(internetConnection) {
            write();
            Toast.makeText(getApplicationContext(),
                    "Currency Taxes Updated!",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void write(){
        String filename = "currencies_global.srl";
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),"")+File.separator+filename));

            out.writeObject(currencies_global);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(){
        ObjectInputStream input;
        String filename = "currencies_global.srl";

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(getFilesDir(),"")+File.separator+filename)));
            currencies_global = null;
            currencies_global = (ArrayList<Currency>) input.readObject();
            Log.v("serialization","Currencies!");
            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Empty Database!",
                    Toast.LENGTH_SHORT)
                    .show();
            withDatabase=false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}