package com.hxhxtla.ngaapp.postlistpage;

import android.app.Activity;
import android.app.ProgressDialog;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;

public class PostListPageActivity extends Activity implements ITaskActivity {

	private ProgressDialog progressDialog;

	private PostListAdapter pla;

	public void callbackHander(String doc) {
		pla.setData(doc);
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

}
