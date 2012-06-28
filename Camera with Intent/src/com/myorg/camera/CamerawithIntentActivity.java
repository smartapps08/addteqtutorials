package com.myorg.camera;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CamerawithIntentActivity extends Activity {
	Button btnCapture;
	ImageView img;
	Uri outputFileUri;

	private static final int CAPTURE_PHOTO = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnCapture = (Button) findViewById(R.id.btnCapture);
		img = (ImageView) findViewById(R.id.imgPhoto);

		btnCapture.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// create an output file
				File file = new File(Environment.getExternalStorageDirectory(),
						"tutorial.jpg");
				outputFileUri = Uri.fromFile(file);

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

				startActivityForResult(intent, CAPTURE_PHOTO);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_PHOTO) {
			if (data != null) {
				if (data.hasExtra("data")) {
					Bitmap thumbnail = data.getParcelableExtra("data");
					img.setImageBitmap(thumbnail);
					Log.d("CAMERA", "Thumbnail available");
				}
			} else {
				Log.d("CAMERA", "Thumbnail NOT available");
				BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
				factoryOptions.inJustDecodeBounds=true;
				BitmapFactory.decodeFile(outputFileUri.getPath(), factoryOptions);
				
				int imageWidth=factoryOptions.outWidth;
				int imageHeight=factoryOptions.outHeight;
				
				int scaleFactor=Math.min(imageWidth/320, imageHeight/240);
				
				factoryOptions.inJustDecodeBounds=false;
				factoryOptions.inSampleSize=scaleFactor;
				factoryOptions.inPurgeable=true;
				
				Bitmap bitmap=BitmapFactory.decodeFile(outputFileUri.getPath(), factoryOptions);
				img.setImageBitmap(bitmap);
				
			}
		}
	}
}