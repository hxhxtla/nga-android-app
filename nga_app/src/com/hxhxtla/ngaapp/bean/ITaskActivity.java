package com.hxhxtla.ngaapp.bean;

import android.app.Activity;

public interface ITaskActivity {
	public void callbackHander(String doc);

	public void showContectionProgressDialog();

	public void showGettingProgressDialog();

	public void showLoadingProgressDialog();
	
	public Activity getActivity();
}
