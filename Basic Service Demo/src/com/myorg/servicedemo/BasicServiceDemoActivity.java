package com.myorg.servicedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BasicServiceDemoActivity extends Activity {
	Button btnStart, btnStop;
	Intent intent;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        intent=new Intent(this, BasicService.class);
        
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStop=(Button)findViewById(R.id.btnStop);
        
        btnStart.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startService(intent);
				
			}
		});
        
        btnStop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				stopService(intent);
				
			}
		});
        
    }
}