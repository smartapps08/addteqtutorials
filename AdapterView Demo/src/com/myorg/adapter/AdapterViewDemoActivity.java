package com.myorg.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

public class AdapterViewDemoActivity extends Activity {
	private String[] countryList = { "Bahama", "Bangladesh", "Belgium",
			"Bugeria", "United States", "United Kingdom" };

	private Spinner spinnerOS;// View
	private AutoCompleteTextView countrySelection;

	private ArrayAdapter<CharSequence> osAdapter;// adapter
	private ArrayAdapter<String> countryAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		countrySelection = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

		spinnerOS = (Spinner) findViewById(R.id.spinnerOS);
		osAdapter = ArrayAdapter.createFromResource(this, R.array.osList,
				R.layout.items);
		spinnerOS.setAdapter(osAdapter);
		spinnerOS.setOnItemSelectedListener(listener);

		countryAdapter = new ArrayAdapter<String>(this, R.layout.items,
				countryList);
		countrySelection.setAdapter(countryAdapter);

	}

	OnItemSelectedListener listener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parentView, View view,
				int position, long id) {
			Toast.makeText(getApplicationContext(),
					"Selected Value: " + position, Toast.LENGTH_LONG).show();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};

}