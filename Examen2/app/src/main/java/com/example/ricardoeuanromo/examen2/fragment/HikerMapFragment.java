package com.example.ricardoeuanromo.examen2.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.example.ricardoeuanromo.examen2.R;
import com.example.ricardoeuanromo.examen2.utils.PermissionUtils;


/**
 * Created by ricardoeuan on 1/19/16.
 */
public class HikerMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        LocationListener {

    private MapView mMapView;
    private GoogleMap googleMap;
    private LocationManager locationManager;

    private boolean mPermissionDenied = false;

    final private int MIN_TIME = 1;
    final private int MIN_DISTANCE = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (PermissionUtils.isNetworkConnected(getContext())) {
                if (PermissionUtils.isGPSEnabled(locationManager)) {
                    enableLocationManager();
                } else {
                    PermissionUtils.enableGPSDialog(getActivity());
                }
            } else {
                PermissionUtils.enableMobileDataDialog(getActivity());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_hiker_map, container, false);
        mMapView = (MapView) mapView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return mapView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if (mPermissionDenied) {
            mPermissionDenied = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

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
    public void onDestroy() {
        super.onDestroy();
        switchLocationListenerStatus();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        enableMyLocation(googleMap);

        this.googleMap = googleMap;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (PermissionUtils.isGPSEnabled(locationManager)) {
            Toast.makeText(getContext(), "Your current location.", Toast.LENGTH_SHORT).show();
        } else {
            PermissionUtils.enableGPSDialog(getActivity());
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
        googleMap.animateCamera(cameraUpdate);
        switchLocationListenerStatus();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        enableLocationManager();
    }

    @Override
    public void onProviderDisabled(String provider) {
        PermissionUtils.enableGPSDialog(getActivity());
    }

    private void enableMyLocation(GoogleMap googleMap) {

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
        googleMap.setMyLocationEnabled(true);

    }

    private void enableLocationManager() {

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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

    }

    private void switchLocationListenerStatus() {

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
}