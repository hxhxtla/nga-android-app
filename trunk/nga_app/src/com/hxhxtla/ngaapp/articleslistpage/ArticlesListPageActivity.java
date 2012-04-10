package com.hxhxtla.ngaapp.articleslistpage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ArticleInfo;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.postlistpage.PostListPageActivity;
import com.hxhxtla.ngaapp.task.GetServerDataTask;

public class ArticlesListPageActivity extends Activity implements ITaskActivity {

	private ArticlesListAdapter ala;

	private ProgressDialog progressDialog;

	private HistoryTopicListAdapter htla;

	private int curPageNum = 1;

	private ImageButton btn_next;
	private ImageButton btn_pre;
	private ImageButton btn_top;

	private Button btn_refresh;

	private ListView lv;

	private String urlKeyword;
	private String urlKeyword1;
	private String urlKeyword2;
	private String urlKeyword3;

	private void initView() {

		setContentView(R.layout.articles_list_page);

		lv = (ListView) findViewById(R.id.articles_list);

		ala = new ArticlesListAdapter(this);

		lv.setAdapter(ala);

		Spinner spinner = (Spinner) findViewById(R.id.articles_history_topiclist);

		htla = new HistoryTopicListAdapter(this,
				SharedInfoController.DISPLAYED_HISTORY_TOPICLIST);

		spinner.setAdapter(htla);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 != 0) {
					SharedInfoController.addTopicHistory(htla.getItem(arg2));
					htla.notifyDataSetChanged();
					refreshView();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		LinearLayout articles_page_selector = (LinearLayout) findViewById(R.id.articles_page_selector);
		articles_page_selector.findViewById(R.id.bar_separator_end)
				.setVisibility(View.GONE);
		articles_page_selector.findViewById(R.id.bar_btn_end).setVisibility(
				View.GONE);
		btn_next = (ImageButton) articles_page_selector
				.findViewById(R.id.bar_btn_next);
		btn_pre = (ImageButton) articles_page_selector
				.findViewById(R.id.bar_btn_pre);
		btn_top = (ImageButton) articles_page_selector
				.findViewById(R.id.bar_btn_top);

		btn_refresh = (Button) articles_page_selector
				.findViewById(R.id.bar_btn_refresh);

		showPageByIndex(curPageNum);

		OnClickListener btnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == btn_next) {
					showPageByIndex(curPageNum + 1);
				} else if (v == btn_pre && curPageNum > 1) {
					showPageByIndex(curPageNum - 1);
				} else if (v == btn_top) {
					showPageByIndex(1);
				} else if (v == btn_refresh) {
					refreshView();
				}
			}

		};
		btn_next.setOnClickListener(btnClickListener);
		btn_pre.setOnClickListener(btnClickListener);
		btn_top.setOnClickListener(btnClickListener);
		btn_refresh.setOnClickListener(btnClickListener);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ArticleInfo ai = ala.getItem(arg2);
				SharedInfoController.RECENT_POST_URL = ai.getLink();
				startActivity(new Intent(ArticlesListPageActivity.this,
						PostListPageActivity.class));
			}
		});

	}

	private void initData() {
		if (SharedInfoController.SERVER_URL == null) {
			SharedInfoController.SERVER_URL = this
					.getString(R.string.server_url);
		}
		urlKeyword = this.getString(R.string.article_keyword);
		urlKeyword1 = this.getString(R.string.article_keyword1);
		urlKeyword2 = this.getString(R.string.article_keyword2);
		urlKeyword3 = this.getString(R.string.server_rss);
	}

	private void showPageByIndex(int index) {
		if (index > 0) {
			curPageNum = index;
			refreshView();
			btn_refresh.setText(String.valueOf(index));
		}
	}

	private void refreshView() {
		GetServerDataTask gsdt = new GetServerDataTask(
				ArticlesListPageActivity.this);
		String url = SharedInfoController.SERVER_URL
				+ urlKeyword
				+ urlKeyword1
				+ SharedInfoController.DISPLAYED_HISTORY_TOPICLIST.get(0)
						.getId() + "&" + urlKeyword2
				+ String.valueOf(curPageNum) + "&" + urlKeyword3;

		gsdt.execute(url);
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

	public void callbackHander(String doc) {
		if (doc != null) {
			ala.setData(doc);
			ala.notifyDataSetChanged();
			htla.notifyDataSetChanged();
			lv.setSelectionAfterHeaderView();
			closeContectionProgressDialog();
		} else {
			// TODO
		}

	}

}
