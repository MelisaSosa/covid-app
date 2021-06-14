package org.unlam.covidapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.unlam.covidapp.R;
import org.unlam.covidapp.Services.ServiceLogin;
import org.unlam.covidapp.dto.SoaRequest;
import org.unlam.covidapp.dto.SoaResponse;
import org.unlam.covidapp.ui.shake.ShakeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = LoginActivity.class.getName();

    private EditText editEmail;
    private EditText editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_desbloqueada);
        editEmail = findViewById(R.id.input_email);
        editPass = findViewById(R.id.input_password);

        Button button = findViewById(R.id.btnIniciarSesion);
        button.setOnClickListener( v -> {
            SoaRequest request = new SoaRequest();
            request.setEmail(editEmail.getText().toString());
            request.setPassword(editPass.getText().toString());

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://so-unlam.net.ar/api/")
                    .build();
            ServiceLogin serviceLogin = retrofit.create(ServiceLogin.class);
            Call<SoaResponse> call = serviceLogin.login(request);
            call.enqueue(new Callback<SoaResponse>() {
                @Override
                public void onResponse(Call<SoaResponse> call, Response<SoaResponse> response) {

                    if (response.isSuccessful()) {
                        //PANTALLA DE APLICACION
                       // TextView textToken = findViewById(R.id.text_token);
                       // TextView textTokenRefresh = findViewById(R.id.text_token_refresh);
                        //Log.e(TAG, "LoginActivity Correcto");
                        Intent intent = new Intent(LoginActivity.this, ShakeActivity.class);
                        startActivity(intent);
                        Log.e(TAG, "TODO OK");
                    } else {
                        Log.e(TAG, "TODO MAL");

                    }
                    Log.e(TAG, "Mensaje finalizado");
                }

                @Override
                public void onFailure(Call<SoaResponse> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });

        });
        Button btnSetup = (Button)findViewById(R.id.btnRegistrarse);
        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
    });
    }
}
