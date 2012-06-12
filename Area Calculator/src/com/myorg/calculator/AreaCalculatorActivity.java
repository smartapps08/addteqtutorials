package com.myorg.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AreaCalculatorActivity extends Activity {// implements
														// OnClickListener {

	Button calculate, calculatePerimeter;
	EditText inputRadious;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		calculate = (Button) findViewById(R.id.button1);
		calculatePerimeter = (Button) findViewById(R.id.button2);
		inputRadious = (EditText) findViewById(R.id.editText1);

		calculate.setOnClickListener(areaOnClickListener);
		calculatePerimeter.setOnClickListener(periOnClickListener);

	}

	OnClickListener areaOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String radiousStr = inputRadious.getText().toString();
			double radious = Double.parseDouble(radiousStr);
			double area = Math.PI * radious * radious;

			Toast.makeText(getApplicationContext(), "Area: " + area,
					Toast.LENGTH_LONG).show();

		}
	};

	OnClickListener periOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String radiousStr = inputRadious.getText().toString();
			double radious = Double.parseDouble(radiousStr);
			double perimeter = 2 * Math.PI * radious;

			Toast.makeText(getApplicationContext(), "Perimeter: " + perimeter,
					Toast.LENGTH_LONG).show();

		}
	};

	
}