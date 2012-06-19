package com.myorg.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DemoOneActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Thread t = new Thread() {
			public void run() {
				String myFeed = "https://maps.googleapis.com/maps/api/place/search/xml?location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&sensor=false&key=AIzaSyDCF20We1W_QrHtkb6fi1ckLnldlSYhb4c";
				try {
					URL url = new URL(myFeed);
					// Create a new HTTP URL connection
					URLConnection connection = url.openConnection();
					HttpURLConnection httpConnection = (HttpURLConnection) connection;
					int responseCode = httpConnection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						Log.d("In Thread", "Process IOStream");
						InputStream in = httpConnection.getInputStream();
						processStream(in);
						Log.d("In Thread", "Process IOStream");
					}
				} catch (MalformedURLException e) {
				} catch (IOException e) {
				}
			}
		};
		t.start();

	}

	private void processStream(InputStream inputStream) { // Create a new XML
															// Pull Parser.
		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			// Assign a new input stream.
			xpp.setInput(inputStream, null);
			int eventType = xpp.getEventType();
			// Continue until the end of the document is reached.
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Check for a start tag of the results tag.
				if (eventType == XmlPullParser.START_TAG
						&& xpp.getName().equals("result")) {
					eventType = xpp.next();
					String name = ""; // Process each result within the result
										// tag.
					String lat="";
					String lon="";
					while (!(eventType == XmlPullParser.END_TAG && xpp
							.getName().equals("result"))) { // Check for the
															// name tag within
															// the results tag.
						if (eventType == XmlPullParser.START_TAG
								&& xpp.getName().equals("name")) // Extract the
																	// POI name.
						{
							name = xpp.nextText();
							Log.d("NAME", name);
							
							
						}
						// Move on to the next tag.
						eventType = xpp.next();
						if (eventType == XmlPullParser.START_TAG
								&& xpp.getName().equals("lat")) // Extract the
																	// POI name.
						{
							lat=xpp.nextText();
							Log.d("Lat", lat);
						}
						eventType = xpp.next();
					}
					// Do something with each POI name.
				}
				// Move on to the next result tag.
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
		} catch (IOException e) {

		}
	}
}
