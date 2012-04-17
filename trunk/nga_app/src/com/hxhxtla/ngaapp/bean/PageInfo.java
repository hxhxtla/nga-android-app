package com.hxhxtla.ngaapp.bean;

import java.util.ArrayList;

public class PageInfo implements Comparable<PageInfo> {
	private int index;

	private ArrayList<PostInfo> postList;

	public PageInfo(int value) {
		index = value;
		postList = new ArrayList<PostInfo>();
	}

	@Override
	public int compareTo(PageInfo another) {
		if (this.index > another.index) {
			return 1;
		} else if (this.index < another.index) {
			return -1;
		} else {
			return 0;
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ArrayList<PostInfo> getPostList() {
		return postList;
	}

	public void setPostList(ArrayList<PostInfo> postList) {
		this.postList = postList;
	}

}
