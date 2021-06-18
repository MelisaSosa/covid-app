package org.unlam.covidapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import org.unlam.covidapp.R;

import java.util.List;

import io.paperdb.Paper;

public class RegistrarPatronActivity extends AppCompatActivity {

    private String save_pattern_key = "pattern_code";
    private String final_pattern = "";
    private PatternLockView mPatternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.getApplicationContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = (level * 100) / (float)scale;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Batería");
        alertDialog.setMessage("La batería restante es de " + batteryPct + "%");
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        if(save_pattern != null && !save_pattern.equals("null")) {
            setContentView(R.layout.activity_principal);
            mPatternLockView = findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
                    if(final_pattern.equals(save_pattern)){
                        Toast.makeText(RegistrarPatronActivity.this, "Patrón correcto!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrarPatronActivity.this, LoginActivity.class);
                        startActivity(intent);


                    }else{ Toast.makeText(RegistrarPatronActivity.this, "Patrón incorrecto!", Toast.LENGTH_SHORT).show();}


                }

                @Override
                public void onCleared() {

                }
            });
        } else {
            setContentView(R.layout.activity_main);
            mPatternLockView = findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils .patternToString(mPatternLockView,pattern);

                }

                @Override
                public void onCleared() {

                }
            });

            Button btnSetup = findViewById(R.id.btnGuardarPatron);
            btnSetup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(save_pattern_key, final_pattern);
                    Toast.makeText(RegistrarPatronActivity.this, "Patrón guardado correctamente!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrarPatronActivity.this, LoginPatronActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}