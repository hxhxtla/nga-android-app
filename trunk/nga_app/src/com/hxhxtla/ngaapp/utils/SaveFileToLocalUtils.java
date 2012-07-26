package com.hxhxtla.ngaapp.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;

public class SaveFileToLocalUtils {

	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/nga.hxhxtla/";

	private final static String IMAGE_PATH = ALBUM_PATH + "imageCache/";

	public static void initialize() {
		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
	}

	public static String saveImage(Bitmap bm, String fileName)
			throws IOException {
		File dirFile = new File(IMAGE_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		String localPath = IMAGE_PATH + fileName + ".jpg";
		File myCaptureFile = new File(localPath);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		bos.flush();
		bos.close();
		return "file://" + localPath;
	}
}
