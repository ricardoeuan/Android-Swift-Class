package com.itesm.ricardoeuan.examen01.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ricardoeuan on 2/9/16.
 */
public class CurrencySharedPref {

    public static final String CURRENCY_PREFS_NAME = "currency_preferences";

    public CurrencySharedPref() {
        super();
    }

    public void apply(Context context, String key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(CURRENCY_PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(Context context, String key) {
        SharedPreferences settings;
        String value;

        settings = context.getSharedPreferences(CURRENCY_PREFS_NAME, Context.MODE_PRIVATE);
        value = settings.getString(key, null);

        return value;
    }
}
