package com.hxhxtla.ngaapp.postlistpage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.CommentInfo;
import com.hxhxtla.ngaapp.bean.PageInfo;
import com.hxhxtla.ngaapp.bean.PostInfo;
import com.hxhxtla.ngaapp.bean.UserInfo;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.PostContentBuilder;

public class PostListAdapter extends BaseAdapter implements ListAdapter {

	private ArrayList<PageInfo> pageinfoList;

	private ArrayList<PostInfo> postInfoList;

	private Activity mContext;

	private HashMap<String, String> tempInfo;

	private Random randKey;

	private String post_item;
	private String post_datetime;
	private String post_content;
	private String post_comment;
	private String post_subtitle;
	private String post_comment_author;
	private String post_comment_content;
	private String post_user_info1;
	private String post_user_info2;
	private String post_user_info3;
	private String post_user_info4;
	private String keyword_url;

	private String curHighLightAuthor;

	private int pageSize = 20;

	private TextView title;

	public PostListAdapter(Activity value) {
		mContext = value;

		post_item = mContext.getString(R.string.post_item);
		// post_author = mContext.getString(R.string.post_author);
		// post_floor = mContext.getString(R.string.post_floor);
		post_content = mContext.getString(R.string.post_content);
		post_datetime = mContext.getString(R.string.post_datetime);
		post_comment = mContext.getString(R.string.post_comment);
		post_subtitle = mContext.getString(R.string.post_subtitle);
		post_comment_author = mContext.getString(R.string.post_comment_author);
		post_comment_content = mContext
				.getString(R.string.post_comment_content);
		post_user_info1 = mContext.getString(R.string.post_user_info1);
		post_user_info2 = mContext.getString(R.string.post_user_info2);
		post_user_info3 = mContext.getString(R.string.post_user_info3);
		post_user_info4 = mContext.getString(R.string.post_user_info4);

		keyword_url = mContext.getString(R.string.keyword_url);

		pageinfoList = new ArrayList<PageInfo>();
		postInfoList = new ArrayList<PostInfo>();

		tempInfo = new HashMap<String, String>();
		randKey = new Random();

		title = (TextView) mContext.getLayoutInflater().inflate(
				R.layout.post_list_title, null);
	}

	public void setData(Document document, int pagenum, String AllUserInfo) {
		if (document != null) {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
					.create();
			Map<String, UserInfo> userInfoList = gson.fromJson(
					AllUserInfo.trim(), new TypeToken<Map<String, UserInfo>>() {
					}.getType());
			PageInfo ipage = null;
			for (PageInfo pi : pageinfoList) {
				if (pi.getIndex() == pagenum) {
					ipage = pi;
					break;
				}
			}
			if (ipage == null) {
				ipage = new PageInfo(pagenum);
				pageinfoList.add(ipage);
			}

			Elements postList = document.select(post_item);
			for (int index = 0; index < postList.size(); index++) {
				Element item = postList.get(index);
				PostInfo pi;

				String sbutitle = item.select(post_subtitle).text();
				if (pagenum == 1 && index == 0) {
					title.setText(PostContentBuilder.getTitleHtml(sbutitle));
					sbutitle = null;
				}

				if (ipage.getPostList().size() <= index) {
					LinearLayout ll = (LinearLayout) mContext
							.getLayoutInflater().inflate(
									R.layout.post_list_item, null);
					pi = new PostInfo(ll);
					ipage.getPostList().add(pi);
				} else {
					pi = ipage.getPostList().get(index);
				}

				String datetime = item.select(post_datetime).text();
				pi.setDatetime(datetime);

				String content = item.select(post_content).html();

				Elements comments = item.select(post_comment);
				ArrayList<CommentInfo> cil = null;
				if (comments.size() > 0) {
					cil = new ArrayList<CommentInfo>();
					for (Element comment : comments) {
						CommentInfo ci = new CommentInfo();
						ci.author = comment.select(post_comment_author).text();
						ci.conntent = comment.select(post_comment_content)
								.html();
						cil.add(ci);
					}
				}

				pi.setContentSource(content, cil, sbutitle);

				Elements imgs = item.select(post_user_info1).select(
						post_user_info2);
				for (Element img : imgs) {
					String onerror = img.attr(post_user_info3);
					if (onerror != null && !onerror.isEmpty()
							&& onerror.indexOf(post_user_info4) != -1) {
						String userInfo = onerror.replace(post_user_info4, "");
						userInfo = userInfo.substring(1, userInfo.length() - 1);
						userInfo = clearUserInfo(userInfo);
						if (userInfo != null) {
							String[] values = userInfo.split(",");
							if (values.length > 6) {
								UserInfo iUser = userInfoList.get(values[6]);
								pi.setAuthor(iUser.username);
								pi.setHighlight(curHighLightAuthor);

								pi.setFloor("[" + values[2] + "楼]");
								pi.setPrestige(mContext
										.getString(R.string.user_info_prestige)
										+ Math.floor(iUser.rvrc / 10));
								pi.setPostcount(mContext
										.getString(R.string.user_info_postcount)
										+ iUser.postnum);
								String url = checkURL(iUser.avatar);
								if (url != null && !url.isEmpty()) {
									pi.setUrlAvatar(url.replaceAll("\"", ""));
								}
								String pid = values[5];
								pi.setPid(pid);
							} else {
								// TODO
							}
						}
						break;
					}
				}
			}
			syncToPostInfoList();

		}
	}

	private String checkURL(String url) {
		if (url != null && !url.isEmpty() && url.indexOf(keyword_url) != -1) {
			return url;
		} else if (tempInfo.containsKey(url)) {
			String[] values = tempInfo.get(url).split("\"");
			for (String value : values) {
				value = checkURL(value);
				if (value != null) {
					return value;
				}
			}
		}
		return null;
	}

	private String clearUserInfo(String value) {
		if (value != null && !value.isEmpty()) {
			Matcher matcher;
			String temp;
			StringBuffer sb;
			Integer key;
			String StrKey;

			matcher = PostContentBuilder.P_SQUARE_BRACKETS.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(0);
				key = randKey.nextInt(10000);
				StrKey = key.toString();
				matcher.appendReplacement(sb, StrKey);
				tempInfo.put(StrKey, temp);
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = PostContentBuilder.P_BRACES.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(0);
				key = randKey.nextInt(10000);
				StrKey = key.toString();
				matcher.appendReplacement(sb, StrKey);
				tempInfo.put(StrKey, temp);
			}
			matcher.appendTail(sb);
			value = sb.toString();

			return value;
		} else {
			return null;
		}
	}

	private void syncToPostInfoList() {
		Collections.sort(pageinfoList);
		postInfoList.clear();
		for (int index = 0; index < pageinfoList.size(); index++) {
			PageInfo cpi = pageinfoList.get(index);
			if (index > 0) {
				PageInfo ppi = pageinfoList.get(index - 1);
				LinearLayout ll = (LinearLayout) mContext.getLayoutInflater()
						.inflate(R.layout.post_load_item, null);
				PostInfo ipage;
				if (cpi.getIndex() - ppi.getIndex() != 1) {
					ipage = new PostInfo(ppi.getIndex() + 1, ll);
					ll.findViewById(R.id.pli_icon).setVisibility(View.VISIBLE);
					ll.findViewById(R.id.pli_text).setVisibility(View.VISIBLE);
				} else {
					ipage = new PostInfo(0, ll);
				}
				postInfoList.add(ipage);
			}
			postInfoList.addAll(cpi.getPostList());
		}
	}

	public void setHighlightAuthor(String value) {
		curHighLightAuthor = value;
	}

	public void setHighlightAuthor(int value) {
		curHighLightAuthor = getItem(value).getAuthor();
	}

	public String getQuoteInfo(int value) {
		return getItem(value).getQuoteContent();
	}

	public void refreshHighlightAuthor() {
		for (PostInfo pi : postInfoList) {
			boolean oldState = pi.isHighlight();

			pi.setHighlight(curHighLightAuthor);
			if (oldState && !pi.isHighlight()) {
				pi.getContentView().setBackgroundResource(R.drawable.msgbox2);
			} else if (!oldState && pi.isHighlight()) {
				pi.getContentView().setBackgroundResource(R.drawable.msgbox1);
			}
		}
	}

	public int getPositionByPageIndex(int index, int floorInPage) {
		PageInfo pi = getPageInfoByIndex(index);
		if (pi != null) {
			if (floorInPage >= pi.getPostList().size()) {
				floorInPage = 0;
			}
			return postInfoList.indexOf(pi.getPostList().get(floorInPage));
		}
		return -1;
	}

	public int getFloorInPageByFloorIndex(int index) {
		return index % pageSize;
	}

	public int getPageIndexByFloorIndex(int index) {
		Double pageIndex = Math.ceil((double) index / (double) pageSize);
		return pageIndex.intValue();
	}

	public boolean checkLoaded(int pageIndex) {
		PageInfo pi = getPageInfoByIndex(pageIndex);
		return (pi != null);
	}

	private PageInfo getPageInfoByIndex(int index) {

		Iterator<PageInfo> ipi = pageinfoList.iterator();
		while (ipi.hasNext()) {
			PageInfo pi = ipi.next();
			if (pi.getIndex() == index) {
				return pi;
			}
		}
		return null;
	}

	public void clearWebViewCache() {
		if (postInfoList != null && postInfoList.size() > 0) {
			for (PostInfo pi : postInfoList) {
				pi.clearCache();
			}
		}
	}

	@Override
	public int getCount() {
		return postInfoList.size() + 1;
	}

	@Override
	public PostInfo getItem(int arg0) {
		return postInfoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg0 == 0) {
			return title;
		} else {
			PostInfo pi = getItem(arg0 - 1);
			if (SharedInfoController.showAvatar()) {
				pi.tryLoadAvatar();
			} else {
				pi.addAvatarLoadByClick();
			}
			return pi.getView();
		}
	}

}
