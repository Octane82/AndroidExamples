package com.everlapp.androidexamples.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.everlapp.androidexamples.R;

import java.util.List;

public class SensorsActivity extends AppCompatActivity {

    private TextView tvSensors;

    private SensorManager sensorManager;
    private List<Sensor> sensors;

    private Sensor sensorLight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        tvSensors = findViewById(R.id.tvSensors);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }


    public void onClickSensorListener(View view) {
        sensorManager.unregisterListener(listenerLight, sensorLight);

        StringBuilder sb = new StringBuilder();
        for (Sensor sensor : sensors) {
            sb.append("Name: ").append(sensor.getName())
                    .append("\nType: ").append(sensor.getType())
                    .append("\nVendor: ").append(sensor.getVendor())
                    .append("\nVersion: ").append(sensor.getVersion())
                    .append("\nMax range: ").append(sensor.getMaximumRange())
                    .append("\nResolution: ").append(sensor.getResolution())
                    .append("\n/* ------------------------------------------------ */\n");

            tvSensors.setText(sb.toString());


        }
    }

    public void onClickSensorLight(View view) {
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);

    }



    SensorEventListener listenerLight = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            tvSensors.setText(String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

}
