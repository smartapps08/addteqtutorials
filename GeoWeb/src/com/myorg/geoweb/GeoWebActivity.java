package com.myorg.geoweb;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class GeoWebActivity extends Activity implements LocationListener {
	WebView webview;
	LocationManager manager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webview = (WebView) findViewById(R.id.webview);

		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new Locater(), "locater");
		webview.loadUrl("file:///android_asset/geoweb.html");
	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
				10.0f, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.removeUpdates(this);
	}

	public void onLocationChanged(Location location) {
		double latitude=location.getLatitude();
		double longitude=location.getLongitude();
		String jsCall="javascript:whereami("+latitude+","+longitude+")";
		webview.loadUrl(jsCall);
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public class Locater {
		public String getLocation() throws JSONException {
			Location loc = manager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (loc == null) {
				return null;
			}
			JSONObject json = new JSONObject();

			json.put("lat", loc.getLatitude());
			json.put("lon", loc.getLongitude());

			String locationData = json.toString();
			Log.d("LOCATION", locationData);
			return locationData;
		}
	}
}