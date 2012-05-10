package com.hxhxtla.ngaapp.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.LoginController;
import com.hxhxtla.ngaapp.controller.SharedInfoController;

public class LoginTask extends AsyncTask<String, String, String> {
	private ITaskActivity iactivity;

	public LoginTask(ITaskActivity iactivity) {
		super();
		this.iactivity = iactivity;
	}

	@Override
	protected void onPreExecute() {
		iactivity.showContectionProgressDialog();
	}

	@Override
	protected String doInBackground(String... params) {
		String url = iactivity.getActivity().getString(R.string.login_url);
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> httpparams = new ArrayList<NameValuePair>();

		httpparams.add(new BasicNameValuePair("_act", "login"));
		httpparams.add(new BasicNameValuePair("email", params[0]));
		httpparams.add(new BasicNameValuePair("password", params[1]));
		httpparams.add(new BasicNameValuePair("to", iactivity.getActivity()
				.getString(R.string.server_url)));

		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(httpparams,
					HTTP.UTF_8));
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
		if (httpResponse != null
				&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			List<Cookie> cookies = SharedInfoController.httpClient
					.getCookieStore().getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("_sid")) {
					LoginController.ngaPassportCid = cookie.getValue();
				}
				if (cookie.getName().equalsIgnoreCase("_178c")) {
					LoginController.ngaPassportUid = cookie.getValue().split(
							"%23")[0];
				}
			}
			if (LoginController.ngaPassportUid != null
					&& !LoginController.ngaPassportUid.isEmpty()
					&& LoginController.ngaPassportCid != null
					&& !LoginController.ngaPassportCid.isEmpty()) {
				LoginController.logged = true;
			} else {
				LoginController.logged = false;
			}
		} else {
			// TODO
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... arg0) {
		iactivity.showGettingProgressDialog();
	}

	@Override
	protected void onPostExecute(String value) {
		iactivity.showLoadingProgressDialog();
		iactivity.callbackHander(null);
	}
}
