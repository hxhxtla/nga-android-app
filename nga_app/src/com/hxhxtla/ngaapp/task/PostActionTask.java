package com.hxhxtla.ngaapp.task;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;

import android.os.AsyncTask;

public class PostActionTask extends AsyncTask<String, String, String> {
	private ITaskActivity iactivity;

	public String step;
	public String pid;
	public String action;
	public String fid;
	public String tid;
	public String _ff;
	public String attachments;
	public String attachments_check;
	public String force_topic_key;
	public String filter_key;
	public String post_subject;
	public String post_content;
	public String checkkey;

	public PostActionTask(ITaskActivity iactivity) {
		super();
		this.iactivity = iactivity;
	}

	@Override
	protected String doInBackground(String... arg0) {
		String url = iactivity.getActivity().getString(R.string.login_url);
		return null;
	}

}
