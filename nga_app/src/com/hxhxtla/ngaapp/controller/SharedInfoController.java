package com.hxhxtla.ngaapp.controller;

import java.util.ArrayList;
import java.util.List;

import com.hxhxtla.ngaapp.bean.TopicInfo;

public class SharedInfoController {

	public static final List<TopicInfo> DISPLAYED_HISTORY_TOPICLIST = new ArrayList<TopicInfo>();

	public static final int DISPLAYED_HISTORY_TOPICLIST_LIMIT = 5;
	
	public static String SERVER_URL;
	
	public static String RECENT_POST_URL;

	public SharedInfoController() {
		// TODO Auto-generated constructor stub
	}

	public static void addTopicHistory(TopicInfo value) {
		for (TopicInfo ti : DISPLAYED_HISTORY_TOPICLIST) {
			if (ti == value || ti.getId().equals(value.getId())) {
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

}
