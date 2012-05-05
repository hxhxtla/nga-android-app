package com.hxhxtla.ngaapp.bean;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.controller.PostContentBuilder;

public class PostInfo {

	private String author;
	private String floor;
	private String datetime;
	private String content;

	private boolean highlight;

	private int pageIndex;

	// ////////////////////////////////
	private ViewGroup view;

	private TextView tvAuthor;
	private TextView tvFloor;
	private TextView tvDatetime;

	private WebView wvContent;

	public PostInfo(ViewGroup value) {
		view = value;
		tvAuthor = (TextView) view.findViewById(R.id.post_author);
		tvFloor = (TextView) view.findViewById(R.id.post_floor);
		tvDatetime = (TextView) view.findViewById(R.id.post_datetime);
		wvContent = (WebView) view.findViewById(R.id.post_content);
		wvContent.setBackgroundColor(0);
	}

	public PostInfo(int index, ViewGroup value) {
		pageIndex = index;
		view = value;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
		tvAuthor.setText(author);
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
		tvFloor.setText(floor);
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
		tvDatetime.setText(datetime);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content, String subtitle,
			ArrayList<CommentInfo> cil) {
		this.content = content;

		wvContent.loadDataWithBaseURL(null,
				PostContentBuilder.buildContent(content, subtitle, cil),
				"text/html", "UTF-8", null);
		if (highlight) {
			wvContent.setBackgroundResource(R.drawable.msgbox1);
		}
	}

	public View getView() {
		return view;
	}

	public WebView getContentView() {
		return wvContent;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = (this.author.equals(highlight));
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void clearCache() {
		if (wvContent != null) {
			wvContent.clearCache(true);
			wvContent.clearHistory();
		}
	}

}
