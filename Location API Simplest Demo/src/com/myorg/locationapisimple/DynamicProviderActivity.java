package com.myorg.locationapisimple;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DynamicProviderActivity extends Activity {

	private LocationManager locationManager;
	private TextView txtOutput;

	private static int minUpdateTime = 0; // different values i.e 30 secs
	private static int minUpdateDistance = 0; // 10 m

	private Criteria criteria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txtOutput = (TextView) findViewById(R.id.txtOutput);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// expensive criteria
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(true);
		criteria.setBearingRequired(true);
		criteria.setSpeedRequired(true);
		criteria.setCostAllowed(true);

		// criteria.setHorizontalAccuracy(Criteria.)

	}

	@Override
	protected void onResume() {
		super.onResume();
		registerListener();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// unregister the listener
	}

	private void registerListener() {
		// unregister all
		unregisterAllListeners();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		String bestAvailableProvider = locationManager.getBestProvider(
				criteria, true);

		txtOutput.append("Best Provider"+ bestProvider);
		txtOutput.append("BEST AVAILABLE"+ bestAvailableProvider);
		
		
		
		if(bestProvider==null)
		{
			Log.e("BEST NULL", "No location providers are in this device fulfilling our criteria");
		}
		else if(bestProvider.equals(bestAvailableProvider))
		{
			locationManager.requestLocationUpdates(bestAvailableProvider, minUpdateTime, minUpdateDistance, bestAvailableProviderListener);
		}
		else
		{
			//locationManager
			if(bestAvailableProvider!=null)
			{
				locationManager.requestLocationUpdates(bestAvailableProvider, minUpdateTime, minUpdateDistance, bestAvailableProviderListener);
			}
			else
			{
				// not best available provider
				// so take all providers and request all of them
				List<String> allProviders=locationManager.getAllProviders();
				for (String provider : allProviders) {
					locationManager.requestLocationUpdates(provider, minUpdateTime, minUpdateDistance, bestProviderListener);
				}
				Log.e("TAG", "No Location Provider  is currently available to meet criteria");
			}
		
		}
			

	}

	private void unregisterAllListeners() {
		locationManager.removeUpdates(bestProviderListener);
		locationManager.removeUpdates(bestAvailableProviderListener);
		
	}
	
	private LocationListener bestAvailableProviderListener=new LocationListener() {
		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		public void onProviderDisabled(String provider) {
			registerListener();
			
		}
		
		public void onLocationChanged(Location location) {
			// react to location change
			reactToLocationChange(location);
			
		}

		
	};
	
	private LocationListener bestProviderListener=new LocationListener() {
		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		public void onProviderDisabled(String provider) {
			registerListener();
			
		}
		
		public void onLocationChanged(Location location) {
			reactToLocationChange(location);
			
		}
	};
	
	private void reactToLocationChange(Location location) {
		//		
	}

}
