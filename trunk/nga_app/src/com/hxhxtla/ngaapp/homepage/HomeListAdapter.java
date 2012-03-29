package com.hxhxtla.ngaapp.homepage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.TopicInfo;

public class HomeListAdapter extends BaseAdapter implements ListAdapter {

	private static List<TopicInfo> TopicInfoList;

	public static final int ADD_STATUS_OK = 0;
	public static final int ADD_STATUS_FULL = 1;

	private Context mContext;

	private static final int NUM_PER_PAGE = 12;

	private int index_view;

	public HomeListAdapter(Context c) {
		mContext = c;
		if (TopicInfoList == null) {
			initializeTopicInfoList();
		}
	}

	public int getCount() {
		int num = TopicInfoList.size() - NUM_PER_PAGE * index_view;
		return num < NUM_PER_PAGE ? num : NUM_PER_PAGE;
	}

	public TopicInfo getItem(int arg0) {
		return TopicInfoList.get(arg0 + NUM_PER_PAGE * index_view);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public NgaAppMainActivity getContext() {
		return (NgaAppMainActivity) mContext;
	}

	public void addItem(TopicInfo value) {
		TopicInfoList.add(TopicInfoList.size() - 1, value);
	}

	public void removeItemAt(int index) {
		TopicInfoList.remove(index + NUM_PER_PAGE * index_view);
	}

	public int addNewItem(TopicInfo value) {
		addItem(value);
		if (Math.floor((TopicInfoList.size() - 1) / NUM_PER_PAGE) > index_view) {
			return ADD_STATUS_FULL;
		} else {
			return ADD_STATUS_OK;
		}
	}

	public boolean checkItemNotExist(TopicInfo value) {
		for (TopicInfo item : TopicInfoList) {
			if (value.getId().equals(item.getId())) {
				return false;
			}
		}
		return true;
	}

	public TopicInfo deleteItemAt(int position) {
		TopicInfo item = getItem(position);
		if (item.getId()
				.equals(mContext.getString(R.string.add_topic_id))) {
			return null;
		}
		this.removeItemAt(position);
		return item;
	}

	public boolean isAddItem(int position) {
		return (position == TopicInfoList.size() - 1);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Activity aty = (Activity) mContext;

		TopicInfo curTopicInfo = getItem(position);

		convertView = curTopicInfo.getView();

		if (convertView == null) {
			convertView = aty.getLayoutInflater().inflate(
					R.layout.home_list_item, null);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.home_list_item_icon);
			TextView textView = (TextView) convertView
					.findViewById(R.id.home_list_item_title);

			imageView.setImageResource(curTopicInfo.getIcon());

			textView.setText(curTopicInfo.getName());

			curTopicInfo.setView(convertView);
		}

		return convertView;
	}

	private void initializeTopicInfoList() {
		TopicInfoList = new ArrayList<TopicInfo>();

		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic1_id),
				mContext.getString(R.string.topic1), R.drawable.p7));
		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic2_id),
				mContext.getString(R.string.topic2), R.drawable.p323));
		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic3_id),
				mContext.getString(R.string.topic3), R.drawable.p354));
		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic4_id),
				mContext.getString(R.string.topic4), R.drawable.p318));
		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic5_id),
				mContext.getString(R.string.topic5), R.drawable.p10));
		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic6_id),
				mContext.getString(R.string.topic6), R.drawable.p230));
		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic7_id),
				mContext.getString(R.string.topic7), R.drawable.p387));

		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic8_id),
				mContext.getString(R.string.topic8), R.drawable.p320));
		TopicInfoList.add(new TopicInfo(mContext.getString(R.string.topic9_id),
				mContext.getString(R.string.topic9), R.drawable.p181));
		TopicInfoList.add(new TopicInfo(
				mContext.getString(R.string.topic10_id), mContext
						.getString(R.string.topic10), R.drawable.p182));
		TopicInfoList.add(new TopicInfo(
				mContext.getString(R.string.topic11_id), mContext
						.getString(R.string.topic11), R.drawable.p183));
		TopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.add_topic_id), mContext
				.getString(R.string.add_topic), R.drawable.add_icon));
		getContext().saveTopicInfoListToConfig();
	}

	public int getIndex_view() {
		return index_view;
	}

	public void setIndex_view(int index_view) {
		this.index_view = index_view;
	}

	public static List<TopicInfo> getTopicInfoList() {
		return TopicInfoList;
	}

	public static void setTopicInfoList(List<TopicInfo> topicInfoList) {
		TopicInfoList = topicInfoList;
	}

	public static int getCurrentPageCount() {
		int Numpage = (int) Math.ceil((double) TopicInfoList.size()
				/ (double) NUM_PER_PAGE);
		return Numpage;
	}

}
