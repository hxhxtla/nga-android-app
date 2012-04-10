package com.hxhxtla.ngaapp.postlistpage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.GetServerDataTask;

public class PostListPageActivity extends Activity implements ITaskActivity {

	private ProgressDialog progressDialog;

	private PostListAdapter pla;

	private ListView lv;
	private TextView title;

	private boolean initialization = true;

	private int curPageNum = 1;
	private int maxPageNum;

	private String urlKeyword2;

	private ImageButton btn_next;
	private ImageButton btn_pre;
	private ImageButton btn_top;
	private ImageButton btn_end;

	private Button btn_refresh;

	private void initView() {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.post_list_page);

		this.setTitle(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST.get(0)
				.getName());

		title = (TextView) findViewById(R.id.post_title);

		lv = (ListView) findViewById(R.id.post_list);

		pla = new PostListAdapter(this);

		lv.setAdapter(pla);

		LinearLayout post_page_selector = (LinearLayout) findViewById(R.id.post_page_selector);

		btn_next = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_next);
		btn_pre = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_pre);
		btn_top = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_top);
		btn_end = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_end);
		btn_refresh = (Button) post_page_selector
				.findViewById(R.id.bar_btn_refresh);

		OnClickListener btnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == btn_next) {
					showPageByIndex(curPageNum + 1);
				} else if (v == btn_pre && curPageNum > 1) {
					showPageByIndex(curPageNum - 1);
				} else if (v == btn_top) {
					showPageByIndex(1);
				} else if (v == btn_end) {
					showPageByIndex(maxPageNum);
				} else if (v == btn_refresh) {
					refreshView(true);
				}
			}

		};
		btn_next.setOnClickListener(btnClickListener);
		btn_pre.setOnClickListener(btnClickListener);
		btn_top.setOnClickListener(btnClickListener);
		btn_end.setOnClickListener(btnClickListener);
		btn_refresh.setOnClickListener(btnClickListener);

		refreshView(true);
	}

	private void initData() {
		urlKeyword2 = this.getString(R.string.article_keyword2);
	}

	private void showPageByIndex(int index) {
		if (index > 0 && index <= maxPageNum) {
			curPageNum = index;
			refreshView(false);
			btn_refresh.setText(String.valueOf(index));
		}
	}

	private void refreshView(boolean status) {
		initialization = status;
		GetServerDataTask gsdt = new GetServerDataTask(
				PostListPageActivity.this);
		String url = SharedInfoController.RECENT_POST_URL + "&" + urlKeyword2
				+ String.valueOf(curPageNum);

		gsdt.execute(url);
	}

	public void callbackHander(String doc) {
		if (doc != null) {
			Document document = Jsoup.parse(doc);
			if (initialization) {
				setTitle(document);
				setPageNum(document);
			}
			pla.setData(document);
			pla.notifyDataSetChanged();
			closeContectionProgressDialog();
		}
	}

	private void setTitle(Document document) {
		if (document != null && title != null) {
			Elements maxpage = document.select(this
					.getString(R.string.post_title));
			if (maxpage.size() > 0) {
				title.setText(maxpage.get(0).text());
			} else {
				// TODO
			}
		}
	}

	private void setPageNum(Document document) {
		if (document != null) {
			Elements maxpage = document.select(this
					.getString(R.string.post_maxpage));
			if (maxpage.size() > 0) {
				Pattern pagenum = Pattern.compile("\\d+?");
				Matcher matcher = pagenum.matcher(maxpage.get(0).text());
				matcher.find();
				maxPageNum = Integer.parseInt(matcher.group());
			} else {
				maxPageNum = 1;
			}
		}
	}

	public void showContectionProgressDialog() {
		progressDialog = ProgressDialog.show(this,
				getString(R.string.articles_pd_title),
				getString(R.string.articles_pd_msg1));
	}

	public void showGettingProgressDialog() {
		progressDialog.setMessage(getString(R.string.articles_pd_msg2));
	}

	public void showLoadingProgressDialog() {
		progressDialog.setMessage(getString(R.string.articles_pd_msg3));
	}

	public void closeContectionProgressDialog() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initData();
		this.initView();
	}

}
