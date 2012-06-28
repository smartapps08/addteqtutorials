package com.myorg.mmapi;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SoundPool soundPool;
	private int soundId;
	private boolean isLoaded = false;

	// for MediaPlayer
	private MediaPlayer mp;

	// for Recorder
	private MediaRecorder recorder;
	private File audioFile = null;

	private AudioManager audioManager;
	Button playSample;
	Button playSampleWithMP, pauseSample, stopSample;

	Button startRecording, stopRecording;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		playSample = (Button) findViewById(R.id.btnPlay);
		playSampleWithMP = (Button) findViewById(R.id.btnPlayMP);
		pauseSample = (Button) findViewById(R.id.btnPause);
		stopSample = (Button) findViewById(R.id.btnStop);

		startRecording = (Button) findViewById(R.id.btnStartRecording);
		stopRecording = (Button) findViewById(R.id.btnStopRecording);
		stopRecording.setEnabled(false);

		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				isLoaded = true;

			}
		});

		soundId = soundPool.load(this, R.raw.robotnoise, 1);

		// from application res
		// mp=MediaPlayer.create(this, R.raw.robotnoise);
		// mp=new MediaPlayer();

		// URL of online audio
		// mp=MediaPlayer.create(this,
		// Uri.parse("http://example.com/audio/audio.mp3"));

		// SDcard/local files
		// mp=MediaPlayer.create(this,
		// Uri.parse("file:///sdcard/robotnoise.ogg"));

		// content provider (MediaStore)
		// mp=MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

		playSample.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				float actualVolume = audioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				float maxVolume = audioManager
						.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				float volume = actualVolume / maxVolume;

				if (isLoaded) {
					soundPool.play(soundId, volume, volume, 1, 0, 1f);
				}

			}
		});

		playSampleWithMP.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				mp = new MediaPlayer();
				try {
					mp.setDataSource("/sdcard/robotnoise.ogg");
					mp.prepare();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mp.start();

			}
		});

		pauseSample.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				mp.pause();

			}
		});

		stopSample.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				mp.stop();
				mp.release();

			}
		});

		startRecording.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startRecording.setEnabled(false);
				stopRecording.setEnabled(true);

				File dir = Environment.getExternalStorageDirectory();
				try {
					audioFile = File.createTempFile("mmapi", ".3gp", dir);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				recorder = new MediaRecorder();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				recorder.setOutputFile(audioFile.getAbsolutePath());
				try {
					recorder.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recorder.start();

			}
		});

		stopRecording.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startRecording.setEnabled(true);
				stopRecording.setEnabled(false);

				recorder.stop();
				recorder.release();
				addRecordingToMediaStore();
			}
		});

	}

	public void addRecordingToMediaStore() {
		ContentValues values = new ContentValues();
		long current = System.currentTimeMillis();
		values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
		values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
		values.put(MediaStore.Audio.Media.DATE_ADDED, current);
		values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());

		ContentResolver cr = getContentResolver();

		Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Uri newUri = cr.insert(base, values);

		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
		Toast.makeText(getApplicationContext(),
				"Media Data is inserted to a ContentProvider named MediaStore",
				Toast.LENGTH_LONG).show();
	}
}