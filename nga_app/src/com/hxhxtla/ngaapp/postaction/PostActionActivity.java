package com.hxhxtla.ngaapp.postaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.PostActionTask;

public class PostActionActivity extends Activity implements ITaskActivity {
	private EditText subject;
	private EditText content;
	private EditText quote;
	private Button submit;
	private ProgressDialog progressDialog;
	private PostActionTask pat;

	private void initView() {
		setContentView(R.layout.post_page);

		subject = (EditText) findViewById(R.id.post_action_title);

		content = (EditText) findViewById(R.id.post_action_content);

		submit = (Button) findViewById(R.id.post_action_submit);

		if (SharedInfoController.POST_ACTION_TYPE == R.string.post_action_type_reply
				&& SharedInfoController.POST_ACTION_CONTENT_PRE_ADD != null
				&& !SharedInfoController.POST_ACTION_CONTENT_PRE_ADD.isEmpty()) {

			LinearLayout module_quote = (LinearLayout) findViewById(R.id.post_action_module_quote);
			module_quote.setVisibility(View.VISIBLE);
			quote = (EditText) findViewById(R.id.post_action_quote);
			quote.setText(SharedInfoController.POST_ACTION_CONTENT_PRE_ADD);
			SharedInfoController.POST_ACTION_CONTENT_PRE_ADD = null;

			ToggleButton tb = (ToggleButton) findViewById(R.id.post_page_editText_display);
			tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						quote.setVisibility(View.VISIBLE);
					} else {
						quote.setVisibility(View.GONE);
					}

				}
			});
		}

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String fid = PostActionActivity.this
						.getString(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST
								.get(0).getId());
				String tid = SharedInfoController.RECENT_POST.getTID();
				String text_subject = subject.getText().toString();
				String text_content;
				if (quote != null
						&& quote.getText().toString().trim().length() > 0) {
					text_content = quote.getText().toString() + "\n"
							+ content.getText().toString();
				} else if (content.getText().toString().trim().length() > 0) {
					text_content = content.getText().toString();
				} else {
					SharedInfoController.showCommonAlertDialog(
							PostActionActivity.this, R.string.post_cant_msg,
							null);
					return;
				}
				if (SharedInfoController.CTRL_PREFIX_DISPLAY) {
					text_content = getString(R.string.PREFIX_DEFAULT) + "\n"
							+ text_content;
				}
				pat = new PostActionTask(PostActionActivity.this);
				pat.execute(fid, tid, text_subject, text_content);

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
		if (doc != null) {
			if (doc.indexOf(getString(R.string.post_compeleted_msg)) != -1) {
				android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						PostActionActivity.this.finish();
					}
				};
				SharedInfoController.showCommonAlertDialog(this,
						R.string.post_compeleted_msg, listener);
			} else {

				SharedInfoController.showCommonAlertDialog(this,
						R.string.post_failed_msg, null);
			}
		}
		closeContectionProgressDialog();
	}

	@Override
	public void showContectionProgressDialog() {

		progressDialog = ProgressDialog.show(this,
				getString(R.string.articles_pd_title),
				getString(R.string.articles_pd_msg1));
		progressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					dialog.cancel();
					if (pat != null) {
						pat.cancel(false);
						pat = null;
					}
				}
				return true;
			}
		});
	}

	@Override
	public void showGettingProgressDialog() {
		progressDialog.setMessage(getString(R.string.articles_pd_msg2));

	}

	@Override
	public void showLoadingProgressDialog() {
		progressDialog.setMessage(getString(R.string.articles_pd_msg3));
	}

	public void closeContectionProgressDialog() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
