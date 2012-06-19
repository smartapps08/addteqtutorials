package com.myorg.googleplaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class GooglePlacesDemoActivity extends ListActivity {
	private static final String TAG = GooglePlacesDemoActivity.class
			.getSimpleName();
	private Button btnShow;
	public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/search/xml?";
	public static final String API_KEY = "AIzaSyDCF20We1W_QrHtkb6fi1ckLnldlSYhb4c";

	ArrayList<String> poiList;
	ArrayAdapter<String> adapter;
	ProgressDialog pd;
	
	ConnectivityManager connectivityManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		poiList = new ArrayList<String>();
		
		connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=connectivityManager.getActiveNetworkInfo();
		
		if(info!=null)
		{
			if(info.isConnected())
			{
				pd = ProgressDialog.show(this, "", "Loading...");
				RequestThread t = new RequestThread();
				t.start();
			}
			else
			{
				//show a message "Internet connection is not available"
			}
		}
		else
		{
			//show a message "Internet connection is not available"
		}
		
		

	}

	public void processInputStream(InputStream is) {
		XmlPullParserFactory factory;

		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput(is, null);
			int eventType = xpp.getEventType();

			//
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG
						&& xpp.getName().equals("result")) {
					Log.e(TAG, "Inside the loop of parsing");
					eventType = xpp.next();
					String name = "";
					String lat = "";
					String lng = "";
					while (!(eventType == XmlPullParser.END_TAG && xpp
							.getName().equals("result"))) {
						if (eventType == XmlPullParser.START_TAG
								&& xpp.getName().equals("name")) {
							name = xpp.nextText();
							Log.e(TAG, name);
						}

//						eventType = xpp.next();
//						if (eventType == XmlPullParser.START_TAG
//								&& xpp.getName().equals("lat")) {
//							lat = xpp.nextText();
//							Log.e(TAG, lat);
//						}
//
//						eventType = xpp.next();
//						if (eventType == XmlPullParser.START_TAG
//								&& xpp.getName().equals("lng")) {
//							lat = xpp.nextText();
//							Log.e(TAG, lng);
//						}

						eventType = xpp.next();
						
					}
					String poi = name;// + " " + lat + " " + lng;
					Log.d(TAG, poi+"---------------------");
					poiList.add(poi);
					

					
				}
				eventType = xpp.next();
			}

		} catch (XmlPullParserException e) {
			Log.e(TAG, e.toString());
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		}

	}

	class RequestThread extends Thread {
		public void run() {
			double lat = -33.8670522;
			double lon = 151.1957362;
			int radius = 500;
			String type = "food";
			String name = "harbour";

			String requestURLString = GooglePlacesDemoActivity.BASE_URL
					+ "location=" + lat + "," + lon + "&radius=" + radius
					+ "&type=" + type + "&name=" + name + "&sensor=false&key="
					+ GooglePlacesDemoActivity.API_KEY;
			Log.d(TAG, requestURLString);
			try {
				URL url = new URL(requestURLString);
				// create a new HTTP Connection
				URLConnection connection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection) connection;

				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpsURLConnection.HTTP_OK) {
					InputStream in = httpConnection.getInputStream();
					processInputStream(in);

				}
				handler.sendEmptyMessage(0);

			} catch (MalformedURLException e) {
				Log.e(TAG, e.toString());
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pd.dismiss();
			if (poiList.size() > 0) {
				adapter = new ArrayAdapter<String>(
						GooglePlacesDemoActivity.this,
						android.R.layout.simple_list_item_1, poiList);
				setListAdapter(adapter);
			}
		}
	};
}