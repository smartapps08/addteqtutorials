package com.myorg.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WakeUpIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm goes Off", Toast.LENGTH_LONG).show();
		
		//start that service
		// you can even start an activity
	}
}
