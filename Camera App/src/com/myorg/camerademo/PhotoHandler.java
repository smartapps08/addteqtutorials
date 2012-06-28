package com.myorg.camerademo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {
	public final Context mContext;

	public PhotoHandler(Context context) {
		this.mContext = context;
	}

	public void onPictureTaken(byte[] data, Camera camera) {
		File pictureFileDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
						+ "Camera App");
		if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
			Toast.makeText(mContext, "Can't create a directory",
					Toast.LENGTH_LONG).show();
			return;
		}

		String photoFileName = "pic" + System.currentTimeMillis() + ".jpg";
		File picFile = new File(pictureFileDir.getPath() + File.separator
				+ photoFileName);

		try {
			FileOutputStream fos = new FileOutputStream(picFile);
			fos.write(data);
			fos.close();
			Toast.makeText(
					mContext,
					"File saved:" + pictureFileDir.getPath() + File.separator
							+ photoFileName, Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			Toast.makeText(mContext, "File NOT saved! " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(mContext, "File NOT saved! " + e.getMessage(),
					Toast.LENGTH_LONG).show();

		}

	}
}
