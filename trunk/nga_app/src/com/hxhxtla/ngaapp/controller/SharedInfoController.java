package com.hxhxtla.ngaapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ArticleInfo;
import com.hxhxtla.ngaapp.bean.TopicInfo;

public class SharedInfoController {

	public static final List<TopicInfo> DISPLAYED_HISTORY_TOPICLIST = new ArrayList<TopicInfo>();

	public static final int DISPLAYED_HISTORY_TOPICLIST_LIMIT = 5;

	public static String SERVER_URL;

	public static ArticleInfo RECENT_POST;

	public static boolean CTRL_AVATAR_SHOW;
	public static boolean CTRL_AVATAR_SHOW_WIFI;

	public static boolean HAS_WIFI;

	public static boolean showAvatar() {
		if (CTRL_AVATAR_SHOW && (HAS_WIFI || CTRL_AVATAR_SHOW_WIFI)) {
			return true;
		} else {
			return false;
		}

	}

	public static DefaultHttpClient httpClient;

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

	public static void showCommonAlertDialog(Activity value, int messageResId) {
		Builder br = new AlertDialog.Builder(value);
		br.setTitle(R.string.keyword_tip_check);
		br.setMessage(messageResId);
		br.setPositiveButton(R.string.menu_confirm, null);
		br.create().show();
	}

}
