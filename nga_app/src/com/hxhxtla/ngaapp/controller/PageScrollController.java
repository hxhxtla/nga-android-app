package com.hxhxtla.ngaapp.controller;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class PageScrollController {

	private LinearLayout ll;

	private HorizontalScrollView hsv;

	private PointTabController ptc;

	private int curPageIndex = 0;

	public PageScrollController(LinearLayout value1,
			HorizontalScrollView value2, PointTabController value3) {
		ll = value1;
		hsv = value2;
		ptc = value3;
		hsv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP
						|| event.getAction() == MotionEvent.ACTION_CANCEL) {
					int curX = hsv.getScrollX();
					int distance1 = curX - getChildAt(curPageIndex).getLeft();
					if (distance1 == 0) {
						return false;
					} else {

						int distance2;
						int targetIndex;
						if (distance1 > 0) {
							targetIndex = curPageIndex + 1;
						} else {
							targetIndex = curPageIndex - 1;
						}
						distance2 = curX - getChildAt(targetIndex).getLeft();

						if (Math.abs(distance1) < Math.abs(distance2)) {

							targetIndex = curPageIndex;
							curPageIndex = -1;
						}
						if (showPageByIndex(targetIndex, true))
							syncPTC(targetIndex);
					}
				}
				return false;
			}
		});
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

	private boolean showPageByIndex(int index, boolean smooth) {
		if (index > -1 && index < getChildCount() && curPageIndex != index) {
			int targetX = getChildAt(index).getLeft();
			if (smooth) {
				hsv.smoothScrollTo(targetX, 0);
			} else {
				hsv.scrollTo(targetX, 0);
			}
			if (hsv.getScrollX() != targetX) {
				return showPageByIndex(index, true);
			}
			curPageIndex = index;
			return true;
		} else {
			return false;
		}
	}

	public boolean showNextPage() {
		return showPageByIndex(curPageIndex + 1, true);
	}

	public boolean showPreviousPage() {
		return showPageByIndex(curPageIndex - 1, true);
	}

	private void syncPTC(int index) {
		if (ptc != null) {
			ptc.changePageOn(index);
		}
	}
}
