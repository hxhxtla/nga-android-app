package com.hxhxtla.ngaapp.articleslistpage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	public ArticlesListAdapter(Activity value) {
		mContext = value;
		if (articleInfoList == null) {
			articleInfoList = new ArrayList<ArticleInfo>();
		}
	}

	public void setData(Iterator value) {
		int index = -1;
		while (value.hasNext()) {
			index++;
			Element item = (Element) value.next();
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
			ai.setAuthor(cleanDirty(item.elementText(mContext
					.getString(R.string.author))));
			ai.setLink(cleanDirty(item.elementText(mContext
					.getString(R.string.link))));
			String title = cleanDirty(item.elementText(mContext
					.getString(R.string.title)));
			title = title.substring(0, title.lastIndexOf(mContext
					.getString(R.string.nga_rss_keyword1)));
			ai.setTitle(title.trim());
			String description = cleanDirty(item.elementText(mContext
					.getString(R.string.description)));
			description = description.substring(ai.getTitle().length());
			String[] arr = description.split(mContext
					.getString(R.string.nga_rss_keyword2));
			ai.setPostcount(arr[0].trim());
			ai.setLastpost(arr[1].trim());
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
