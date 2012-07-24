package com.hxhxtla.ngaapp.articleslistpage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.hxhxtla.ngaapp.bean.ArticleInfo;

public class ArticlesListAdapter extends BaseAdapter implements ListAdapter {
	private static final Calendar lastBuildDate = Calendar.getInstance();

	private List<ArticleInfo> articleInfoList;

	private int articlesListSize = 0;

	private Activity mContext;

	private String topic_rows;
	private String topic_row;
	private String topic_title;
	private String topic_author;
	private String topic_replyer;
	private String topic_info1;
	private String topic_info2;
	private String topic_info3;
	private String topic_info4;

	public ArticlesListAdapter(Activity value) {
		mContext = value;

		topic_rows = mContext.getString(R.string.topic_rows);
		topic_row = mContext.getString(R.string.topic_row);
		topic_title = mContext.getString(R.string.topic_title);
		topic_author = mContext.getString(R.string.topic_author);
		topic_replyer = mContext.getString(R.string.topic_replyer);
		topic_info1 = mContext.getString(R.string.topic_info1);
		topic_info2 = mContext.getString(R.string.topic_info2);
		topic_info3 = mContext.getString(R.string.topic_info3);
		topic_info4 = mContext.getString(R.string.topic_info4);
		if (articleInfoList == null) {
			articleInfoList = new ArrayList<ArticleInfo>();
		}
	}

	public boolean setData(Document document) {
		Elements topics = document.select(topic_rows);
		Elements topicList = topics.select(topic_row);
		int index;
		if (topicList.size() <= 1) {
			return false;
		}

		lastBuildDate.setTime(new Date());
		ArticleInfo.now = (long) Math
				.floor(lastBuildDate.getTimeInMillis() / 1000);
		lastBuildDate.setTimeInMillis(ArticleInfo.now * 1000);
		lastBuildDate.set(Calendar.HOUR_OF_DAY, 0);
		ArticleInfo.nowDayStart = (long) Math.floor(lastBuildDate
				.getTimeInMillis() / 1000);
		lastBuildDate.set(Calendar.DAY_OF_MONTH, 1);
		lastBuildDate.set(Calendar.MONTH, 0);
		ArticleInfo.nowYearStart = (long) Math.floor(lastBuildDate
				.getTimeInMillis() / 1000);

		for (index = 0; index < topicList.size(); index++) {

			Element item = (Element) topicList.get(index);
			while (articleInfoList.size() <= index) {
				LinearLayout ll = (LinearLayout) mContext.getLayoutInflater()
						.inflate(R.layout.articles_list_item, null);
				if (index % 2 == 0) {
					ll.setBackgroundResource(R.color.article_bg1);
				} else {
					ll.setBackgroundResource(R.color.article_bg2);
				}
				articleInfoList.add(new ArticleInfo(ll));
			}
			ArticleInfo ai = articleInfoList.get(index);
			ai.setAuthor(item.select(topic_author).text());
			Elements title = item.select(topic_title);
			ai.setLink(title.attr("href"));
			ai.setTitle(title.text());
			ai.setLastpost(item.select(topic_replyer).text());

			Elements imgs = item.select(topic_info1).select(topic_info2);
			for (Element img : imgs) {
				String onerror = img.attr(topic_info3);
				if (onerror != null && !onerror.isEmpty()
						&& onerror.indexOf(topic_info4) != -1) {
					String topicInfo = onerror.replace(topic_info4, "");
					topicInfo = topicInfo.substring(1, topicInfo.length() - 1);
					if (topicInfo != null) {
						String[] values = topicInfo.split(",");
						if (values.length == 21) {
							ai.setPostcount(values[15]);
							ai.setPostTime(values[13]);
						} else {
							// TODO
						}
					}
					break;
				}
			}
		}

		articlesListSize = index;
		return true;
	}

	@Override
	public int getCount() {
		return articlesListSize;
	}

	@Override
	public ArticleInfo getItem(int arg0) {
		return articleInfoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return getItem(arg0).getView();
	}

}
