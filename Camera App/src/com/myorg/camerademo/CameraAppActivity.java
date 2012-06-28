package com.myorg.camerademo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CameraAppActivity extends Activity {
	public Camera camera;
	public int cameraID;
	Button btnCapture;
	Preview preview;
	FrameLayout previewLayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnCapture = (Button) findViewById(R.id.btnCapture);
		preview=new Preview(this);
		previewLayout=(FrameLayout)findViewById(R.id.preview);
		previewLayout.addView(preview);

		// if we have Camera on our device
		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(getApplicationContext(), "No Camera on this device",
					Toast.LENGTH_LONG).show();
		} else {
			// check whther front camera is available
			cameraID = findFrontFacingCamera();
			camera = Camera.open(cameraID);
			if (cameraID < 0) {
				Toast.makeText(getApplicationContext(),
						"No Front Camera on this device", Toast.LENGTH_LONG)
						.show();
			}
		}

		btnCapture.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				preview.camera.takePicture(null, null, new PhotoHandler(
						getApplicationContext()));
			}
		});
	}

	public int findFrontFacingCamera() {
		int cameraId = -1;
		int numberofCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberofCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				cameraId = i;
				break;
			}

		}

		return cameraId;
	}
}