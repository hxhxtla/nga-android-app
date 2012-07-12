package com.hxhxtla.ngaapp.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.view.View;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;

public class ArticleInfo {
	public static final Calendar lastBuildDate = Calendar.getInstance();

	public static Pattern P_TID;

	private String title;

	private String link;

	private String author;

	private String lastpost;

	private String postcount;
	// //////////////////////////////
	private View view;

	private TextView tvTitle;

	private TextView tvAuthor;

	private TextView tvLastpost;

	private TextView tvPostcount;

	private TextView tvPostTime;

	public ArticleInfo(View value) {
		view = value;
		tvTitle = (TextView) view.findViewById(R.id.articles_list_item_title);
		tvAuthor = (TextView) view.findViewById(R.id.articles_list_item_author);
		tvLastpost = (TextView) view
				.findViewById(R.id.articles_list_item_lastpost);
		tvPostcount = (TextView) view
				.findViewById(R.id.articles_list_item_count);
		tvPostTime = (TextView) view
				.findViewById(R.id.articles_list_item_posttime);
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

	public void setPostTime(Date value) {
		Calendar postTime = Calendar.getInstance();
		postTime.setTime(value);
		int dif_year = lastBuildDate.get(Calendar.YEAR)
				- postTime.get(Calendar.YEAR);
		if (dif_year == 0) {
			int dif_day = lastBuildDate.get(Calendar.DAY_OF_YEAR)
					- postTime.get(Calendar.DAY_OF_YEAR);
			if (dif_day == 0) {
				int dif_hour = lastBuildDate.get(Calendar.HOUR_OF_DAY)
						- postTime.get(Calendar.HOUR_OF_DAY);
				if (dif_hour == 0) {
					tvPostTime.setText(R.string.posttime_hour);
				} else {
					if (postTime.get(Calendar.HOUR_OF_DAY) < 12) {
						tvPostTime.setText(R.string.posttime_morning);
					} else {
						tvPostTime.setText(R.string.posttime_afternoon);
					}
				}
			} else if (dif_day == 1) {
				tvPostTime.setText(R.string.posttime_yesterday);
			} else {
				tvPostTime.setText("("
						+ String.valueOf(postTime.get(Calendar.MONTH) + 1)
						+ "-"
						+ String.valueOf(postTime.get(Calendar.DAY_OF_MONTH))
						+ ")");
			}
		} else {
			tvPostTime.setText(R.string.posttime_grave);
		}

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public String getTID() {
		if (P_TID == null) {
			P_TID = Pattern.compile("tid=(\\d+)", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
		}
		Matcher matcher = P_TID.matcher(getLink());
		matcher.find();
		return matcher.group(1);
	}

}
