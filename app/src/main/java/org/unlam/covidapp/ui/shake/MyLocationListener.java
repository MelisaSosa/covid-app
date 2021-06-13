package org.unlam.covidapp.ui.shake;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.view.View;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
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

        activity.latitud = location.getLatitude();
        activity.longitud = location.getLongitude();

        String addressLine = null;
        Geocoder gcd = new Geocoder(activity.getApplicationContext(), Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                addressLine = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        activity.locationText.setText("Estas en: " + addressLine);
        activity.mostrarHospitalesButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public ArrayList<String> getLugarCercano(String nombre, double latitud, double longitud) {
        Geocoder gcd = new Geocoder(activity.getApplicationContext(), Locale.getDefault());

        List<Address> lugaresAdd = null;
        try {
            lugaresAdd = gcd.getFromLocationName(nombre, 5, latitud, longitud, latitud, longitud);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        final ArrayList<String> lugares = new ArrayList<>();
        if(!lugaresAdd.isEmpty()) {
            for(Address a : lugaresAdd) {
                lugares.add(a.getAddressLine(0));
            }
        }

        return lugares;
    }
}
