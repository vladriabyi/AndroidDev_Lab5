package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;

    private CompassView compassView;
    private TextView angleDirectionText;

    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private boolean hasAccelerometer = false;
    private boolean hasMagnetometer = false;

    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        compassView = findViewById(R.id.compassView);
        angleDirectionText = findViewById(R.id.angleDirectionText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
            hasAccelerometer = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
            hasMagnetometer = true;
        }

        if (hasAccelerometer && hasMagnetometer) {
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer);
            SensorManager.getOrientation(rotationMatrix, orientation);

            float azimuthInRadians = orientation[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);
            if (azimuthInDegrees < 0) {
                azimuthInDegrees += 360;
            }

            compassView.updateDirection(azimuthInDegrees);
            updateAngleDirectionText(azimuthInDegrees);
        }
    }

    private void updateAngleDirectionText(float azimuth) {
        String direction;
        if (azimuth >= 337.5 || azimuth < 22.5) {
            direction = "Північ";
        } else if (azimuth >= 22.5 && azimuth < 67.5) {
            direction = "ПнС";
        } else if (azimuth >= 67.5 && azimuth < 112.5) {
            direction = "Схід";
        } else if (azimuth >= 112.5 && azimuth < 157.5) {
            direction = "ПдС";
        } else if (azimuth >= 157.5 && azimuth < 202.5) {
            direction = "Південь";
        } else if (azimuth >= 202.5 && azimuth < 247.5) {
            direction = "ПдЗ";
        } else if (azimuth >= 247.5 && azimuth < 292.5) {
            direction = "Захід";
        } else {
            direction = "ПнЗ";
        }
        angleDirectionText.setText(String.format("%.0f° %s", azimuth, direction));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Не використовується
    }
} 