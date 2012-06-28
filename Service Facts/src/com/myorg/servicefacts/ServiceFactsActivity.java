package com.myorg.servicefacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ServiceFactsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }
    
    public void start()
    {
    	Intent intentToService=new Intent(this, EmptyService.class);
    	startService(intentToService);
    	
    }
    
    
    public void stop()
    {
    	Intent intentToService=new Intent(this, EmptyService.class);
    	stopService(intentToService);
    	
    }
    
}