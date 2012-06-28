package com.myorg.anotherservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServiceExampleActivity extends Activity {
	Button start, stop;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start=(Button)findViewById(R.id.button1);
        stop=(Button)findViewById(R.id.button2);
    
        start.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i=new Intent(ServiceExampleActivity.this, PlayerService.class);
				i.putExtra(PlayerService.EXTRA_PLAYLIST, "main");
				i.putExtra(PlayerService.EXTRA_SHUFFLE, true);
				startService(i);
			}
		});
        
        stop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i=new Intent(ServiceExampleActivity.this, PlayerService.class);
				stopService(i);
			}
		});
    }
}