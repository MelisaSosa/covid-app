package org.unlam.covidapp.ui.shake;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.unlam.covidapp.R;
import org.unlam.covidapp.ui.hospitales.HospitalesActivity;

import java.util.ArrayList;

public class ShakeActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    public TextView locationText;
    public Button mostrarHospitalesButton;
    public double longitud;
    public double latitud;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        locationText = findViewById(R.id.locationText);
        mostrarHospitalesButton = findViewById(R.id.hospitales);

        mostrarHospitalesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLocationListener locationListener = new MyLocationListener(ShakeActivity.this);
                ArrayList<String> hospitalesCercanos = locationListener.getLugarCercano("hospital", latitud, longitud);
                ArrayList<String> clinicasCercanas = locationListener.getLugarCercano("clinica", latitud, longitud);

                Intent myIntent = new Intent(ShakeActivity.this, HospitalesActivity.class);
                myIntent.putStringArrayListExtra("hospitalesCercanos", hospitalesCercanos);
                myIntent.putStringArrayListExtra("clinicasCercanas", clinicasCercanas);
                ShakeActivity.this.startActivity(myIntent);
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });
    }

    private void handleShakeEvent(int count) {
        if (count == 1) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new MyLocationListener(ShakeActivity.this);

            if (validarGPSHabilitado()) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            } else {
                solicitarHabilitarGPS();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    private boolean validarGPSHabilitado() {
        int GPSEnabled = 0;

        try {
            GPSEnabled = Settings.Secure.getInt(this.getApplicationContext().getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }

        return GPSEnabled != Settings.Secure.LOCATION_MODE_OFF;
    }

    private void solicitarHabilitarGPS() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Ubicación");
        alertDialog.setMessage("Necesitas prender la ubicación para seguir utilizando la app");
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(onGPS);
            }
        });
        alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }
}
