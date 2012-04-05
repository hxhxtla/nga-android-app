package com.hxhxtla.ngaapp.articleslistpage;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.IActivity;
import com.hxhxtla.ngaapp.controller.PointTabController;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.GetArticlesListTask;

public class ArticlesListPageActivity extends Activity implements IActivity {

	private ArticlesListAdapter ala;

	private ProgressDialog progressDialog;

	private HistoryTopicListAdapter htla;

	private PointTabController pointTabController;

	private int curPageNum = 1;

	private ImageButton btn_next;
	private ImageButton btn_pre;
	private ImageButton btn_top;

	private ImageButton btn_refresh;

	private TextView tv;

	private ListView lv;

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

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

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.articles_tab_bar);

		pointTabController = new PointTabController(linearLayout);

		pointTabController.setNumPage(10);

		tv = (TextView) findViewById(R.id.articles_page_num);

		showPageByIndex(curPageNum);

		btn_next = (ImageButton) findViewById(R.id.articles_btn_next);
		btn_pre = (ImageButton) findViewById(R.id.articles_btn_pre);
		btn_top = (ImageButton) findViewById(R.id.articles_btn_top);

		btn_refresh = (ImageButton) findViewById(R.id.articles_btn_refresh);

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

	}

	private void initData() {

	}

	private void showPageByIndex(int index) {
		if (index > 0) {
			curPageNum = index;
			refreshView();
			tv.setText(String.valueOf(index));
			pointTabController.changePageOn(index - 1);
		}
	}

	private void refreshView() {
		GetArticlesListTask galt = new GetArticlesListTask(
				ArticlesListPageActivity.this);

		galt.execute(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST.get(0)
				.getId(), String.valueOf(curPageNum));
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

	@Override
	public void callbackGetArticlesList(Document doc) {
		if (doc != null) {
			Element channel = (Element) doc.getRootElement().element(
					this.getString(R.string.channel));
			Iterator it = channel
					.elementIterator(this.getString(R.string.item));
			ala.setData(it);
			ala.notifyDataSetChanged();
			htla.notifyDataSetChanged();
			lv.setSelectionAfterHeaderView();
			closeContectionProgressDialog();
		} else {
			// TODO
		}

	}

}
