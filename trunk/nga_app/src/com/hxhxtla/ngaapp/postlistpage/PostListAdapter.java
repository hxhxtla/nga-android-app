package com.hxhxtla.ngaapp.postlistpage;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.hxhxtla.ngaapp.R;
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

	public PostListAdapter(Activity value) {
		mContext = value;

		post_item = mContext.getString(R.string.post_item);
		post_author = mContext.getString(R.string.post_author);
		post_floor = mContext.getString(R.string.post_floor);
		post_content = mContext.getString(R.string.post_content);
		post_datetime = mContext.getString(R.string.post_datetime);

		if (postInfoList == null) {
			postInfoList = new ArrayList<PostInfo>();
		}
	}

	public void setData(String value) {
		if (value != null) {
			Document document = Jsoup.parse(value);
			Elements postList = document.select("table[class=" + post_item
					+ "]");
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
				String author = item.select("a[class=" + post_author + "]")
						.text();
				pi.setAuthor(author);

				String floor = item.select("a[class=" + post_floor + "]")
						.text();
				pi.setFloor(floor);

				String datetime = item.select(
						"span[id=" + post_datetime + String.valueOf(index)
								+ "]").text();
				pi.setDatetime(datetime);

				String content = item
						.select("span[class=" + post_content + "]").html();
				pi.setContent(content);
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
