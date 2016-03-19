package com.example.ricardoeuanromo.weatherapp;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ricardoeuanromo.weatherapp.fragment.ByCityIDFragment;
import com.example.ricardoeuanromo.weatherapp.fragment.ByCoordinatesFragment;
import com.example.ricardoeuanromo.weatherapp.fragment.ByNameFragment;
import com.example.ricardoeuanromo.weatherapp.fragment.ByZipCodeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByNameFragment myFragment = (ByNameFragment)getSupportFragmentManager().findFragmentById(R.id.frame_container);
                // Validate user instantiated a fragment to perform query
                if (myFragment != null && myFragment.isVisible()) {
                    new OpenWeatherMapTask(((EditText) findViewById(R.id.editTextCityName)).getText().toString(), (TextView) findViewById(R.id.json)).execute();
                } else {
                    Snackbar.make(view, "To start select an option from left side menu", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private class OpenWeatherMapTask extends AsyncTask<Void, Void, String> {

        String cityName;
        TextView tvResult;

        String appId = "395f4ba13223b3cf35c4fcc9bc9e082a";
        String queryWeather = "http://api.openweathermap.org/data/2.5/weather?q=";
        String queryKey = "&appid=" + appId;

        OpenWeatherMapTask(String cityName, TextView tvResult) {
            this.cityName = cityName;
            this.tvResult = tvResult;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String queryReturn;

            String query = null;
            try {
                query = queryWeather + URLEncoder.encode(cityName, "UTF-8") + queryKey;
                queryReturn = sendQuery(query);
                result += ParseJSON(queryReturn);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            }

            //Separate thread that fills the result TextView with processed data
            final String finalQueryReturn = query + "\n\n" + queryReturn;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) findViewById(R.id.result)).setText(finalQueryReturn);
                }
            });

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            tvResult.setText(s);
        }

        // Make the httpConnection with remote host
        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
            }
            return result;
        }

        // This method extracts strings, objects and arrays from the JSON returned from server
        private String ParseJSON(String json) {
            String jsonResult = "";

            try {
                JSONObject jsonObject = new JSONObject(json);
                String cod = jsonHelperGetString(jsonObject, "cod");

                if (cod != null) {
                    if (cod.equals("200")) {
                        jsonResult += jsonHelperGetString(jsonObject, "name") + "\n";
                        JSONObject sys = jsonHelperGetJSONObject(jsonObject, "sys");
                        if (sys != null) {
                            jsonResult += jsonHelperGetString(sys, "country") + "\n";
                        }
                        jsonResult += "\n";

                        JSONObject coord = jsonHelperGetJSONObject(jsonObject, "coord");
                        if (coord != null) {
                            String lon = jsonHelperGetString(coord, "lon");
                            String lat = jsonHelperGetString(coord, "lat");
                            jsonResult += "lon: " + lon + "\n";
                            jsonResult += "lat: " + lat + "\n";
                        }
                        jsonResult += "\n";

                        JSONArray weather = jsonHelperGetJSONArray(jsonObject, "weather");

                        if (weather != null) {
                            for (int i = 0; i < weather.length(); i++) {
                                JSONObject thisWeather = weather.getJSONObject(i);
                                jsonResult += "weather " + i + ":\n";
                                jsonResult += "id: " + jsonHelperGetString(thisWeather, "id") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "main") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "description") + "\n";
                                jsonResult += "\n";
                            }
                        }
                        JSONObject main = jsonHelperGetJSONObject(jsonObject, "main");
                        if (main != null) {
                            jsonResult += "temp: " + jsonHelperGetString(main, "temp") + "\n";
                            jsonResult += "pressure: " + jsonHelperGetString(main, "pressure") + "\n";
                            jsonResult += "humidity: " + jsonHelperGetString(main, "humidity") + "\n";
                            jsonResult += "temp_min: " + jsonHelperGetString(main, "temp_min") + "\n";
                            jsonResult += "temp_max: " + jsonHelperGetString(main, "temp_max") + "\n";
                            jsonResult += "sea_level: " + jsonHelperGetString(main, "sea_level") + "\n";
                            jsonResult += "grnd_level: " + jsonHelperGetString(main, "grnd_level") + "\n";
                            jsonResult += "\n";
                        }
                        jsonResult += "visibility: " + jsonHelperGetString(jsonObject, "visibility") + "\n";
                        jsonResult += "\n";

                        JSONObject wind = jsonHelperGetJSONObject(jsonObject, "wind");
                        if (wind != null) {
                            jsonResult += "wind:\n";
                            jsonResult += "speed: " + jsonHelperGetString(wind, "speed") + "\n";
                            jsonResult += "deg: " + jsonHelperGetString(wind, "deg") + "\n";
                            jsonResult += "\n";
                        }

                    } else if (cod.equals("404")) {
                        String message = jsonHelperGetString(jsonObject, "message");
                        jsonResult += "cod 404: " + message;
                    }
                } else {
                    jsonResult += "cod == null\n";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                jsonResult += e.getMessage();
            }
            return jsonResult;
        }

        /*
            Helper methods for accessing specific json entities
         */
        private String jsonHelperGetString(JSONObject obj, String k) {
            String val = null;
            try {
                val = obj.getString(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return val;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k) {
            JSONObject o = null;

            try {
                o = obj.getJSONObject(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k) {
            JSONArray a = null;
            try {
                a = obj.getJSONArray(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return a;
        }
    }
        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            Fragment fragment = null;

            switch (id) {
                case R.id.nav_name:
                    fragment = new ByNameFragment();
                    break;
                case R.id.nav_id:
                    fragment = new ByCityIDFragment();
                    break;
                case R.id.nav_zip:
                    fragment = new ByZipCodeFragment();
                    break;
                case R.id.nav_coordinates:
                    fragment = new ByCoordinatesFragment();
                    break;
            }

            if (id == R.id.nav_about) {
                displayAboutDialog();
            } else {
                switchFragment(fragment);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        private void switchFragment(Fragment fragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }

        private void displayAboutDialog() {
            new AlertDialog.Builder(this)
                    .setTitle("About Weather App")
                    .setMessage("Version 1.0\nRicardo Euán\nricardeuan@gmail.com")
                    .setNeutralButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }
