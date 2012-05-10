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
				if (ti.getId() == ati.getId()) {
					AllTopicInfoList.remove(ati);
					break;
				}
			}
		}
	}

	private void initializeAllTopicInfoList() {
		AllTopicInfoList.add(new TopicInfo(R.string.topic1_id, R.string.topic1,
				R.drawable.p7));
		AllTopicInfoList.add(new TopicInfo(R.string.topic2_id, R.string.topic2,
				R.drawable.p323));
		AllTopicInfoList.add(new TopicInfo(R.string.topic3_id, R.string.topic3,
				R.drawable.p354));
		AllTopicInfoList.add(new TopicInfo(R.string.topic4_id, R.string.topic4,
				R.drawable.p318));
		AllTopicInfoList.add(new TopicInfo(R.string.topic5_id, R.string.topic5,
				R.drawable.p10));
		AllTopicInfoList.add(new TopicInfo(R.string.topic6_id, R.string.topic6,
				R.drawable.p230));
		AllTopicInfoList.add(new TopicInfo(R.string.topic7_id, R.string.topic7,
				R.drawable.p387));

		AllTopicInfoList.add(new TopicInfo(R.string.topic8_id, R.string.topic8,
				R.drawable.p320));
		AllTopicInfoList.add(new TopicInfo(R.string.topic9_id, R.string.topic9,
				R.drawable.p181));
		AllTopicInfoList.add(new TopicInfo(R.string.topic10_id,
				R.string.topic10, R.drawable.p182));
		AllTopicInfoList.add(new TopicInfo(R.string.topic11_id,
				R.string.topic11, R.drawable.p183));
		AllTopicInfoList.add(new TopicInfo(R.string.topic12_id,
				R.string.topic12, R.drawable.p185));
		AllTopicInfoList.add(new TopicInfo(R.string.topic13_id,
				R.string.topic13, R.drawable.p186));
		AllTopicInfoList.add(new TopicInfo(R.string.topic14_id,
				R.string.topic14, R.drawable.p187));
		AllTopicInfoList.add(new TopicInfo(R.string.topic15_id,
				R.string.topic15, R.drawable.p184));
		AllTopicInfoList.add(new TopicInfo(R.string.topic16_id,
				R.string.topic16, R.drawable.p188));
		AllTopicInfoList.add(new TopicInfo(R.string.topic17_id,
				R.string.topic17, R.drawable.p189));

		AllTopicInfoList.add(new TopicInfo(R.string.topic18_id,
				R.string.topic18, R.drawable.p310));
		AllTopicInfoList.add(new TopicInfo(R.string.topic19_id,
				R.string.topic19, R.drawable.p190));
		AllTopicInfoList.add(new TopicInfo(R.string.topic20_id,
				R.string.topic20, R.drawable.p213));
		AllTopicInfoList.add(new TopicInfo(R.string.topic21_id,
				R.string.topic21, R.drawable.p218));
		AllTopicInfoList.add(new TopicInfo(R.string.topic22_id,
				R.string.topic22, R.drawable.p258));
		AllTopicInfoList.add(new TopicInfo(R.string.topic23_id,
				R.string.topic23, R.drawable.p272));
		AllTopicInfoList.add(new TopicInfo(R.string.topic24_id,
				R.string.topic24, R.drawable.p191));
		AllTopicInfoList.add(new TopicInfo(R.string.topic25_id,
				R.string.topic25, R.drawable.p200));
		AllTopicInfoList.add(new TopicInfo(R.string.topic26_id,
				R.string.topic26, R.drawable.p240));
		AllTopicInfoList.add(new TopicInfo(R.string.topic27_id,
				R.string.topic27, R.drawable.p274));
		AllTopicInfoList.add(new TopicInfo(R.string.topic28_id,
				R.string.topic28, R.drawable.p315));
		AllTopicInfoList.add(new TopicInfo(R.string.topic29_id,
				R.string.topic29, R.drawable.p333));
		AllTopicInfoList.add(new TopicInfo(R.string.topic30_id,
				R.string.topic30, R.drawable.p327));
		AllTopicInfoList.add(new TopicInfo(R.string.topic31_id,
				R.string.topic31, R.drawable.p388));
		AllTopicInfoList.add(new TopicInfo(R.string.topic32_id,
				R.string.topic32, R.drawable.p10));
		AllTopicInfoList.add(new TopicInfo(R.string.topic33_id,
				R.string.topic33, R.drawable.p10));

		AllTopicInfoList.add(new TopicInfo(R.string.topic34_id,
				R.string.topic34, R.drawable.p264));
		AllTopicInfoList.add(new TopicInfo(R.string.topic35_id,
				R.string.topic35, R.drawable.p8));
		AllTopicInfoList.add(new TopicInfo(R.string.topic36_id,
				R.string.topic36, R.drawable.p102));
		AllTopicInfoList.add(new TopicInfo(R.string.topic37_id,
				R.string.topic37, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic38_id,
				R.string.topic38, R.drawable.p254));
		AllTopicInfoList.add(new TopicInfo(R.string.topic39_id,
				R.string.topic39, R.drawable.p355));
		AllTopicInfoList.add(new TopicInfo(R.string.topic40_id,
				R.string.topic40, R.drawable.p116));

		AllTopicInfoList.add(new TopicInfo(R.string.topic41_id,
				R.string.topic41, R.drawable.p193));
		AllTopicInfoList.add(new TopicInfo(R.string.topic42_id,
				R.string.topic42, R.drawable.p201));
		AllTopicInfoList.add(new TopicInfo(R.string.topic43_id,
				R.string.topic43, R.drawable.p334));
		AllTopicInfoList.add(new TopicInfo(R.string.topic44_id,
				R.string.topic44, R.drawable.p335));

		AllTopicInfoList.add(new TopicInfo(R.string.topic45_id,
				R.string.topic45, R.drawable.p332));
		AllTopicInfoList.add(new TopicInfo(R.string.topic46_id,
				R.string.topic46, R.drawable.p321));
		AllTopicInfoList.add(new TopicInfo(R.string.topic47_id,
				R.string.topic47, R.drawable.pdefault));

		AllTopicInfoList.add(new TopicInfo(R.string.topic48_id,
				R.string.topic48, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic49_id,
				R.string.topic49, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic50_id,
				R.string.topic50, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic51_id,
				R.string.topic51, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic52_id,
				R.string.topic52, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic53_id,
				R.string.topic53, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic54_id,
				R.string.topic54, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic55_id,
				R.string.topic55, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic56_id,
				R.string.topic56, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic57_id,
				R.string.topic57, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic58_id,
				R.string.topic58, R.drawable.pdefault));
		AllTopicInfoList.add(new TopicInfo(R.string.topic59_id,
				R.string.topic59, R.drawable.pdefault));
	}

}
