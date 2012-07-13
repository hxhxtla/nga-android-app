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
import com.hxhxtla.ngaapp.utils.TopicInfoListUtils;

public class AllTopicListAdapter extends BaseAdapter implements ListAdapter {
	private static final List<TopicInfo> AllTopicInfoList = new ArrayList<TopicInfo>();

	private Context mContext;

	public AllTopicListAdapter(Context c) {
		mContext = c;
		if (AllTopicInfoList.size() == 0) {
			TopicInfoListUtils.initializeAllTopicInfoList(AllTopicInfoList);
		}
	}

	public int getCount() {
		return AllTopicInfoList.size();
	}

	public TopicInfo getItem(int index) {
		return AllTopicInfoList.get(index);
	}

	public void removeItem(TopicInfo item) {
		AllTopicInfoList.remove(item);
	}

	public void addItem(TopicInfo item) {
		AllTopicInfoList.add(0, item);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (AllTopicInfoList.size() == 0) {
			TopicInfoListUtils.initializeAllTopicInfoList(AllTopicInfoList);
		}

		Activity aty = (Activity) mContext;

		TopicInfo curTopicInfo = getItem(position);

		convertView = curTopicInfo.getView();
		if (convertView == null) {
			convertView = aty.getLayoutInflater().inflate(
					R.layout.topic_picker_list_item, null);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.topic_picker_list_item_icon);
			TextView textView = (TextView) convertView
					.findViewById(R.id.topic_picker_list_item_title);

			imageView.setImageResource(curTopicInfo.getIcon());

			textView.setText(curTopicInfo.getName());

			curTopicInfo.setView(convertView);
		}
		return convertView;
	}

	public void syncAllTopicInfoList(List<TopicInfo> til) {
		for (TopicInfo ti : til) {
			for (TopicInfo ati : AllTopicInfoList) {
				if (ti.getId() == ati.getId()) {
					AllTopicInfoList.remove(ati);
					break;
				}
			}
		}
	}

}
