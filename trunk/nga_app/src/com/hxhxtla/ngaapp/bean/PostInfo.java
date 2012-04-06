package com.hxhxtla.ngaapp.bean;

import android.view.View;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;

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

	private TextView tvContent;

	public PostInfo(View value) {
		view = value;
		tvAuthor = (TextView) view.findViewById(R.id.post_author);
		tvFloor = (TextView) view.findViewById(R.id.post_floor);
		tvDatetime = (TextView) view.findViewById(R.id.post_datetime);

		tvContent = (TextView) view.findViewById(R.id.post_content);
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
		tvContent.setText(content);
	}

	public View getView() {
		return view;
	}

}
