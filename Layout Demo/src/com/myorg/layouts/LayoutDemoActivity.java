package com.myorg.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutDemoActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_table);
//		TextView label = new TextView(this);
//		label.setText("This is a photo");
//		label.setTextSize(20);
//		label.setGravity(Gravity.CENTER_HORIZONTAL);
//
//		ImageView pic = new ImageView(this);
//		pic.setImageResource(R.drawable.ic_launcher);
//		pic.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT));
//
//		LinearLayout ll=new LinearLayout(this);
//		ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//		ll.setGravity(Gravity.CENTER);
//		ll.setOrientation(LinearLayout.VERTICAL);
//		
//		ll.addView(label);
//		ll.addView(pic);
//		
//		setContentView(ll);
		
//		pic.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "I am a cool droid", Toast.LENGTH_LONG).show();
//				
//			}
//		});
		
		
	}
}