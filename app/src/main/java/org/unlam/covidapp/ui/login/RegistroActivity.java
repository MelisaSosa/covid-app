package org.unlam.covidapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.unlam.covidapp.R;
import org.unlam.covidapp.Services.ServiceRegistro;
import org.unlam.covidapp.dto.SoaRequest;
import org.unlam.covidapp.dto.SoaResponse;

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
            SoaRequest request = new SoaRequest();
            request.setEnv("TEST");
            request.setName(editName.getText().toString());
            request.setLastname(editLastName.getText().toString());
            request.setDni(Long.parseLong(editDNI.getText().toString()));
            request.setEmail(editEmail.getText().toString());
            request.setPassword(editPass.getText().toString());
            request.setCommission(Long.parseLong(editComission.getText().toString()));
            request.setGroup(Long.parseLong(editGroup.getText().toString()));

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://so-unlam.net.ar/api/")
                    .build();
            ServiceRegistro serviceRegistro = retrofit.create(ServiceRegistro.class);

            Call<SoaResponse> call = serviceRegistro.register(request);
            call.enqueue(new Callback<SoaResponse>() {
                @Override
                public void onResponse(Call<SoaResponse> call, Response<SoaResponse> response) {

                    if (response.isSuccessful()) {
                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //TextView textEnv = findViewById(R.id.text_env);
                      //  TextView textToken = findViewById(R.id.text_token);
                      //  TextView textTokenRefresh = findViewById(R.id.text_token_refresh);
                        Log.e(TAG, "TODO OK");
                    } else {
                        Log.e(TAG, "TODO MAL");
                        //Log.e(TAG, response.toString());
                    }
                    Log.e(TAG, "Mensaje finalizado");
                }

                @Override
                public void onFailure(Call<SoaResponse> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });

        });

    }
}
