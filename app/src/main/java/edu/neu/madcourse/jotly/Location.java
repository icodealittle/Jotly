package edu.neu.madcourse.jotly;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;

public class Location extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    private Button locationBtn;
    private TextView locationTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationBtn = findViewById(R.id.locationbtn);
        locationTV = findViewById(R.id.locationTV);

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(Location.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Location.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Location method
                getLocation();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,
                    5, Location.this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        try {
            Geocoder geocoder = new Geocoder(Location.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            locationTV.setText("Location: " + address);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onFlushComplete(int requestCode) {
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }
}