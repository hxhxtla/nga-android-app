package com.hxhxtla.ngaapp.postlistpage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.hxhxtla.ngaapp.R;

public class PostListAdapter extends BaseAdapter implements ListAdapter {

	private Activity mContext;

	private static String post_item;
	private static String post_author;

	public PostListAdapter(Activity value) {
		mContext = value;

		post_item = mContext.getString(R.string.post_item);
		post_author = mContext.getString(R.string.post_author);
	}

	public void setData(String value) {
		if (value != null) {
			Document document = Jsoup.parse(value);
			Elements postList = document.getElementsByClass(post_item);
			for (Element post : postList) {
				Element author = post.getElementsByClass(post_author).get(0);
			}
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
