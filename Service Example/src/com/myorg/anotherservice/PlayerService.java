package com.myorg.anotherservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service {

	protected static final String EXTRA_PLAYLIST = "EXTRA_PLAYLIST";
	protected static final String EXTRA_SHUFFLE = "EXTRA_SHUFFLE";

	private boolean isPlaying=false;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		String playlist=intent.getStringExtra(EXTRA_PLAYLIST);
		boolean useShuffle=intent.getBooleanExtra(EXTRA_SHUFFLE, false);
		
		play(playlist, useShuffle);
		return START_NOT_STICKY;
	}
	
	private void play(String playlist, boolean useShuffle) {
		if(!isPlaying)
		{
			Log.d("MEDIA PLAYER", "------------Play------------");
			isPlaying=true;
		}
	}

	@Override
	public void onDestroy() {
		stop();
	}

	private void stop() {
		if(isPlaying)
		{
			Log.d("MEDIA PLAYER", "------------Stop------------");
			isPlaying=false;
		}
	}

}
