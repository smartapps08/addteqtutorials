package com.myorg.downloaderdemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class DownloaderService extends IntentService {
	public static final String TAG = "DownloaderService";
	public static final String EXTRA_MESSENGER = "com.myorg.downloaddemo.EXTRA_MESSENGER";
	private HttpClient client;

	public DownloaderService() {
		super("Downloader Service");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		client = new DefaultHttpClient();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		client.getConnectionManager().shutdown();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, intent.getData().toString());
		HttpGet getMethod = new HttpGet(intent.getData().toString());

		int result = Activity.RESULT_CANCELED;
		try {
			ResponseHandler<byte[]> responseHandler = new ByteArrayResponseHandler();
			byte[] responseByte = client.execute(getMethod, responseHandler);

			File output = new File(Environment.getExternalStorageDirectory(),
					intent.getData().getLastPathSegment());
			if (output.exists()) {
				output.delete();
			}

			FileOutputStream fileOutputStream = new FileOutputStream(
					output.getPath());
			fileOutputStream.write(responseByte);
			fileOutputStream.close();
			result = Activity.RESULT_OK;
			Log.d(TAG, "File written in file system: " + output.getPath());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Bundle extras = intent.getExtras();
		if (extras != null) {
			Messenger messenger = (Messenger) extras.get(EXTRA_MESSENGER);
			Message msg = Message.obtain();

			msg.arg1 = result;

			try {
				Log.d(TAG, "Message to be sent");
				messenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}

}
