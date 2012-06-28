package com.myorg.servicebind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BoundService extends Service {

	private final IBinder binder=new MyBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("BoundService", "onBind");
		return binder;
	}
	
	public class MyBinder extends Binder
	{
		BoundService getService()
		{
			Log.d("BoundService", "getService");
			return BoundService.this;
		}
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("BoundService", "onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("BoundService", "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("BoundService", "onDestroy");
	}

}
