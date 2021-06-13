package org.unlam.covidapp.ui.shake;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocationListener implements LocationListener {

    private ShakeActivity activity;

    public MyLocationListener(ShakeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        activity.locationText.setText("");

        double lowerLeftLatitude = location.getLatitude();
        double lowerLeftLongitude = location.getLongitude();

        String addressLine = null;
        String hospital = null;
        Geocoder gcd = new Geocoder(activity.getApplicationContext(), Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                addressLine = addresses.get(0).getAddressLine(0);
            }

            addresses = gcd.getFromLocationName("hospital", 5, lowerLeftLatitude, lowerLeftLongitude, lowerLeftLatitude, lowerLeftLongitude);
            addresses.addAll(gcd.getFromLocationName("clinica", 5, lowerLeftLatitude, lowerLeftLongitude, lowerLeftLatitude, lowerLeftLongitude));


        } catch (IOException e) {
            e.printStackTrace();
        }



        activity.locationText.setText("Estas en: " + addressLine);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
