package com.itesm.ricardoeuan.examen01.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.ricardoeuan.examen01.R;
import com.itesm.ricardoeuan.examen01.utils.ConversionUtils;
import com.itesm.ricardoeuan.examen01.utils.CurrencySharedPref;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CurrencySharedPref currencySharedPref;
    private SharedPreferences sharedPreferences;
    private ConversionUtils conversionUtils;

    private EditText input;
    private Button convertBtn;
    private TextView currencyFromLabel, currencyToLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencySharedPref = new CurrencySharedPref();
        sharedPreferences = getPreferences(MODE_PRIVATE);

        input = (EditText) findViewById(R.id.inputEditText);
        currencyFromLabel = (TextView) findViewById(R.id.currencyFromLabel);
        currencyToLabel = (TextView) findViewById(R.id.currencyToLabel);
        currencyToLabel = (TextView) findViewById(R.id.currencyToLabel);
        convertBtn = (Button) findViewById(R.id.convertBtn);

        currencyFromLabel.setText(sharedPreferences.getString("fromCurrency", "MXN"));
        currencyToLabel.setText(sharedPreferences.getString("toCurrency", "USD"));

        convertBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        currencyFromLabel.setText(currencySharedPref.getValue(this, "fromCurrency"));
        currencyToLabel.setText(currencySharedPref.getValue(this, "toCurrency"));
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

    public void settings(View v) {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        String from = currencyFromLabel.getText().toString();
        String to = currencyToLabel.getText().toString();
        if (isValidNumber(input.getText().toString())) {
            conversionUtils = new ConversionUtils(this, Float.valueOf(input.getText().toString()));
            if (from.equals("EURO") && to.equals("MXN"))
                conversionUtils.convertFromEuroToMXN();
            else if (from.equals("MXN") && to.equals("EURO"))
                conversionUtils.convertFromMXNToEuro();
            else if (from.equals("USD") && to.equals("EURO"))
                conversionUtils.convertFromUSDToEuro();
            else if (from.equals("EURO") && to.equals("USD"))
                conversionUtils.convertFromEuroToUSD();
            else if (from.equals("USD") && to.equals("MXN"))
                conversionUtils.convertFromUSDToMXN();
            else if (from.equals("MXN") && to.equals("USD"))
                conversionUtils.convertFromMXNToUSD();
            else
                Toast.makeText(this, "This conversion makes no sense", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Please give an ammount", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidNumber(String value) {
        if(value.isEmpty()) {
            return false;
        }
        return true;
    }
}
