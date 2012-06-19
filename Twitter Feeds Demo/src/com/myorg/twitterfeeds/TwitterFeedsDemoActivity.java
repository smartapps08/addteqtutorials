package com.myorg.twitterfeeds;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

public class TwitterFeedsDemoActivity extends ListActivity {

	ProgressDialog pd;
	ArrayList<String> myTweets;
	ArrayAdapter<String> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myTweets=new ArrayList<String>();
		//pd = ProgressDialog.show(getApplicationContext(), "", "Fetching your tweets...");
		RequestThread t = new RequestThread();
		t.start();
	}

	DefaultHttpClient client = new DefaultHttpClient();

	class RequestThread extends Thread {
		public void run() {

			String requestURLString = "https://api.twitter.com/1/statuses/user_timeline.json?include_entities=true&include_rts=true&screen_name=ahsan1808&count=20";
			// send request
			HttpGet getReq = new HttpGet(requestURLString);
			try {
				HttpResponse getResp = client.execute(getReq);
				final int statuscode = getResp.getStatusLine().getStatusCode();
				if (statuscode == HttpStatus.SC_OK) {
					HttpEntity entity = getResp.getEntity();
					if (entity != null) {
						String jsonString = EntityUtils.toString(entity);
						Log.d("JSON", jsonString);
						JSONArray jsonArray = new JSONArray(jsonString);
						int count = jsonArray.length();

						for (int i = 0; i < count; i++) {
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							String source=jsonObj.getString("source");
							JSONObject userObj = jsonObj.getJSONObject("user");
						
							String name = userObj.getString("name");
							Log.d("Name", name);
							String tweet = source + "--" + name;
							myTweets.add(tweet);
						}

					}
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// parse json

			handler.sendEmptyMessage(0);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//pd.dismiss();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					TwitterFeedsDemoActivity.this,
					android.R.layout.simple_list_item_1, myTweets);
			setListAdapter(adapter);

		}
	};

}