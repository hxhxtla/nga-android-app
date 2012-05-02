package com.hxhxtla.ngaapp.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;
import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.TopicInfo;

public class ConfigController {
	private Context context;

	private SharedPreferences sharedPreferences;

	public ConfigController(Context c) {
		context = c;
	}

	public SharedPreferences getConfig() {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(
					context.getString(R.string.CONFIG), Context.MODE_PRIVATE);
		}
		return sharedPreferences;
	}

	public List<TopicInfo> getTopiclist() {
		String JSONStr = getConfig().getString(
				context.getString(R.string.TOPIC_LIST), null);
		return JSON.parseArray(JSONStr, TopicInfo.class);
	}

	public boolean saveTopiclist(List<TopicInfo> til) {
		List<TopicInfo> JSONList = new ArrayList<TopicInfo>();
		for (TopicInfo ti : til) {
			JSONList.add(ti.clone());
		}
		Editor editor = getConfig().edit();
		editor.putString(context.getString(R.string.TOPIC_LIST),
				JSON.toJSONString(JSONList));
		return editor.commit();
	}

	public boolean saveLoginInfo(String uid, String cid) {
		Editor editor = getConfig().edit();
		editor.putString(context.getString(R.string.nga_uid), uid);
		editor.putString(context.getString(R.string.nga_cid), cid);
		return editor.commit();

	}

	public boolean clearLoginInfo() {
		Editor editor = getConfig().edit();
		editor.remove(context.getString(R.string.nga_uid));
		editor.remove(context.getString(R.string.nga_cid));
		return editor.commit();
	}

	public String getNgaPassportUid() {
		return getConfig().getString(context.getString(R.string.nga_uid), null);
	}

	public String getNgaPassportCid() {
		return getConfig().getString(context.getString(R.string.nga_cid), null);
	}
}
