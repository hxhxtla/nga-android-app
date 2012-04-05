package com.hxhxtla.ngaapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import android.content.Context;
import android.os.AsyncTask;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.IActivity;

public class GetArticlesListTask extends AsyncTask<String, String, Document> {
	private IActivity iactivity;

	public static String SERVER_URL;

	public GetArticlesListTask(IActivity value) {
		super();
		iactivity = value;
		Context c = (Context) iactivity;
		if (SERVER_URL == null) {
			SERVER_URL = c.getString(R.string.server_url);
		}
	}

	@Override
	protected void onPreExecute() {
		iactivity.showContectionProgressDialog();
	}

	@Override
	protected Document doInBackground(String... arg0) {
		String url = getArticlesListURL(arg0[0], arg0[1]);
		if (url == null) {
			// TODO
			return null;
		}
		Document document;
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
			conn.setRequestProperty("User-Agent", "3rd_part_android_app");
			conn.connect();
			publishProgress();
			is = conn.getInputStream();
			res = IOUtils.toString(is, "GBK");
			is.close();
			conn.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			document = DocumentHelper.parseText(res);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return document;
	}

	@Override
	protected void onProgressUpdate(String... arg0) {
		iactivity.showGettingProgressDialog();
	}

	@Override
	protected void onPostExecute(Document result) {
		iactivity.showLoadingProgressDialog();
		iactivity.callbackGetArticlesList(result);
	}

	private String getArticlesListURL(String fid, String pagenum) {
		try {
			Integer.parseInt(fid);
		} catch (NumberFormatException e) {
			return null;
		}
		String res = SERVER_URL + "/thread.php?fid=" + fid + "&page=" + pagenum
				+ "&rss=1";
		return res;
	}

}
