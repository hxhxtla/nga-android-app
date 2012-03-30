package com.hxhxtla.ngaapp.controller;

import android.app.Activity;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;

import com.hxhxtla.ngaapp.R;

public class PointTabController {
	private LinearLayout linearLayout;

	private int num_page;

	private ImageSwitcher curOnPoint;

	public PointTabController(LinearLayout ll) {
		linearLayout = ll;
	}

	public void changePageOn(int pageIndex) {
		if (pageIndex < 0 || pageIndex >= linearLayout.getChildCount()) {
			return;
		}
		ImageSwitcher is = (ImageSwitcher) linearLayout.getChildAt(pageIndex);
		if (is == curOnPoint) {
			return;
		}
		if (curOnPoint != null) {
			curOnPoint.showNext();
		}
		curOnPoint = is;
		curOnPoint.showNext();
	}

	private View getNewPoint() {
		Activity aty = (Activity) linearLayout.getContext();
		View v = aty.getLayoutInflater().inflate(R.layout.ui_point, null);
		return v;
	}

	private void syncPage() {
		int cur_num_page = linearLayout.getChildCount();
		int count = 0;
		count = num_page - cur_num_page;
		while (count > 0) {
			linearLayout.addView(getNewPoint());
			count--;
		}

		while (count < 0) {
			linearLayout.removeViews(linearLayout.getChildCount() - 1 - count,
					count);
			count = linearLayout.getChildCount() - cur_num_page;
		}
	}

	public int getNumPage() {
		return num_page;
	}

	public void setNumPage(int num_page) {
		this.num_page = num_page;
		syncPage();
	}
}
