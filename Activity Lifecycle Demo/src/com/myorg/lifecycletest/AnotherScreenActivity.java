package com.myorg.lifecycletest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnotherScreenActivity extends Activity {
	Button backButton;
	TextView txtTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.another);
		
		backButton=(Button)findViewById(R.id.btnBack);
		txtTitle=(TextView)findViewById(R.id.textView1);
		
		String retrievedStr=getIntent().getStringExtra("title");
		txtTitle.setText(retrievedStr);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(AnotherScreenActivity.this, MainScreenActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
	}
}
