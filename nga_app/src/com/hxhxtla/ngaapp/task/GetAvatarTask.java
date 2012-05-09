package com.hxhxtla.ngaapp.task;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class GetAvatarTask extends AsyncTask<String, String, HttpResponse> {

	private DefaultHttpClient httpClient;

	public GetAvatarTask() {
		super();
		httpClient = new DefaultHttpClient();
	}

	@Override
	protected HttpResponse doInBackground(String... arg0) {
		String url = arg0[0];
		if (url == null) {
			// TODO
			return null;
		}
		HttpGet httpRequest = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			return httpResponse;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(HttpResponse result) {

		if (result != null
				&& result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity httpEntity = result.getEntity();
			InputStream is;
			try {
				is = httpEntity.getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
