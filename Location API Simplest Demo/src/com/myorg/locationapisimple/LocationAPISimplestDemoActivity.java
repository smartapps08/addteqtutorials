package com.myorg.locationapisimple;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;

public class LocationAPISimplestDemoActivity extends Activity implements
		LocationListener {
	private LocationManager locationManager;
	private String provider;
	private TextView txtOutput;
	private String[] statusArray = { "Out of Service",
			"Temporarily Unavailable", "Available" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txtOutput = (TextView) findViewById(R.id.txtOutput);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// gps, network and passive
		// provider = LocationManager.GPS_PROVIDER;
		List<String> providerList = locationManager.getProviders(true);
		for (String provider : providerList) {
			printProviderDetails(provider);
		}
		
//		How to define criteria and select the provider based on criteria
		Criteria criteria=new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(false);
		
		provider=locationManager.getBestProvider(criteria, true);
		
		
//		provider = locationManager.getBestProvider(criteria, false);
		
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			provider=LocationManager.GPS_PROVIDER;
		}
		else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			provider=LocationManager.NETWORK_PROVIDER;
		}
		else
		{
			provider=LocationManager.PASSIVE_PROVIDER;
		}
		txtOutput.append("\n Selected Provider: "+provider);
		setProximityAlert();
		
		//------------------------------
		IntentFilter filter=new IntentFilter("OUR_OWN_PROX_ALERT");
		registerReceiver(new PoximityIntentReceiver(), filter);
		
		
		//-------------------------------
		
	}
	
	private void printProviderDetails(String provider)
	{
		LocationProvider info=locationManager.getProvider(provider);
		txtOutput.append("\n"+info.getName()+" "+info.getPowerRequirement()+" "+info.getAccuracy());
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 1000, 0.0f, this);

		Location currentLocation = locationManager
				.getLastKnownLocation(provider);
		if (currentLocation != null) {
			double latitude = currentLocation.getLatitude();
			double longitude = currentLocation.getLongitude();
			String provider = currentLocation.getProvider();
			txtOutput.append("\nLocation update from " + provider + ": Lat:"
					+ latitude + " Lon: " + longitude + " Time:"
					+ currentLocation.getTime());
		} else {
			txtOutput.append("\nLocation data not available!");
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	public void onLocationChanged(Location location) {
		if (location != null) {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			String provider = location.getProvider();
			txtOutput.append("\n\nLocation update from " + provider + ": Lat:"
					+ latitude + " Lon: " + longitude + " Time:"
					+ location.getTime());
			

		}

	}

	public void onProviderDisabled(String provider) {
		txtOutput.append("\nProvider " + provider + " has been disabled");
	}

	public void onProviderEnabled(String provider) {
		txtOutput.append("\nProvider " + provider + " has been enabled");
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		txtOutput.append("\nProvider " + provider + " has bchanged its status: "
				+ statusArray[status]);

	}
	
	
	private static final String OUR_OWN_PROX_ALERT="com.myorg.locationapisimple.alert";
	
	private void setProximityAlert()
	{
		double lat=73.145678;
		double lon=0.510678;
		float radius=100.0f;
		long expiration=-1; // do not expire
		
		Intent intent=new Intent(OUR_OWN_PROX_ALERT);
		PendingIntent proximityIntent=PendingIntent.getBroadcast(LocationAPISimplestDemoActivity.this, -1, intent, 0);
		
		locationManager.addProximityAlert(lat, lon, radius, expiration, proximityIntent);
	}
}