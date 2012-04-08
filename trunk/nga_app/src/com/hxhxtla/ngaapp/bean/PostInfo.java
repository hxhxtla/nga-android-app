package com.hxhxtla.ngaapp.bean;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.controller.PostContentBuilder;

public class PostInfo {

	private String author;
	private String floor;
	private String datetime;
	private String content;
	// ////////////////////////////////
	private View view;

	private TextView tvAuthor;
	private TextView tvFloor;
	private TextView tvDatetime;

	private WebView wvContent;

	public PostInfo(View value) {
		view = value;
		tvAuthor = (TextView) view.findViewById(R.id.post_author);
		tvFloor = (TextView) view.findViewById(R.id.post_floor);
		tvDatetime = (TextView) view.findViewById(R.id.post_datetime);

		wvContent = (WebView) view.findViewById(R.id.post_content);
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

	public void setContent(String content) {
		this.content = content;
		wvContent.loadDataWithBaseURL(null,
				PostContentBuilder.buildContent(content), "text/html", "UTF-8",
				null);
	}

	public View getView() {
		return view;
	}

}
