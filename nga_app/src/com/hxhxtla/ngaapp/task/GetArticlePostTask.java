package com.hxhxtla.ngaapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.os.AsyncTask;

import com.hxhxtla.ngaapp.R;

public class GetArticlePostTask extends AsyncTask<String, String, Document> {
	private Activity iactivity;

	public static String SERVER_URL;

	public GetArticlePostTask(Activity value) {
		super();
		iactivity = value;
		if (SERVER_URL == null) {
			SERVER_URL = iactivity.getString(R.string.server_url);
		}
	}

	@Override
	protected void onPreExecute() {

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
		document = Jsoup.parse(res);
		return document;
	}

	@Override
	protected void onProgressUpdate(String... arg0) {

	}

	@Override
	protected void onPostExecute(Document result) {

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
