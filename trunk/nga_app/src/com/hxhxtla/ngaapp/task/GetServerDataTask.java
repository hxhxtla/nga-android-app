package com.hxhxtla.ngaapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import android.os.AsyncTask;

import com.hxhxtla.ngaapp.bean.ITaskActivity;

public class GetServerDataTask extends AsyncTask<String, String, String> {
	private ITaskActivity iactivity;

	public GetServerDataTask(ITaskActivity value) {
		super();
		iactivity = value;
	}

	@Override
	protected void onPreExecute() {
		iactivity.showContectionProgressDialog();
	}

	@Override
	protected String doInBackground(String... arg0) {
		String url = arg0[0];
		if (url == null) {
			// TODO
			return null;
		}
		URL url_rss;
		try {
			url_rss = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		InputStream is;
		HttpURLConnection conn;
		String res;
		try {
			conn = (HttpURLConnection) url_rss.openConnection();
			conn.setReadTimeout(3000);
			conn.connect();
			publishProgress();
			is = conn.getInputStream();
			res = IOUtils.toString(is, "GBK");
			IOUtils.closeQuietly(is);
			is.close();
			conn.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}

	@Override
	protected void onProgressUpdate(String... arg0) {
		iactivity.showGettingProgressDialog();
	}

	@Override
	protected void onPostExecute(String result) {
		iactivity.showLoadingProgressDialog();
		iactivity.callbackHander(result);
	}

}
