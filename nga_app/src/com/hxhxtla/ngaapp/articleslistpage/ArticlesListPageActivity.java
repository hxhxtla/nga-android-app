package com.hxhxtla.ngaapp.articleslistpage;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.IActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.GetArticlesListTask;

public class ArticlesListPageActivity extends Activity implements IActivity {

	private ArticlesListAdapter ala;

	private ProgressDialog progressDialog;

	private HistoryTopicListAdapter htla;

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.articles_list_page);

		ListView lv = (ListView) findViewById(R.id.articles_list);

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

	}

	private void initData() {
		refreshView();
	}

	private void refreshView() {
		GetArticlesListTask galt = new GetArticlesListTask(
				ArticlesListPageActivity.this);

		galt.execute(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST.get(0)
				.getId());
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
			closeContectionProgressDialog();
		} else {
			// TODO
		}

	}

}
