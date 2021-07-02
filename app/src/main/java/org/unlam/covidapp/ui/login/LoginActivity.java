package org.unlam.covidapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.unlam.covidapp.R;
import org.unlam.covidapp.Services.ServiceEvent;
import org.unlam.covidapp.Services.ServiceLogin;
import org.unlam.covidapp.dto.SoaEventRequest;
import org.unlam.covidapp.dto.SoaEventResponse;
import org.unlam.covidapp.dto.SoaRegisterRequest;
import org.unlam.covidapp.dto.SoaRegisterResponse;
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
    private TextView TxtUsuario;
    private TextView TxtPass;
    private String token;
    private String tokenRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.input_email);
        editPass = findViewById(R.id.input_password);
        TxtUsuario = findViewById(R.id.TxtUsuario);
        TxtPass = findViewById(R.id.TxtPass);

        cargarPreferencias();

        Button button = findViewById(R.id.btnIniciarSesion);
        button.setOnClickListener( v -> {
            guardarPreferencias();
            SoaRegisterRequest request = new SoaRegisterRequest();
            request.setEmail(editEmail.getText().toString());
            request.setPassword(editPass.getText().toString());

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://so-unlam.net.ar/api/")
                        .build();

                ServiceLogin serviceLogin = retrofit.create(ServiceLogin.class);
                Call<SoaRegisterResponse> call = serviceLogin.login(request);
                call.enqueue(new Callback<SoaRegisterResponse>() {
                    @Override
                    public void onResponse(Call<SoaRegisterResponse> call, Response<SoaRegisterResponse> response) {

                        if (response.isSuccessful()) {
                            token = response.body().getToken();
                            tokenRefresh=response.body().getToken_refresh();

                            Toast.makeText(LoginActivity.this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ShakeActivity.class);
                            intent.putExtra("token_refresh",tokenRefresh);
                            startActivity(intent);
                            Log.e(TAG, "SESION INICIADA");
                        } else {
                            Toast.makeText(LoginActivity.this, "Los datos ingresados no son correctos", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "SESION NO INICIADA");

                        }
                    }

                    @Override
                    public void onFailure(Call<SoaRegisterResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });

            } else {
                Toast.makeText(LoginActivity.this, "No hay conexión", Toast.LENGTH_SHORT).show();
            }

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
    private void cargarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("Credenciales",Context.MODE_PRIVATE);
        String user = preferences.getString("user","No existe la información");
        String pass = preferences.getString("pass","No existe la información");
        TxtUsuario.setText(user);
        TxtPass.setText(pass);
    }


    private void guardarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("Credenciales",Context.MODE_PRIVATE);
        String usuario = editEmail.getText().toString();
        String pass = editPass.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",usuario);
        editor.putString("pass",pass);

        TxtUsuario.setText(usuario);
        TxtPass.setText(pass);

        editor.commit();
    }
}
