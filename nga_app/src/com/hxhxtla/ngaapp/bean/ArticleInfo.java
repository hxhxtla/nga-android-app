package com.hxhxtla.ngaapp.bean;

import com.hxhxtla.ngaapp.R;

import android.view.View;
import android.widget.TextView;

public class ArticleInfo {

	private String title;

	private String link;

	private String author;

	private String lastpost;

	private String postcount;
	////////////////////////////////
	private View view;

	private TextView tvTitle;

	private TextView tvAuthor;

	private TextView tvLastpost;

	private TextView tvPostcount;

	public ArticleInfo(View value) {
		view = value;
		tvTitle = (TextView) view.findViewById(R.id.articles_list_item_title);
		tvAuthor = (TextView) view.findViewById(R.id.articles_list_item_author);
		tvLastpost = (TextView) view
				.findViewById(R.id.articles_list_item_lastpost);
		tvPostcount = (TextView) view
				.findViewById(R.id.articles_list_item_count);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		tvTitle.setText(title);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
		tvAuthor.setText(author);
	}

	public String getLastpost() {
		return lastpost;
	}

	public void setLastpost(String lastpost) {
		this.lastpost = lastpost;
		tvLastpost.setText(lastpost);
	}

	public String getPostcount() {
		return postcount;
	}

	public void setPostcount(String postcount) {
		this.postcount = postcount;
		tvPostcount.setText(postcount);
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

}
