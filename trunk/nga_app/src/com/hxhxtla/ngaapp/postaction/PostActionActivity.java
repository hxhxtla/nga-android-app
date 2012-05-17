package com.hxhxtla.ngaapp.postaction;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;

public class PostActionActivity extends Activity implements ITaskActivity {
	private void initView() {
		setContentView(R.layout.post_page);

		EditText subject = (EditText) findViewById(R.id.post_action_title);

		EditText content = (EditText) findViewById(R.id.post_action_content);

		Button submit = (Button) findViewById(R.id.post_action_submit);
	}

	private void initData() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initData();
		this.initView();
	}

	@Override
	public void callbackHander(String doc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showContectionProgressDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showGettingProgressDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLoadingProgressDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
