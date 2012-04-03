package com.hxhxtla.ngaapp.articleslistpage;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.TopicInfo;

public class HistoryTopicListAdapter extends BaseAdapter implements
		SpinnerAdapter {
	private List<TopicInfo> history_topic_list;

	private Context mContext;

	private TextView tv;

	public HistoryTopicListAdapter(Context context, List<TopicInfo> objects) {
		mContext = context;

		history_topic_list = objects;
	}

	@Override
	public int getCount() {
		return history_topic_list.size();
	}

	@Override
	public TopicInfo getItem(int position) {
		return history_topic_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (tv == null) {
			tv = new TextView(mContext);
		}
		tv.setText(getItem(0).getName());
		return tv;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

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

}
