package com.myorg.servicebind;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ServiceBindingActivity extends Activity {
	private BoundService serviceRef;
	private Button bind, unbind;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		bind=(Button)findViewById(R.id.btnBind);
		unbind=(Button)findViewById(R.id.btnUnbind);
		
		bind.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent bindIntent=new Intent(ServiceBindingActivity.this, BoundService.class);
				bindService(bindIntent, mConnection, Context.BIND_AUTO_CREATE);
				
				// any public method from activity
			}
		});
		
		unbind.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				unbindService(mConnection);
				
			}
		});
	}

	// handle connection between activity and service
	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			Log.d("ServiceBindingActivity", "onServiceDisconnected");
			serviceRef = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("ServiceBindingActivity", "onServiceConnected");
			serviceRef = ((BoundService.MyBinder) service).getService();
		}
	};

}