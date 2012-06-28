package com.myorg.mediaservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MediaPlayerServiceActivity extends Activity {
	Button btnPlay, btnStop;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnPlay=(Button)findViewById(R.id.btnPlay);
        btnStop=(Button)findViewById(R.id.btnStop);
        
        btnPlay.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i=new Intent(MediaPlayerServiceActivity.this, FakePlayerService.class);
				i.putExtra(FakePlayerService.EXTRA_PLAYLIST, "main playlist");
				i.putExtra(FakePlayerService.EXTRA_SHUFFLE, true);
				startService(i);
				
			}
		});
        
        btnStop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i=new Intent(MediaPlayerServiceActivity.this, FakePlayerService.class);
				stopService(i);
			}
		});
        
    }
}