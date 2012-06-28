package com.myorg.downloaderdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DownloaderDemoActivity extends Activity {
	Button btnDownload;
	TextView textView;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		textView=(TextView)findViewById(R.id.textView1);

		btnDownload = (Button) findViewById(R.id.btnDownload);
		btnDownload.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				btnDownload.setEnabled(false);
				Intent i = new Intent(DownloaderDemoActivity.this,
						DownloaderService.class);
				i.setData(Uri
						.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip"));
				i.putExtra(DownloaderService.EXTRA_MESSENGER, new Messenger(handler));
				startService(i);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			textView.setText("Download Complete");
			btnDownload.setEnabled(true);
			Toast.makeText(getApplicationContext(), "Download Complete",
					Toast.LENGTH_LONG).show();
		}
	};
}