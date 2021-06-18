package org.unlam.covidapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.unlam.covidapp.R;
import org.unlam.covidapp.Services.ServiceRegistro;
import org.unlam.covidapp.dto.SoaRegisterRequest;
import org.unlam.covidapp.dto.SoaRegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {

    private static String TAG = RegistroActivity.class.getName();

    private EditText editName;
    private EditText editLastName;
    private EditText editDNI;
    private EditText editEmail;
    private EditText editPass;
    private EditText editComission;
    private EditText editGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editName = findViewById(R.id.input_name);
        editLastName= findViewById(R.id.input_Lastname);
        editDNI = findViewById(R.id.input_dni);
        editEmail = findViewById(R.id.input_email);
        editPass = findViewById(R.id.input_password);
        editComission = findViewById(R.id.input_commission);
        editGroup = findViewById(R.id.input_group);

        Button button = findViewById(R.id.send_request);
        button.setOnClickListener( v -> {
            SoaRegisterRequest request = new SoaRegisterRequest();
            request.setEnv("TEST");
            try {

                request.setName(editName.getText().toString());
                request.setLastname(editLastName.getText().toString());
                request.setDni(Long.parseLong(editDNI.getText().toString()));
                request.setEmail(editEmail.getText().toString());
                request.setPassword(editPass.getText().toString());
                request.setCommission(Long.parseLong(editComission.getText().toString()));
                request.setGroup(Long.parseLong(editGroup.getText().toString()));
            }
            catch (Exception e)
            {
                Toast.makeText(RegistroActivity.this, "Ingrese los datos correctamente", Toast.LENGTH_SHORT).show();
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://so-unlam.net.ar/api/")
                        .build();
                ServiceRegistro serviceRegistro = retrofit.create(ServiceRegistro.class);

                Call<SoaRegisterResponse> call = serviceRegistro.register(request);
                call.enqueue(new Callback<SoaRegisterResponse>() {
                    @Override
                    public void onResponse(Call<SoaRegisterResponse> call, Response<SoaRegisterResponse> response) {

                        if (response.isSuccessful()) {
                            Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            //TextView textEnv = findViewById(R.id.text_env);
                            //  TextView textToken = findViewById(R.id.text_token);
                            //  TextView textTokenRefresh = findViewById(R.id.text_token_refresh);
                            Toast.makeText(RegistroActivity.this, "Te has registrado correctamente", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "TODO OK");

                        } else {
                            Log.e(TAG, "TODO MAL");
                            Toast.makeText(RegistroActivity.this, "Ups! Revisa los datos nuevamente", Toast.LENGTH_SHORT).show();
                        }
                        Log.e(TAG, "Mensaje finalizado");
                    }

                    @Override
                    public void onFailure(Call<SoaRegisterResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });

            } else {
                Toast.makeText(RegistroActivity.this, "No hay conexión", Toast.LENGTH_SHORT).show();
            }



        });

    }
}
