package com.hxhxtla.ngaapp.postaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.PostActionTask;

public class PostActionActivity extends Activity implements ITaskActivity {
	private EditText subject;
	private EditText content;
	private Button submit;

	private void initView() {
		setContentView(R.layout.post_page);

		subject = (EditText) findViewById(R.id.post_action_title);

		content = (EditText) findViewById(R.id.post_action_content);

		submit = (Button) findViewById(R.id.post_action_submit);

		if (SharedInfoController.POST_ACTION_CONTENT_PRE_ADD != null
				&& !SharedInfoController.POST_ACTION_CONTENT_PRE_ADD.isEmpty()) {
			content.setText(SharedInfoController.POST_ACTION_CONTENT_PRE_ADD);
		}

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String fid = PostActionActivity.this
						.getString(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST
								.get(0).getId());
				String tid = SharedInfoController.RECENT_POST.getTID();
				String text_subject = subject.getText().toString();
				String text_content = content.getText().toString();
				new PostActionTask(PostActionActivity.this).execute(fid, tid,
						text_subject, text_content);

			}
		});
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
