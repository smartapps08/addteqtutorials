package com.myorg.alarmmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlarmManagerDemoActivity extends Activity {

	EditText etTime, etInterval;
	Button btnSetAlarm, btnCancel;

	public static final String ALARM_INTENT_ACTION = "com.myorg.alarmmanager.WAKE_UP_ALARM";

	AlarmManager alarmManager;
	PendingIntent alarmIntent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etTime = (EditText) findViewById(R.id.etTimeStart);
		etInterval = (EditText) findViewById(R.id.etInterval);

		btnSetAlarm = (Button) findViewById(R.id.btnSetAlarm);
		btnCancel=(Button)findViewById(R.id.btnCancel);
		
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		btnSetAlarm.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				int alarmTime = Integer.parseInt(etTime.getText().toString());
				long firstTime = System.currentTimeMillis();
				Intent intentToFire = new Intent(ALARM_INTENT_ACTION);
				alarmIntent = PendingIntent.getBroadcast(
						AlarmManagerDemoActivity.this, 0, intentToFire, 0);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
						firstTime+alarmTime, alarmTime, alarmIntent);

				
			}
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				alarmManager.cancel(alarmIntent);
				
			}
		});

	}
}