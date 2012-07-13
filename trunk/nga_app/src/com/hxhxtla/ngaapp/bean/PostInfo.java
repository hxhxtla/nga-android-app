package com.hxhxtla.ngaapp.bean;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.task.GetAvatarTask;
import com.hxhxtla.ngaapp.task.PostContentBuilder;

public class PostInfo {

	public static final HashMap<String, GetAvatarTask> avatarTask = new HashMap<String, GetAvatarTask>();

	private boolean highlight;

	private int pageIndex;

	private boolean avatarLoaded = false;

	// var////////////////////////////////
	private String author;
	private String floor;
	private String datetime;
	private String prestige;
	private String postcount;
	private String urlAvatar;

	private String originalContent;

	private String pid;

	// view////////////////////////////////
	private ViewGroup view;

	private TextView tvAuthor;
	private TextView tvFloor;
	private TextView tvDatetime;
	private TextView tvPrestige;
	private TextView tvPostcount;

	private ProgressBar pbLoading;

	private WebView wvContent;

	private ImageView ivAvatar;

	public PostInfo(ViewGroup value) {
		view = value;
		tvAuthor = (TextView) view.findViewById(R.id.post_author);
		tvFloor = (TextView) view.findViewById(R.id.post_floor);
		tvDatetime = (TextView) view.findViewById(R.id.post_datetime);
		tvPrestige = (TextView) view.findViewById(R.id.post_prestige);
		tvPostcount = (TextView) view.findViewById(R.id.post_count);
		wvContent = (WebView) view.findViewById(R.id.post_content);
		wvContent.setBackgroundColor(0);
		ivAvatar = (ImageView) view.findViewById(R.id.post_avatar);
		pbLoading = (ProgressBar) view
				.findViewById(R.id.post_author_progressBar);
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

	public String getPrestige() {
		return prestige;
	}

	public void setPrestige(String prestige) {
		this.prestige = prestige;
		tvPrestige.setText(prestige);
	}

	public String getPostcount() {
		return postcount;
	}

	public void setPostcount(String postcount) {
		this.postcount = postcount;
		tvPostcount.setText(postcount);
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setContent(String content) {
		wvContent
				.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
		if (highlight) {
			wvContent.setBackgroundResource(R.drawable.msgbox1);
		}
	}

	public void setContentSource(String value, ArrayList<CommentInfo> cil,
			String title) {
		originalContent = value;
		new PostContentBuilder(this).execute(value, cil, title);
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;

	}

	public void callAvatarHandler(Bitmap bitmap) {
		if (!avatarLoaded) {
			ivAvatar.setImageBitmap(bitmap);
			avatarLoaded = true;
			pbLoading.setVisibility(View.GONE);
		}
	}

	public void tryLoadAvatar() {
		if (!avatarLoaded && urlAvatar != null && !urlAvatar.isEmpty()) {
			pbLoading.setVisibility(View.VISIBLE);
			GetAvatarTask value = avatarTask.get(urlAvatar);
			if (value != null) {
				avatarTask.get(urlAvatar).addTaskDestination(this);
			} else if (!avatarTask.containsKey(urlAvatar)) {
				value = new GetAvatarTask(this);
				avatarTask.put(urlAvatar, value);
				value.execute(urlAvatar);
			}
		}
	}

	public void addAvatarLoadByClick() {
		if (!avatarLoaded && urlAvatar != null && !urlAvatar.isEmpty()) {
			ivAvatar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					tryLoadAvatar();
				}
			});
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

	public String getQuoteContent() {
		String quote = "[quote][pid=" + pid + "]Reply[/pid] [b]Post by "
				+ author + " (" + datetime + "):[/b]\n\n"
				+ originalContent.replaceAll("\\<br /\\>", "\n") + "[/quote]";
		return quote;
	}

}
