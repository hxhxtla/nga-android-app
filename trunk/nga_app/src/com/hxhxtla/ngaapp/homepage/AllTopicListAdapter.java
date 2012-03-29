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

public class AllTopicListAdapter extends BaseAdapter implements ListAdapter {
	private static final List<TopicInfo> AllTopicInfoList = new ArrayList<TopicInfo>();

	private Context mContext;

	public AllTopicListAdapter(Context c) {
		mContext = c;
		if (AllTopicInfoList.size() == 0) {
			initializeAllTopicInfoList();
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
			initializeAllTopicInfoList();
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
				if (ti.getId().equals(ati.getId())) {
					AllTopicInfoList.remove(ati);
					break;
				}
			}
		}
	}

	private void initializeAllTopicInfoList() {
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic1_id), mContext
				.getString(R.string.topic1), R.drawable.p7));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic2_id), mContext
				.getString(R.string.topic2), R.drawable.p323));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic3_id), mContext
				.getString(R.string.topic3), R.drawable.p354));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic4_id), mContext
				.getString(R.string.topic4), R.drawable.p318));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic5_id), mContext
				.getString(R.string.topic5), R.drawable.p10));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic6_id), mContext
				.getString(R.string.topic6), R.drawable.p230));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic7_id), mContext
				.getString(R.string.topic7), R.drawable.p387));

		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic8_id), mContext
				.getString(R.string.topic8), R.drawable.p320));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic9_id), mContext
				.getString(R.string.topic9), R.drawable.p181));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic10_id), mContext
				.getString(R.string.topic10), R.drawable.p182));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic11_id), mContext
				.getString(R.string.topic11), R.drawable.p183));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic12_id), mContext
				.getString(R.string.topic12), R.drawable.p185));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic13_id), mContext
				.getString(R.string.topic13), R.drawable.p186));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic14_id), mContext
				.getString(R.string.topic14), R.drawable.p187));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic15_id), mContext
				.getString(R.string.topic15), R.drawable.p184));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic16_id), mContext
				.getString(R.string.topic16), R.drawable.p188));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic17_id), mContext
				.getString(R.string.topic17), R.drawable.p189));

		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic18_id), mContext
				.getString(R.string.topic18), R.drawable.p310));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic19_id), mContext
				.getString(R.string.topic19), R.drawable.p190));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic20_id), mContext
				.getString(R.string.topic20), R.drawable.p213));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic21_id), mContext
				.getString(R.string.topic21), R.drawable.p218));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic22_id), mContext
				.getString(R.string.topic22), R.drawable.p258));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic23_id), mContext
				.getString(R.string.topic23), R.drawable.p272));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic24_id), mContext
				.getString(R.string.topic24), R.drawable.p191));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic25_id), mContext
				.getString(R.string.topic25), R.drawable.p200));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic26_id), mContext
				.getString(R.string.topic26), R.drawable.p240));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic27_id), mContext
				.getString(R.string.topic27), R.drawable.p274));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic28_id), mContext
				.getString(R.string.topic28), R.drawable.p315));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic29_id), mContext
				.getString(R.string.topic29), R.drawable.p333));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic30_id), mContext
				.getString(R.string.topic30), R.drawable.p327));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic31_id), mContext
				.getString(R.string.topic31), R.drawable.p388));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic32_id), mContext
				.getString(R.string.topic32), R.drawable.p10));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic33_id), mContext
				.getString(R.string.topic33), R.drawable.p10));

		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic34_id), mContext
				.getString(R.string.topic34), R.drawable.p264));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic35_id), mContext
				.getString(R.string.topic35), R.drawable.p8));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic36_id), mContext
				.getString(R.string.topic36), R.drawable.p102));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic37_id), mContext
				.getString(R.string.topic37), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic38_id), mContext
				.getString(R.string.topic38), R.drawable.p254));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic39_id), mContext
				.getString(R.string.topic39), R.drawable.p355));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic40_id), mContext
				.getString(R.string.topic40), R.drawable.p116));

		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic41_id), mContext
				.getString(R.string.topic41), R.drawable.p193));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic42_id), mContext
				.getString(R.string.topic42), R.drawable.p201));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic43_id), mContext
				.getString(R.string.topic43), R.drawable.p334));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic44_id), mContext
				.getString(R.string.topic44), R.drawable.p335));

		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic45_id), mContext
				.getString(R.string.topic45), R.drawable.p332));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic46_id), mContext
				.getString(R.string.topic46), R.drawable.p321));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic47_id), mContext
				.getString(R.string.topic47), R.drawable.pdefault));

		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic48_id), mContext
				.getString(R.string.topic48), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic49_id), mContext
				.getString(R.string.topic49), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic50_id), mContext
				.getString(R.string.topic50), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic51_id), mContext
				.getString(R.string.topic51), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic52_id), mContext
				.getString(R.string.topic52), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic53_id), mContext
				.getString(R.string.topic53), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic54_id), mContext
				.getString(R.string.topic54), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic55_id), mContext
				.getString(R.string.topic55), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic56_id), mContext
				.getString(R.string.topic56), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic57_id), mContext
				.getString(R.string.topic57), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic58_id), mContext
				.getString(R.string.topic58), R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(mContext
				.getString(R.string.topic59_id), mContext
				.getString(R.string.topic59), R.drawable.pdefault));
	}

}
