package com.myorg.camerademo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Preview extends SurfaceView implements SurfaceHolder.Callback {

	SurfaceHolder holder;
	public Camera camera;

	public Preview(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		// camera.setDisplayOrientation()
		try {
			camera.setPreviewDisplay(holder);
			camera.setPreviewCallback(new PreviewCallback() {

				public void onPreviewFrame(byte[] data, Camera camera) {
					try {
						FileOutputStream fos = new FileOutputStream(String
								.format(Environment
										.getExternalStorageDirectory().getPath()
										+ File.separator + "%d.jpg", System
										.currentTimeMillis()));

						fos.write(data);
						fos.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					invalidate();

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Camera.Parameters params = camera.getParameters();
		params.setPreviewSize(width, height);
		camera.setParameters(params);
		camera.startPreview();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera = null;

	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		Paint p = new Paint(Color.RED);
		canvas.drawText("PREVIEW", canvas.getWidth() / 2,
				canvas.getHeight() / 2, p);
	}

}
