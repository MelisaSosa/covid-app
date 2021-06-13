package org.unlam.covidapp.ui.hospitales;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.unlam.covidapp.R;

import java.util.List;

public class HospitalesActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitales);

        Bundle extras = getIntent().getExtras();
        List<String> hospitalesCercanos = extras.getStringArrayList("hospitalesCercanos");
        List<String> clinicasCercanas = extras.getStringArrayList("clinicasCercanas");

        final ListView listviewH = findViewById(R.id.hospitalesList);
        final ListView listviewC = findViewById(R.id.clinicasList);

        StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, hospitalesCercanos);
        listviewH.setAdapter(adapter);

        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, clinicasCercanas);
        listviewC.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

