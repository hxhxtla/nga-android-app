package com.hxhxtla.ngaapp.bean;

import org.dom4j.Document;

public interface IActivity {
	public void callbackGetArticlesList(Document doc);

	public void showContectionProgressDialog();

	public void showGettingProgressDialog();

	public void showLoadingProgressDialog();
}
