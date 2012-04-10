package com.hxhxtla.ngaapp.postlistpage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.GetServerDataTask;

public class PostListPageActivity extends Activity implements ITaskActivity {

	private ProgressDialog progressDialog;

	private PostListAdapter pla;

	private ListView lv;

	private int curPageNum = 1;

	private String urlKeyword2;

	private void initView() {
		setContentView(R.layout.post_list_page);

		this.setTitle(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST.get(0)
				.getName());

		lv = (ListView) findViewById(R.id.post_list);

		pla = new PostListAdapter(this);

		lv.setAdapter(pla);

		refreshView();
	}

	private void initData() {
		urlKeyword2 = this.getString(R.string.article_keyword2);
	}

	private void refreshView() {
		GetServerDataTask gsdt = new GetServerDataTask(
				PostListPageActivity.this);
		String url = SharedInfoController.RECENT_POST_URL + "&" + urlKeyword2
				+ String.valueOf(curPageNum);

		gsdt.execute(url);
	}

	public void callbackHander(String doc) {
		if (doc != null) {
			pla.setData(doc);
			pla.notifyDataSetChanged();
			closeContectionProgressDialog();
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
