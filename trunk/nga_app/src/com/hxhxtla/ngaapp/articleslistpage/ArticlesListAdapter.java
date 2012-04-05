package com.hxhxtla.ngaapp.articleslistpage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ArticleInfo;

public class ArticlesListAdapter extends BaseAdapter implements ListAdapter {

	private static List<ArticleInfo> articleInfoList;

	private Activity mContext;

	private static String rss_author;
	private static String rss_link;
	private static String rss_title;
	private static String rss_description;
	private static String nga_rss_keyword1;
	private static String nga_rss_keyword2;
	private static String rss_channel;
	private static String rss_item;

	public ArticlesListAdapter(Activity value) {
		mContext = value;

		rss_channel = mContext.getString(R.string.rss_channel);
		rss_item = mContext.getString(R.string.rss_item);
		rss_author = mContext.getString(R.string.rss_author);
		rss_link = mContext.getString(R.string.rss_link);
		rss_title = mContext.getString(R.string.rss_title);
		rss_description = mContext.getString(R.string.rss_description);
		nga_rss_keyword1 = mContext.getString(R.string.nga_rss_keyword1);
		nga_rss_keyword2 = mContext.getString(R.string.nga_rss_keyword2);

		if (articleInfoList == null) {
			articleInfoList = new ArrayList<ArticleInfo>();
		}
	}

	public void setData(String value) {
		Document document;
		try {
			document = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Element channel = (Element) document.getRootElement().element(
				rss_channel);
		Iterator<?> it = channel.elementIterator(rss_item);
		while (it.hasNext()) {
			int index = 0;
			Element item = (Element) it.next();
			while (getCount() <= index) {
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
			ai.setAuthor(cleanDirty(item.elementText(rss_author)));
			ai.setLink(cleanDirty(item.elementText(rss_link)));
			String title = cleanDirty(item.elementText(rss_title));
			title = title.substring(0, title.lastIndexOf(nga_rss_keyword1));
			ai.setTitle(title.trim());
			String description = cleanDirty(item.elementText(rss_description));
			description = description.substring(ai.getTitle().length());
			String[] arr = description.split(nga_rss_keyword2);
			ai.setPostcount(arr[0].trim());
			ai.setLastpost(arr[1].trim());
			index++;
		}
	}

	public String cleanDirty(String value) {
		return value.replace("\n", "");
	}

	@Override
	public int getCount() {
		return articleInfoList.size();
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
