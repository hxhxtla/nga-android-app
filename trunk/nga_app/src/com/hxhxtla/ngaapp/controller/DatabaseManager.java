package com.hxhxtla.ngaapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "nga_hxhxtla.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_IMAGE = "nga_img_cache";
	public static final String IMG_URL = "url";
	public static final String IMG_UUID = "uuid";
	public static final String ID = "id";

	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists " + TABLE_IMAGE + "("
				+ "id integer primary key," + IMG_URL + " varchar," + IMG_UUID
				+ " varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 更改数据库版本的操作
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		// TODO 每次成功打开数据库后首先被执行
	}

	public String getImageUUIDByURL(String value) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(TABLE_IMAGE, null, IMG_URL + "=?",
				new String[] { value }, null, null, null);
		if (cursor.getCount() > 0) {
			int uuidIndex = cursor.getColumnIndex(IMG_UUID);
			cursor.moveToFirst();
			String uuid = cursor.getString(uuidIndex);
			if (uuid != null && !uuid.isEmpty()) {
				if (LocalFileManager.checkImageExist(uuid)) {
					cursor.close();
					db.close();
					return uuid;
				} else {
					db.delete(TABLE_IMAGE, IMG_UUID + "=?",
							new String[] { uuid });
				}
			} else {
				db.delete(TABLE_IMAGE, IMG_URL + "=?", new String[] { value });
			}
		}
		cursor.close();
		db.close();
		return null;
	}

	public void addImageCache(String url, String uuid) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(IMG_URL, url);
		values.put(IMG_UUID, uuid);
		db.insert(TABLE_IMAGE, ID, values);
		db.close();
	}
}