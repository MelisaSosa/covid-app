package org.unlam.covidapp.ui.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 3F;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener shakeListener;
    private long shakeTimestamp;
    private int shakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.shakeListener = listener;
    }

    public interface OnShakeListener {
        void onShake(int count);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shakeListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            float gravity = gX * gX + gY * gY + gZ * gZ;
            float gForce = (float) Math.sqrt(gravity);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();

                // reset the shake count after 3 seconds of no shakes
                if (shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    shakeCount = 0;
                }

                shakeTimestamp = now;
                shakeCount++;

                shakeListener.onShake(shakeCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //ignore
    }
}
