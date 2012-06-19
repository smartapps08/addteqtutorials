package com.myorg.geocoding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class GeocodingDemoActivity extends Activity implements LocationListener {
	private LocationManager locationManager;
	private String provider;
	private TextView txtOutput;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txtOutput = (TextView) findViewById(R.id.txtOutput);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		provider = LocationManager.GPS_PROVIDER;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
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

			// comes our Reverse Geocoding part
			// reverse geocoding
			// 1. we'll create a geocoder object
			Geocoder geocoder = new Geocoder(getApplicationContext(),
					Locale.getDefault());
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 10);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Address myAddress = addresses.get(0);
			txtOutput.append("Address: " + myAddress.getAddressLine(0) + "\n"
					+ myAddress.getAddressLine(1) + "\n"
					+ myAddress.getAdminArea() + " "
					+ myAddress.getCountryName());
			
			fwdGeocoding();

		} else {
			txtOutput.append("\nLocation data not available!");
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
			// reverse geocoding
			// 1. we'll create a geocoder object
			Geocoder geocoder = new Geocoder(getApplicationContext(),
					Locale.getDefault());
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 10);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Address myAddress = addresses.get(0);
			txtOutput.append("Address: " + myAddress.getAddressLine(0) + "\n"
					+ myAddress.getAddressLine(1) + "\n"
					+ myAddress.getAdminArea() + " "
					+ myAddress.getCountryName());

		}

	}

	public void onProviderDisabled(String provider) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	// forward geocoding
	public void fwdGeocoding() {
		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.US);
		String address = "160 Riverside Drive, New York, New York";

		List<Address> locations = null;

		try {
			locations = geocoder.getFromLocationName(address, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Address location = locations.get(0);

		txtOutput.append("Location for address: " + address + " is: Lat: "
				+ location.getLatitude() + " Lon:" + location.getLongitude());

		
	}

}