package com.hxhxtla.ngaapp.controller;

import android.view.View;
import android.widget.LinearLayout;

public class PageScrollController {

	private LinearLayout ll;
	
	private int curPageIndex;

	public PageScrollController(LinearLayout value) {
		ll = value;
	}

	public int getDisplayedChild() {
		return curPageIndex;
	}

	public int getChildCount() {
		return ll.getChildCount();
	}

	public View getCurrentView() {
		return getChildAt(curPageIndex);
	}

	public void addView(View value) {
		ll.addView(value);
	}

	public View getChildAt(int index) {
		return ll.getChildAt(index);
	}
}
