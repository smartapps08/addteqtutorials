package com.myorg.osm;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.maps.MapActivity;

public class MyOSMDroidActivity extends MapActivity {
	RelativeLayout relLayout;
	
	//OpenStreetMapView
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        relLayout=(RelativeLayout)findViewById(R.id.relative2);
        
    }
    
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}