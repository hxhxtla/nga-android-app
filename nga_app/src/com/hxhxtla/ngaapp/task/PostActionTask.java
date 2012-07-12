package com.hxhxtla.ngaapp.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.utils.HttpRequestUtils;

public class PostActionTask extends AsyncTask<String, String, String> {
	private ITaskActivity iactivity;

	public PostActionTask(ITaskActivity iactivity) {
		super();
		this.iactivity = iactivity;
	}

	@Override
	protected String doInBackground(String... value) {
		String url = iactivity.getActivity()
				.getString(R.string.post_action_url);
		HttpUriRequest httpRequest = HttpRequestUtils.getHttpRequest(
				HttpRequestUtils.POST, url, iactivity);

		List<NameValuePair> httpparams = new ArrayList<NameValuePair>();
		httpparams.add(new BasicNameValuePair("step", "2"));
		httpparams.add(new BasicNameValuePair("action", "reply"));
		httpparams.add(new BasicNameValuePair("fid", value[0]));
		httpparams.add(new BasicNameValuePair("tid", value[1]));
		httpparams.add(new BasicNameValuePair("post_subject", value[2]));
		httpparams.add(new BasicNameValuePair("post_content", value[3]));
//		httpparams.add(new BasicNameValuePair("checkkey", checkkey));
//		httpparams.add(new BasicNameValuePair("pid", ""));
//		httpparams.add(new BasicNameValuePair("_ff", ""));
//		httpparams.add(new BasicNameValuePair("attachments", ""));
//		httpparams.add(new BasicNameValuePair("attachments_check", ""));
//		httpparams.add(new BasicNameValuePair("filter_key", ""));
//		httpparams.add(new BasicNameValuePair("force_topic_key", ""));

		try {
			UrlEncodedFormEntity httpRequestEntity = new UrlEncodedFormEntity(
					httpparams, "GBK");
			httpRequest.setHeader("Content-Length",
					String.valueOf(httpRequestEntity.getContentLength()));
			((HttpPost) httpRequest).setEntity(httpRequestEntity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpResponse httpResponse = null;
		if (SharedInfoController.httpClient.getCookieStore() != null) {
			SharedInfoController.httpClient.getCookieStore().clear();
		}
		try {
			httpResponse = SharedInfoController.httpClient.execute(httpRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String strResult = null;
		if (httpResponse != null
				&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			try {
				strResult = EntityUtils.toString(httpResponse.getEntity(),
						"GBK");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return strResult;
	}
}
