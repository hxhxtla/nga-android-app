package com.hxhxtla.ngaapp.utils;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

public class OpenFileInExtendToolUtils {
	
	public static Intent getHtmlFileIntent(String param)

	{

		Uri uri = Uri.parse(param).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(param).build();

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.setDataAndType(uri, "text/html");

		return intent;

	}

	// android获取一个用于打开图片文件的intent

	public static Intent getImageFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addCategory(Intent.CATEGORY_DEFAULT);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.parse(param);

		intent.setDataAndType(uri, "image/*");

		return intent;

	}

	// android获取一个用于打开PDF文件的intent

	public static Intent getPdfFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addCategory(Intent.CATEGORY_DEFAULT);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/pdf");

		return intent;

	}

	// android获取一个用于打开文本文件的intent

	public static Intent getTextFileIntent(String param, boolean paramBoolean)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addCategory(Intent.CATEGORY_DEFAULT);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (paramBoolean)

		{

			Uri uri1 = Uri.parse(param);

			intent.setDataAndType(uri1, "text/plain");

		}

		else

		{

			Uri uri2 = Uri.fromFile(new File(param));

			intent.setDataAndType(uri2, "text/plain");

		}

		return intent;

	}

	// android获取一个用于打开音频文件的intent

	public static Intent getAudioFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("oneshot", 0);

		intent.putExtra("configchange", 0);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "audio/*");

		return intent;

	}

	// android获取一个用于打开视频文件的intent

	public static Intent getVideoFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("oneshot", 0);

		intent.putExtra("configchange", 0);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "video/*");

		return intent;

	}

	// android获取一个用于打开CHM文件的intent

	public static Intent getChmFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addCategory(Intent.CATEGORY_DEFAULT);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/x-chm");

		return intent;

	}

	// android获取一个用于打开Word文件的intent

	public static Intent getWordFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addCategory(Intent.CATEGORY_DEFAULT);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/msword");

		return intent;

	}

	// android获取一个用于打开Excel文件的intent

	public static Intent getExcelFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addCategory(Intent.CATEGORY_DEFAULT);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/vnd.ms-excel");

		return intent;

	}

	// android获取一个用于打开PPT文件的intent

	public static Intent getPptFileIntent(String param)

	{

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.addCategory(Intent.CATEGORY_DEFAULT);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");

		return intent;

	}
}
