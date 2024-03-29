package com.hxhxtla.ngaapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnClickListener;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ArticleInfo;
import com.hxhxtla.ngaapp.bean.TopicInfo;
import com.hxhxtla.ngaapp.task.GetImageTask;

public class SharedInfoController {

	public static Activity CURRENT_ACTIVITY;

	public static final List<TopicInfo> DISPLAYED_HISTORY_TOPICLIST = new ArrayList<TopicInfo>();

	public static final HashMap<String, GetImageTask> imageTaskList = new HashMap<String, GetImageTask>();

	public static final HashMap<String, String> LoadedImageList = new HashMap<String, String>();

	public static final HashMap<String, String> Wait4LoadImageList = new HashMap<String, String>();

	public static final int DISPLAYED_HISTORY_TOPICLIST_LIMIT = 5;

	public static String SERVER_URL;

	public static ArticleInfo RECENT_POST;

	public static boolean CTRL_AVATAR_SHOW;
	public static boolean CTRL_AVATAR_SHOW_WIFI;

	public static boolean CTRL_IMAGE_SHOW;
	public static boolean CTRL_IMAGE_SHOW_WIFI;

	public static boolean HAS_WIFI;

	public static int POST_ACTION_TYPE;

	public static String POST_ACTION_CONTENT_PRE_ADD;

	public static DefaultHttpClient httpClient;

	public static boolean CTRL_PREFIX_DISPLAY;

	public static boolean showAvatar() {
		if (CTRL_AVATAR_SHOW && (HAS_WIFI || CTRL_AVATAR_SHOW_WIFI)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean showImage() {
		if (CTRL_IMAGE_SHOW && (HAS_WIFI || CTRL_IMAGE_SHOW_WIFI)) {
			return true;
		} else {
			return false;
		}

	}

	public SharedInfoController() {
		// TODO Auto-generated constructor stub
	}

	public static void addTopicHistory(TopicInfo value) {
		for (TopicInfo ti : DISPLAYED_HISTORY_TOPICLIST) {
			if (ti == value || ti.getId() == value.getId()) {
				if (DISPLAYED_HISTORY_TOPICLIST.indexOf(ti) != 0) {
					DISPLAYED_HISTORY_TOPICLIST.remove(ti);
					DISPLAYED_HISTORY_TOPICLIST.add(0, ti);
				}
				return;
			}
		}
		DISPLAYED_HISTORY_TOPICLIST.add(0, value.clone());
		while (DISPLAYED_HISTORY_TOPICLIST.size() > DISPLAYED_HISTORY_TOPICLIST_LIMIT) {
			DISPLAYED_HISTORY_TOPICLIST.remove(DISPLAYED_HISTORY_TOPICLIST
					.size() - 1);
		}
	}

	public static void showCommonAlertDialog(Activity value, String msg,
			OnClickListener listener) {

		Builder br = new AlertDialog.Builder(value);
		br.setTitle(R.string.keyword_tip_check);
		br.setMessage(msg);
		br.setPositiveButton(R.string.menu_confirm, listener);
		br.create().show();
	}

	public static void showCommonAlertDialog(Activity value, int messageResId,
			OnClickListener listener) {
		Builder br = new AlertDialog.Builder(value);
		br.setTitle(R.string.keyword_tip_check);
		br.setMessage(messageResId);
		br.setPositiveButton(R.string.menu_confirm, listener);
		br.create().show();
	}

}
