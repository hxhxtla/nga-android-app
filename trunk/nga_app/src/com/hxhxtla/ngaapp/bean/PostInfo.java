package com.hxhxtla.ngaapp.bean;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.controller.PostContentBuilder;

public class PostInfo {

	private String author;
	private String floor;
	private String datetime;
	private String content;
	// ////////////////////////////////
	private LinearLayout view;

	private TextView tvAuthor;
	private TextView tvFloor;
	private TextView tvDatetime;

	private WebView wvContent;

	public PostInfo(LinearLayout value) {
		view = value;
		tvAuthor = (TextView) view.findViewById(R.id.post_author);
		tvFloor = (TextView) view.findViewById(R.id.post_floor);
		tvDatetime = (TextView) view.findViewById(R.id.post_datetime);

		// wvContent = (WebView) view.findViewById(R.id.post_content);
		// wvContent.setBackgroundColor(0);
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

	public void setContent(String content, WebView wv) {
		this.content = content;
		if (wvContent != null) {
			view.removeView(wvContent);
			wvContent = null;
		}
		wv.setBackgroundColor(0);
		wv.loadDataWithBaseURL(null, PostContentBuilder.buildContent(content),
				"text/html", "UTF-8", null);
		view.addView(wv);
		wvContent = wv;
	}

	public View getView() {
		return view;
	}

}
