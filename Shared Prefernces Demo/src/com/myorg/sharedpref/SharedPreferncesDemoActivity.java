package com.myorg.sharedpref;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SharedPreferncesDemoActivity extends Activity {
	EditText txtSet, txtGet;
	Button btnSet, btnGet;
	
	SharedPreferences preferences;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		txtSet = (EditText) findViewById(R.id.etValueToSave);
		txtGet = (EditText) findViewById(R.id.etValueToGet);

		btnSet = (Button) findViewById(R.id.btnSet);
		btnGet = (Button) findViewById(R.id.btnGet);

		btnSet.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				SharedPreferences.Editor editor=preferences.edit();
				editor.putString("value", txtSet.getText().toString());
				editor.putBoolean("hasvalue", true);
				editor.commit();
				
			}
		});

		btnGet.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String myPrefValue=preferences.getString("value", "No Value");
				boolean hasValue=preferences.getBoolean("hasvalue", false);
				txtGet.setText(myPrefValue+hasValue);

			}
		});

	}
}