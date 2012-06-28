package com.myorg.servicefacts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EmptyService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
		
//		return Service.START_STICKY;
//		return Service.START_NOT_STICKY;
//		return Service.START_REDELIVER_INTENT;
	}
	
	
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
