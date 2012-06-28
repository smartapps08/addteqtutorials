package com.myorg.mediaservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FakePlayerService extends Service {
	public static final String EXTRA_PLAYLIST="EXTRA_PLAYLIST";
	public static final String EXTRA_SHUFFLE="EXTRA_SHUFFLE";
	
	private boolean isPlaying=false;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String playlist=intent.getStringExtra(EXTRA_PLAYLIST);
		boolean useShuffle=intent.getBooleanExtra(EXTRA_SHUFFLE, false);
		
		playMediaPlayer(playlist, useShuffle);
		return START_NOT_STICKY;
	}

	private void playMediaPlayer(String playlist, boolean useShuffle) {
		if(!isPlaying)
		{
			Log.d("MEDIA", "-------------PLAY--------------");
			isPlaying=true;
		}
	}
	
	@Override
	public void onDestroy() {
		stopMediaPlayer();
	}

	private void stopMediaPlayer() {
		if(isPlaying)
		{
			Log.d("MEDIA", "-------------STOP--------------");
			isPlaying=false;
		}
	}
	
}
