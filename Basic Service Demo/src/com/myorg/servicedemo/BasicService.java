package com.myorg.servicedemo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BasicService extends Service {

	Timer timer;
	int counter;
	NotificationManager nManager;
	PendingIntent pIntent;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(getApplicationContext(),
				"BasicService::onStartCommand()", Toast.LENGTH_LONG).show();
		return super.onStartCommand(intent, flags, startId);
		
		
		// sticky
		// non-sticky task
		// stopSelf()

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		nManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		pIntent=PendingIntent.getActivity(this, 0, new Intent(this, BasicServiceDemoActivity.class), 0);

		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				counter++;
				Notification notification=new Notification(R.drawable.ic_launcher, "From BasicService", System.currentTimeMillis());
				notification.setLatestEventInfo(BasicService.this, "Basic Service", "We are testing our service", pIntent);
				
				nManager.notify(counter, notification);
			
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 5000);

	}
	
//	Handler handler=new Handler()
//	{
//		public void handleMessage(android.os.Message msg) {
//			Toast.makeText(getApplicationContext(),
//					"Counter value: " + counter, Toast.LENGTH_LONG).show();
//			
//		}
//	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		timer.cancel();
	}

}
