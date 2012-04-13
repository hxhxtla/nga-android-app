package com.hxhxtla.ngaapp.postlistpage;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.CommentInfo;
import com.hxhxtla.ngaapp.bean.PostInfo;

public class PostListAdapter extends BaseAdapter implements ListAdapter {

	private static List<PostInfo> postInfoList;

	private int postListSize = 0;

	private Activity mContext;

	private static String post_item;
	private static String post_author;
	private static String post_floor;
	private static String post_datetime;
	private static String post_content;
	private static String post_comment;
	private static String post_subtitle;
	private static String post_comment_author;
	private static String post_comment_content;

	public PostListAdapter(Activity value) {
		mContext = value;

		post_item = mContext.getString(R.string.post_item);
		post_author = mContext.getString(R.string.post_author);
		post_floor = mContext.getString(R.string.post_floor);
		post_content = mContext.getString(R.string.post_content);
		post_datetime = mContext.getString(R.string.post_datetime);
		post_comment = mContext.getString(R.string.post_comment);
		post_subtitle = mContext.getString(R.string.post_subtitle);
		post_comment_author = mContext.getString(R.string.post_comment_author);
		post_comment_content = mContext
				.getString(R.string.post_comment_content);

		if (postInfoList == null) {
			postInfoList = new ArrayList<PostInfo>();
		}
	}

	public void setData(Document document) {
		if (document != null) {
			Elements postList = document.select(post_item);
			int index;
			for (index = 0; index < postList.size(); index++) {
				Element item = postList.get(index);
				while (postInfoList.size() <= index) {
					LinearLayout ll = (LinearLayout) mContext
							.getLayoutInflater().inflate(
									R.layout.post_list_item, null);
					PostInfo pi = new PostInfo(ll);
					postInfoList.add(pi);
				}
				PostInfo pi = postInfoList.get(index);
				String author = item.select(post_author).text();
				pi.setAuthor(author);

				String floor = item.select(post_floor).text();
				pi.setFloor(floor);

				String datetime = item.select(post_datetime).text();
				pi.setDatetime(datetime);

				String content = item.select(post_content).html();
				String sbutitle = item.select(post_subtitle).text();
				WebView wvContent = (WebView) mContext.getLayoutInflater()
						.inflate(R.layout.post_content_view, null);

				Elements comments = item.select(post_comment);
				ArrayList<CommentInfo> cil = null;
				if (comments.size() > 0) {
					cil = new ArrayList<CommentInfo>();
					for (Element comment : comments) {
						CommentInfo ci = new CommentInfo();
						ci.author = comment.select(post_comment_author).text();
						ci.conntent = comment.select(post_comment_content)
								.html();
						cil.add(ci);
					}
				}

				pi.setContent(content, sbutitle, cil, wvContent);
			}
			postListSize = index;
		}
	}

	@Override
	public int getCount() {
		return postListSize;
	}

	@Override
	public PostInfo getItem(int arg0) {
		return postInfoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return postInfoList.get(arg0).getView();
	}

}
