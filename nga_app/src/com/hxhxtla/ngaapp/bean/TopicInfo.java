package com.hxhxtla.ngaapp.bean;

import android.view.View;

public class TopicInfo {
	private String name;

	private int icon;

	private String id;

	private View view;
	
	public TopicInfo(){
		
	}

	public TopicInfo(String d, String n, int i) {
		id = d;
		name = n;
		icon = i;
	}
	
	public TopicInfo clone(){
		TopicInfo value = new TopicInfo(id, name, icon);
		return value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
