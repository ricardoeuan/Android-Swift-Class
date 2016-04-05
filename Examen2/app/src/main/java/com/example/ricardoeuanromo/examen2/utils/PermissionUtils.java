package com.example.ricardoeuanromo.examen2.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;


/**
 * Utility class for access to runtime permissions.
 */
public abstract class PermissionUtils {

    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void enableMobileDataDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("No internet connection.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("You need an internet connection to use the Hiker's Map feature.")
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public static boolean isGPSEnabled(LocationManager locationManager) {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void enableGPSDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("GPS is disabled")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("All the features of this Application require GPS to be enabled. Please turn it on to have a better experience.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}

