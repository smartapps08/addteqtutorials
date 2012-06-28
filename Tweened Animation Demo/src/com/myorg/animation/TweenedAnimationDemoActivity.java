package com.myorg.animation;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TweenedAnimationDemoActivity extends Activity implements
		AnimationListener {
	private Button btnRotate, btnScroll, btnFade;
	private TextView txtRotate, txtScroll, txtFade;
	View myView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnRotate = (Button) findViewById(R.id.btnRotate);
		btnScroll = (Button) findViewById(R.id.btnScroll);
		btnFade = (Button) findViewById(R.id.btnFade);

		txtRotate = (TextView) findViewById(R.id.txtRotatingText);
		txtScroll = (TextView) findViewById(R.id.txtScrollingText);
		txtFade = (TextView) findViewById(R.id.txtFadingText);

		myView = findViewById(R.id.myview);

		btnRotate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(
						TweenedAnimationDemoActivity.this, R.anim.myanimation);
				anim.setAnimationListener(TweenedAnimationDemoActivity.this);
				txtRotate.startAnimation(anim);
				//myView.startAnimation(anim);

			}
		});

		btnScroll.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Paint paint = new Paint();
				float measureTextCenter = paint.measureText(txtScroll.getText()
						.toString());
				Animation anim = new TranslateAnimation(0.0f,
						measureTextCenter, 0.0f, 0.0f);
				anim.setDuration(5000);
				anim.setInterpolator(new BounceInterpolator());
				myView.startAnimation(anim);
			}
		});

		btnFade.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				float from = 1.0f;
				float to = 0.0f;

				if (myView.getVisibility() == View.INVISIBLE) {
					from = to;
					to = 1.0f;
				}
				Animation anim = new AlphaAnimation(from, to);
				anim.setDuration(5000);
				anim.setAnimationListener(TweenedAnimationDemoActivity.this);
				anim.setRepeatCount(2);
				myView.startAnimation(anim);
			}
		});

	}

	public void onAnimationEnd(Animation animation) {
		Toast.makeText(getApplicationContext(), "Animation Ends",
				Toast.LENGTH_SHORT).show();
//		if (myView.getVisibility() == View.VISIBLE) {
//			myView.setVisibility(View.INVISIBLE);
//		} else {
//			myView.setVisibility(View.VISIBLE);
//		}
	}

	public void onAnimationRepeat(Animation animation) {

	}

	public void onAnimationStart(Animation animation) {
		Toast.makeText(getApplicationContext(), "Animation Started",
				Toast.LENGTH_SHORT).show();
	}
}