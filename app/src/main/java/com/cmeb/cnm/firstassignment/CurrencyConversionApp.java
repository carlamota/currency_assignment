package com.cmeb.cnm.firstassignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
//import java.util.HashMap;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import static android.content.ContentValues.TAG;

public class CurrencyConversionApp extends Activity {

    private Spinner selectCountrySpinner;
    private LayoutInflater inflator;
    private int flags[];
    ArrayList<String> countriesNames;
    private ListView lv;
    private ProgressBar pBar;
    ArrayList<Currency> currencies_global = new ArrayList<Currency>();
    // URL to get contacts JSON
    private static String url = "https://api.fixer.io/latest";

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion_app);

        initialize();
    }

    private void initialize() {
//        flags = new int[] {R.drawable.algeria, R.drawable.bahamas, R.drawable.belgium, R.drawable.bangladesh};
//
//        countriesNames  = new ArrayList();
//        countriesNames.add("algeria");
//        countriesNames.add("bahamas");
//        countriesNames.add("belgium");
//        countriesNames.add("bangladesh");
        GetCurrencies a = new GetCurrencies();
        a.execute();
    }



    public void onConvert(View v) {

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
                ImageView icon = convertView.findViewById(R.id.flag);
                icon.setImageResource(flags[i]);
                TextView countryName = convertView.findViewById(R.id.countryNameLabel);
                countryName.setText(countriesNames.get(i));
            }
            return convertView;
        }

    }

    public void onViewTaxes(View v) {

        setContentView(R.layout.taxes_layout);
        new GetCurrencies().execute();

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetCurrencies extends AsyncTask<Void, Void, Void> {
        //        @Override
//        protected void onPreExecute(){
//            //shoing progress bar
//            ProgressBar  pBar = new ProgressBar(Activity, null, android.R.attr.progressBarStyleSmall);
//        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

//            ArrayList<String> currencies_names = new ArrayList<String>();
//            ArrayList<Double> currencies_rates = new ArrayList<Double>();
//            currencies_global.add(currencies_names);
//            currencies_global.add(currencies_rates);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Object node
                    JSONObject currencies = jsonObj.getJSONObject("rates");

                    JSONArray names = currencies.names();
                    // tmp hash map for single contact
//                    HashMap<String, Double> global_currencies = new HashMap<>();

                    for(int k=0; k<names.length(); k++) {
//                        global_currencies.put(names.getString(k), currencies.getDouble(names.getString(k)));
                        Currency cur = new Currency(names.getString(k), 0, currencies.getDouble(names.getString(k)));
                        currencies_global.add(cur);
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
            return null;
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

    }

    public void onBack(View v){

        setContentView(R.layout.activity_currency_conversion_app);
    }
}