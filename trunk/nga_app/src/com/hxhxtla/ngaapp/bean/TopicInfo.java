package com.hxhxtla.ngaapp.bean;

import android.view.View;

public class TopicInfo {
	private int name;

	private int icon;

	private int id;

	private View view;

	public TopicInfo() {

	}

	public TopicInfo(int d, int n, int i) {
		id = d;
		name = n;
		icon = i;
	}

	public TopicInfo clone() {
		TopicInfo value = new TopicInfo(id, name, icon);
		return value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
