package com.myorg.dmdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DownloadManagerDemoActivity extends Activity {
	Button start, cancel;
	long reference;
	DownloadManager manager;
	IntentFilter filter = new IntentFilter(
			DownloadManager.ACTION_DOWNLOAD_COMPLETE);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

		start = (Button) findViewById(R.id.button1);
		cancel = (Button) findViewById(R.id.button2);
		registerReceiver(receiver, filter);

		start.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = connectivityManager.getActiveNetworkInfo();

				if (info != null) {
					if (info.isConnected()) {
						String pathOfFile = "http://developer.android.com/shareables/icon_templates-v4.0.zip";
						Uri uri = Uri.parse(pathOfFile);
						DownloadManager.Request request = new Request(uri);
						request.setTitle("developer.android.com");
						request.setDescription("Icon templates");
						request.setDestinationInExternalFilesDir(
								DownloadManagerDemoActivity.this,
								Environment.DIRECTORY_DOWNLOADS, "icon.zip");

						// request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
						reference = manager.enqueue(request);

					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								DownloadManagerDemoActivity.this);
						builder.setTitle("Error");
						builder.setMessage("Internet is not connected");
						builder.show();
					}
				}
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				manager.remove(reference);
			}
		});
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			long refCompleted = intent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			if (refCompleted == reference) {
				// downloading completed
				// Toast.makeText(getApplicationContext(),
				// "Downloading Completed!", Toast.LENGTH_LONG).show();

				Query myDownloadQuery = new Query();
				myDownloadQuery.setFilterById(reference);

				Cursor myDownload = manager.query(myDownloadQuery);

				if (myDownload.moveToFirst()) {
					int fileNameId = myDownload
							.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
					String fileUri = myDownload.getString(fileNameId);
					Toast.makeText(getApplicationContext(),
							"Download Completed: " + fileUri, Toast.LENGTH_LONG)
							.show();
				}

				myDownload.close();

			}

		}
	};
	
}