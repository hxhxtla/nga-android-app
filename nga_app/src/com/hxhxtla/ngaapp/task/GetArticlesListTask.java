package com.hxhxtla.ngaapp.task;

import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

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
	protected Document doInBackground(String... arg0) {
		String url = getArticlesListURL(arg0[0]);
		SAXReader sr = new SAXReader();
		Document document = null;
		try {
			document = sr.read(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return document;
	}

	@Override
	protected void onPostExecute(Document result) {
		iactivity.callbackGetArticlesList(result);
	}

	private String getArticlesListURL(String fid) {
		try {
			Integer.parseInt(fid);
		} catch (NumberFormatException e) {
			return null;
		}
		String res = SERVER_URL + "/thread.php?fid=" + fid + "&rss=1";
		return res;
	}

}
