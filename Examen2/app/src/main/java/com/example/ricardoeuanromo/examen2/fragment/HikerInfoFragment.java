package com.example.ricardoeuanromo.examen2.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricardoeuanromo.examen2.R;
import com.example.ricardoeuanromo.examen2.utils.PermissionUtils;

import java.io.IOException;
import java.util.Locale;

public class HikerInfoFragment extends Fragment implements LocationListener {

    private LocationManager locationManager;
    private Geocoder geocoder;

    private TextView results;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onResume() {

        super.onResume();

        // TODO: Implement runtime permissions
        /*
            We need location updates every time the fragment is resumed not only on create
         */
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        geocoder = new Geocoder(getContext(), Locale.getDefault());
    }

    @Override
    public void onPause() {

        super.onPause();

        /*
            Fragment is resting, so location updates are too
         */
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hiker_info, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        results = (TextView) getView().findViewById(R.id.result);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            results.setText(Double.toString(location.getLatitude()) + ", " + Double.toString(location.getLongitude()) +
                    "\n Accuracy: " + Double.toString(location.getAccuracy()) +
                    "\n Speed: " + Double.toString(location.getSpeed()) +
                    "\n Bearing: " + Double.toString(location.getBearing()) +
                    "\n Altitude: " + Double.toString(location.getAltitude()) +
                    "\n Address: " + geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        PermissionUtils.enableGPSDialog(getActivity());
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}