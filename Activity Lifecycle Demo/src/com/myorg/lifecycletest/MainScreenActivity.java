package com.myorg.lifecycletest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainScreenActivity extends Activity {
	
	Button nextButton;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Toast.makeText(getApplicationContext(), "MainScreen: onCreate()",
				Toast.LENGTH_LONG).show();
		nextButton=(Button)findViewById(R.id.btnNext);
		//nextButton.setText(text)
		
		
		
		
	}
	
	public void nextClick(View v) {
		// code to start our Another Activity
		Intent intent=new Intent(MainScreenActivity.this, AnotherScreenActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("title", "This is the value of the string from main screen");
		intent.putExtra("subtitle", "This is the sub title");
		startActivity(intent);
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Toast.makeText(getApplicationContext(), "MainScreen: onStart()",
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getApplicationContext(), "MainScreen: onResume()",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Toast.makeText(getApplicationContext(), "MainScreen: onPause()",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Toast.makeText(getApplicationContext(), "MainScreen: onStop()",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "MainScreen: onDestroy()",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Toast.makeText(getApplicationContext(), "MainScreen: onRestart()",
				Toast.LENGTH_LONG).show();
	}
}