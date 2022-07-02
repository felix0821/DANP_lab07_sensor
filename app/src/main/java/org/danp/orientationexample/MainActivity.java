package org.danp.orientationexample;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
     sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);

    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    if (accelerometer != null) {
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    if (magneticField != null) {
        sensorManager.registerListener(sensorEventListener, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    }

    private SensorEventListener sensorEventListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            TextView metA01 = (TextView) findViewById(R.id.A01);
            TextView metA02 = (TextView) findViewById(R.id.A02);
            TextView metA03 = (TextView) findViewById(R.id.A03);

            TextView metM01 = (TextView) findViewById(R.id.M01);
            TextView metM02 = (TextView) findViewById(R.id.M02);
            TextView metM03 = (TextView) findViewById(R.id.M03);

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, accelerometerReading,
                        0, accelerometerReading.length);
                metA01.setText("("+accelerometerReading[0]+"°)");
                metA02.setText("("+accelerometerReading[1]+"°)");
                metA03.setText("("+accelerometerReading[2]+"°)");
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, magnetometerReading,
                        0, magnetometerReading.length);
                metM01.setText("("+magnetometerReading[0]+"°)");
                metM02.setText("("+magnetometerReading[1]+"°)");
                metM03.setText("("+magnetometerReading[2]+"°)");
            }

            SensorManager.getRotationMatrix(rotationMatrix, null,
                    accelerometerReading, magnetometerReading);
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "mRotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        // "mOrientationAngles" now has up-to-date information.
    }


}