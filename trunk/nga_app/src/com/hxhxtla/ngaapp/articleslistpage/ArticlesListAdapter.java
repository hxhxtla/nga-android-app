package com.hxhxtla.ngaapp.articleslistpage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private List<ArticleInfo> articleInfoList;

	private int articlesListSize = 0;

	private Activity mContext;

	private Pattern P_BRACES;

	private String rss_author;
	private String rss_link;
	private String rss_description;
	private String nga_rss_keyword;
	private String rss_channel;
	private String rss_item;
	private String rss_pubdate;
	private String rss_lastBuildDate;
	private SimpleDateFormat sdf;

	public ArticlesListAdapter(Activity value) {
		mContext = value;

		rss_channel = mContext.getString(R.string.rss_channel);
		rss_item = mContext.getString(R.string.rss_item);
		rss_author = mContext.getString(R.string.rss_author);
		rss_link = mContext.getString(R.string.rss_link);
		nga_rss_keyword = mContext.getString(R.string.nga_rss_keyword);
		rss_description = mContext.getString(R.string.rss_description);
		rss_pubdate = mContext.getString(R.string.rss_pubdate);
		rss_lastBuildDate = mContext.getString(R.string.rss_lastBuildDate);
		P_BRACES = Pattern.compile("(.+?)(\\d+)" + nga_rss_keyword + "(.+)",
				Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		if (articleInfoList == null) {
			articleInfoList = new ArrayList<ArticleInfo>();
		}
		sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
	}

	public boolean setData(String value) {
		Document document;
		try {
			document = DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		Element channel = (Element) document.getRootElement().element(
				rss_channel);
		int index;
		List<?> itemList = channel.elements(rss_item);
		if (itemList.size() <= 1) {
			return false;
		}

		String lastBuildDate = cleanDirty(channel
				.elementText(rss_lastBuildDate));
		try {
			ArticleInfo.lastBuildDate.setTime(sdf.parse(lastBuildDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		for (index = 0; index < itemList.size(); index++) {

			Element item = (Element) itemList.get(index);
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
			ai.setAuthor(cleanDirty(item.elementText(rss_author)));
			ai.setLink(cleanDirty(item.elementText(rss_link)));
			String description = item.elementText(rss_description);

			Matcher matcher = P_BRACES.matcher(description);
			if (matcher.find()) {
				String title = cleanDirty(matcher.group(1));
				ai.setTitle(title);
				String postCount = cleanDirty(matcher.group(2));
				ai.setPostcount(postCount);
				String lastpost = cleanDirty(matcher.group(3));
				ai.setLastpost(lastpost);
			}

			String pubdate = cleanDirty(item.elementText(rss_pubdate));
			Date postDate = null;
			try {
				postDate = sdf.parse(pubdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
			if (postDate != null) {
				ai.setPostTime(postDate);
			}
		}

		articlesListSize = index;
		return true;
	}

	public String cleanDirty(String value) {
		return value.replace("\n", "").trim();
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
