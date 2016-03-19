package com.itesm.ricardoeuan.examen01.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.itesm.ricardoeuan.examen01.R;
import com.itesm.ricardoeuan.examen01.utils.CurrencySharedPref;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private CurrencySharedPref currencySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        currencySharedPref = new CurrencySharedPref();

        Spinner spinnerCurrencyFrom = (Spinner) findViewById(R.id.spinnerCurrencyFrom);
        Spinner spinnerCurrencyTo = (Spinner) findViewById(R.id.spinnerCurrencyTo);
        Button returnBtn = (Button) findViewById(R.id.returnBtn);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.currencies_array, android.R.layout.simple_spinner_dropdown_item);

        spinnerCurrencyFrom.setAdapter(spinnerAdapter);
        spinnerCurrencyTo.setAdapter(spinnerAdapter);

        spinnerCurrencyFrom.setSelection(spinnerAdapter.getPosition(currencySharedPref.getValue(this, "fromCurrency")));
        spinnerCurrencyTo.setSelection(spinnerAdapter.getPosition(currencySharedPref.getValue(this, "toCurrency")));

        spinnerCurrencyFrom.setOnItemSelectedListener(this);
        spinnerCurrencyTo.setOnItemSelectedListener(this);
        returnBtn.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int resId = parent.getId();
        switch (resId) {
            case R.id.spinnerCurrencyFrom :
                switch (position) {
                    case 0:
                        currencySharedPref.apply(this, "fromCurrency", "EURO");
                        break;
                    case 1:
                        currencySharedPref.apply(this, "fromCurrency", "MXN");
                        break;
                    case 2:
                        currencySharedPref.apply(this, "fromCurrency", "USD");
                        break;
                }
                break;

            case R.id.spinnerCurrencyTo :
                switch (position) {
                    case 0:
                        currencySharedPref.apply(this, "toCurrency", "EURO");
                        break;
                    case 1:
                        currencySharedPref.apply(this, "toCurrency", "MXN");
                        break;
                    case 2:
                        currencySharedPref.apply(this, "toCurrency", "USD");
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
