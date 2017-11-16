package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CurrencyConversionApp extends Activity {

    private  ArrayList<Spinner> spinners = new ArrayList<>();
    private ArrayList<EditText> editTexts  = new ArrayList<>();
    private int[] selections;
    private Integer lastChanged = -1;
    private boolean initialized = false;
//    private ListView lv;
//    private ProgressBar pBar;
    ArrayList<Currency> currencies_global = new ArrayList<>();
    LayoutInflater inflator;
    // URL to get contacts JSON
    private static String url = "https://api.fixer.io/latest";
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_layout);

        selections = new int[] {0, 29, 8, 3};

        GetCurrencies getCurrencies = new GetCurrencies();
        getCurrencies.execute();


//        initializeCurrencies();

        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void initializeRows(int[] selection) {

        for (int i=0; i<4; i++){
            if(i==0) {
                spinners.add((Spinner) findViewById(R.id.countrySpinner1));
                editTexts.add((EditText) findViewById(R.id.editValue1));
                editTexts.get(0).setText("1");
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
            spinners.get(i).setSelection(selection[i]);
            spinners.get(i).setOnItemSelectedListener( new NewItemSelectedListener(i));
            editTexts.get(i).addTextChangedListener(new NewTextWatcher(i));
        }
    }

    public class NewItemSelectedListener implements AdapterView.OnItemSelectedListener {

        private int j;

        public NewItemSelectedListener(int j) { this.j = j; }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            selections[j]=pos;
            lastChanged=j;
            if (!initialized){
                if (j==3){
                    initialized=true;
                    lastChanged = 0;
                }

                            }
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

    // CASO NAO HAJA NET:
    private void initializeCurrencies() {
/*
        currencies_global.add(new Currency("Euro", R.drawable.europe, 1));

        for (int i = 0; i<currencies_global.size(); i++)
        switch (currencies_global.get(i).getName()) {
            case ("BGN"): {
                currencies_global.get(i).setName("Bulgarian Lev");
                currencies_global.get(i).setFlag(R.drawable.bulgaria);
            }
            case ("BRL"): {
                currencies_global.get(i).setName("Brazilian Real");
                currencies_global.get(i).setFlag(R.drawable.brazil);
            }
            case ("CAD"): {
                currencies_global.get(i).setName("Canadian Dollar");
                currencies_global.get(i).setFlag(R.drawable.canada);
            }
            case("CHF"):{
                currencies_global.get(i).setName("Swiss Franc");
                currencies_global.get(i).setFlag(R.drawable.switzerland);
            }
            case("CNY"):{
                currencies_global.get(i).setName("Chinese Yuan");
                currencies_global.get(i).setFlag(R.drawable.china);
            }
            case("CZK"):{
                currencies_global.get(i).setName("Czech Koruna");
                currencies_global.get(i).setFlag(R.drawable.czech);
            }
            case("GBP"):{
                currencies_global.get(i).setName("British Pound");
                currencies_global.get(i).setFlag(R.drawable.unitedkingdom);
            }
            case("HKD"):{
                currencies_global.get(i).setName("Hong Kong Dollar");
                currencies_global.get(i).setFlag(R.drawable.hongkong);
            }
            case("HRK"):{
                currencies_global.get(i).setName("Croatian Kuna");
                currencies_global.get(i).setFlag(R.drawable.croatia);
            }
            case("HUF"):{
                currencies_global.get(i).setName("Hungarian Forint");
                currencies_global.get(i).setFlag(R.drawable.hungary);
            }
            case("IDR"):{
                currencies_global.get(i).setName("Indesian Rupiah");
                currencies_global.get(i).setFlag(R.drawable.indonesia);
            }
            case("ILS"):{
                currencies_global.get(i).setName("Isreali New Shekel");
                currencies_global.get(i).setFlag(R.drawable.israel);
            }
            case("INR"):{
                currencies_global.get(i).setName("Indian Rupee");
                currencies_global.get(i).setFlag(R.drawable.india);
            }
            case("JPY"):{
                currencies_global.get(i).setName("Japanese Yen");
                currencies_global.get(i).setFlag(R.drawable.japan);
            }
            case("KRW"):{
                currencies_global.get(i).setName("South Korean Won");
                currencies_global.get(i).setFlag(R.drawable.southkorea);
            }
            case("MXN"):{
                currencies_global.get(i).setName("Mexican Peso");
                currencies_global.get(i).setFlag(R.drawable.mexico);
            }
            case("MYR"):{
                currencies_global.get(i).setName("Malaysian Ringgit");
                currencies_global.get(i).setFlag(R.drawable.malaysia);
            }
            case("NOK"):{
                currencies_global.get(i).setName("Norwegian Krone");
                currencies_global.get(i).setFlag(R.drawable.norway);
            }
            case("NZD"):{
                currencies_global.get(i).setName("New Zeeland Dollar");
                currencies_global.get(i).setFlag(R.drawable.newzealand);
            }
            case("PHP"):{
                currencies_global.get(i).setName("Philippine Piso");
                currencies_global.get(i).setFlag(R.drawable.philippines);
            }
            case("PLN"):{
                currencies_global.get(i).setName("Philippine Piso");
                currencies_global.get(i).setFlag(R.drawable.philippines);
            }
            case("RON"):{
                currencies_global.get(i).setName("Romanian Leu");
                currencies_global.get(i).setFlag(R.drawable.romania);
            }
            case("RUB"):{
                currencies_global.get(i).setName("Russian Rubble");
                currencies_global.get(i).setFlag(R.drawable.russia);
            }
            case("SEK"):{
                currencies_global.get(i).setName("Swedish Krona");
                currencies_global.get(i).setFlag(R.drawable.sweden);
            }
            case("SGD"):{
                currencies_global.get(i).setName("");
                currencies_global.get(i).setFlag(R.drawable.philippines);
            }
            case("THB"):{
                currencies_global.get(i).setName("Singapore Dollar");
                currencies_global.get(i).setFlag(R.drawable.singapore);
            }
            case("TRY"):{
                currencies_global.get(i).setName("Turkish Lira");
                currencies_global.get(i).setFlag(R.drawable.turkey);
            }
            case("USD"):{
                currencies_global.get(i).setName("USA Dollar");
                currencies_global.get(i).setFlag(R.drawable.usa);
            }
            case("ZAR"): {
                currencies_global.get(i).setName("South African Rand");
                currencies_global.get(i).setFlag(R.drawable.southafrica);
            }
            case("AUD"):{
                currencies_global.get(i).setName("Australian Dollar");
                currencies_global.get(i).setFlag(R.drawable.australia);
            }
*/


            currencies_global.add(new Currency("USA dollar", R.drawable.usa, 1));
            currencies_global.add(new Currency("Euro", R.drawable.europe, 2));
            currencies_global.add(new Currency("Japanese yen", R.drawable.japan, 3));
            currencies_global.add(new Currency("Pound sterling", R.drawable.unitedkingdom, 4));
            currencies_global.add(new Currency("Australian dollar", R.drawable.australia, 5));
            currencies_global.add(new Currency("Canadian dollar", R.drawable.canada, 6));
            currencies_global.add(new Currency("Swiss fran", R.drawable.switzerland, 2));
            currencies_global.add(new Currency("Renminbi", R.drawable.china, 2));
            currencies_global.add(new Currency("Swedish krona", R.drawable.sweden, 2));
            currencies_global.add(new Currency("New Zealand dollar", R.drawable.newzealand, 2));
            currencies_global.add(new Currency("Mexican pesa", R.drawable.mexico, 2));
            currencies_global.add(new Currency("Singapore dollar", R.drawable.singapore, 2));
            currencies_global.add(new Currency("Hong Kong dollar", R.drawable.hongkong, 2));
            currencies_global.add(new Currency("Norwegian krone", R.drawable.norway, 2));
            currencies_global.add(new Currency("Turkish lira", R.drawable.turkey, 2));
            currencies_global.add(new Currency("South Korean won", R.drawable.southkorea, 2));
            currencies_global.add(new Currency("Russian ruble", R.drawable.russia, 2));
            currencies_global.add(new Currency("Indian rupee", R.drawable.india, 2));
            currencies_global.add(new Currency("Brazilian real", R.drawable.brazil, 2));
            currencies_global.add(new Currency("South African rand", R.drawable.southafrica, 2));

      //  }
    }


    public void onConvert(View v) {
            Double taxeProportion;
            Double newValue;
            Double referenceTaxe = currencies_global.get(selections[lastChanged]).getRate();
            Double insertedValue = Double.parseDouble(editTexts.get(lastChanged).getText().toString());

            for (int j = 0; j < 4; j++) {
                if (j != lastChanged) {
                    taxeProportion = currencies_global.get(selections[j]).getRate() / referenceTaxe;
                    newValue = insertedValue * taxeProportion;
                    editTexts.get(j).setText(newValue.toString());
                }
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
            else{
                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(currencies_global.get(i).getFlag());
                TextView currencyName = convertView.findViewById(R.id.countryNameLabel);
                currencyName.setText(currencies_global.get(i).getName());
            }
            return convertView;
        }
    }

    public void onViewTaxes(View v) {

        setContentView(R.layout.taxes_layout);

    }


    private class GetCurrencies extends AsyncTask<Void, Void, Void> {
        //        @Override
      //  protected void onPreExecute(){
            //shoing progress bar
       //     ProgressBar  pBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
     //   }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Object node
                    JSONObject currencies = jsonObj.getJSONObject("rates");

                    JSONArray names = currencies.names();

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
                                Currency cur = new Currency("Philippine Piso", (R.drawable.philippines), currencies.getDouble(names.getString(k)));
                                currencies_global.add(cur);

                                /////////////////////////////////////////////////
                      //          currencies_global.get(i).setName("Philippine Piso");
                      //          currencies_global.get(i).setFlag(R.drawable.philippines);
                                //////////////////////////////

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
                                    Toast.LENGTH_LONG)
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
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initializeRows(selections);
                }
            });



            return null;

        }

    }
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            // Dismiss the progress dialog
////            if (pDialog.isShowing())
////                pDialog.dismiss();
//            /**
//             * Updating parsed JSON data into ListView
//             * */
//            ListAdapter adapter = new SimpleAdapter(
//                    CurrencyConversionApp.this, currencies_global,
//                    R.layout.taxes_layout, new String[]{"currency_name", "currency_rate"},
//                    new int[]{R.id.currency_name, R.id.currency_rate});
//
//            lv.setAdapter(adapter);
//        }

        //   }


}