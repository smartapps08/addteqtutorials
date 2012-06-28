package com.myorg.sensortest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorAvailabilityTestActivity extends Activity {
	TextView output, sensorValues;
	SensorManager sensorManager;
	List<Sensor> allSensors;
	List<Sensor> gyroscopes; 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        output=(TextView)findViewById(R.id.txtSensors);
        sensorValues=(TextView)findViewById(R.id.txtValues);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        allSensors=sensorManager.getSensorList(Sensor.TYPE_ALL);
        output.append("All Available Sensors");
        for (Sensor sensor : allSensors) {
			output.append("\n"+sensor.getName());
			
		}
        
        gyroscopes=sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        output.append("\n\nAll Available Sensors of type Gyroscope");
        for (Sensor sensor : gyroscopes) {
			output.append("\n"+sensor.getName());
		}
        
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        
    }
    
    SensorEventListener listener=new SensorEventListener() {
		
		public void onSensorChanged(SensorEvent event) {
			//value[0] X-axis acceleration
			//value[1] Y-axis acceleration
			//value[2] Z-axis acceleration
			String sensorData="X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2];
			sensorValues.setText(sensorData);
			
			
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	};
}