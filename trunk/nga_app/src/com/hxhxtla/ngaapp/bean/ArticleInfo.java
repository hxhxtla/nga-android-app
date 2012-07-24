package com.hxhxtla.ngaapp.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.view.View;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;

public class ArticleInfo {

	public static final SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			"MM-dd HH:mm");
	public static final SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");

	public static long now;

	public static long nowDayStart;

	public static long nowYearStart;

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

	public void setPostTime(String value) {
		if (value == null || value.isEmpty()) {
			return;
		}
		Calendar postTime = Calendar.getInstance();
		long millisecondsValue = Long.parseLong(value);
		postTime.setTimeInMillis(millisecondsValue * 1000);
		Date dPostTime = postTime.getTime();

		long x = now - millisecondsValue;
		String dx;

		if (x < 4500) {
			String z = "分钟前";
			if (x < 60)
				dx = "刚才";
			else if (x < 450)
				dx = "5" + z;
			else if (x < 750)
				dx = "10" + z;
			else if (x < 1050)
				dx = "15" + z;
			else if (x < 1350)
				dx = "20" + z;
			else if (x < 1650)
				dx = "25" + z;
			else if (x < 2100)
				dx = "30" + z;
			else if (x < 2700)
				dx = "40" + z;
			else if (x < 3300)
				dx = "50" + z;
			else
				dx = "1小时前";
		} else {
			if (millisecondsValue > (nowDayStart - 172800)) {
				if (millisecondsValue > nowDayStart)
					dx = "今天";
				else if (millisecondsValue > (nowDayStart - 86400))
					dx = "昨天";
				else
					dx = "前天 ";
				dx += sdf3.format(dPostTime);
			} else if (millisecondsValue > nowYearStart)
				dx = sdf2.format(dPostTime);
			else
				dx = sdf1.format(dPostTime);
		}
		dx = "(" + dx + ")";
		tvPostTime.setText(dx);
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
