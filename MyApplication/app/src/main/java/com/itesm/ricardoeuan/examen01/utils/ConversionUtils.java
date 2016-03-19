package com.itesm.ricardoeuan.examen01.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ricardoeuan on 2/9/16.
 */
public class ConversionUtils {

    private Context context;
    private float value;

    public ConversionUtils(Context context, float value){
        this.context = context;
        this.value = value;
    }

    public void convertFromEuroToMXN() {
        Toast.makeText(context, String.valueOf(value / 0.046918), Toast.LENGTH_SHORT).show();
    }

    public void convertFromMXNToEuro() {
        Toast.makeText(context, String.valueOf(value * 0.046918), Toast.LENGTH_SHORT).show();
    }

    public void convertFromUSDToEuro() {
        Toast.makeText(context, String.valueOf(value/1.12996), Toast.LENGTH_SHORT).show();
    }

    public void convertFromEuroToUSD() {
        Toast.makeText(context, String.valueOf(value * 1.12996), Toast.LENGTH_SHORT).show();
    }

    public void convertFromUSDToMXN() {
        Toast.makeText(context, String.valueOf(value/0.053016), Toast.LENGTH_SHORT).show();
    }

    public void convertFromMXNToUSD() {
        Toast.makeText(context, String.valueOf(value * 0.053016), Toast.LENGTH_SHORT).show();
    }
}
