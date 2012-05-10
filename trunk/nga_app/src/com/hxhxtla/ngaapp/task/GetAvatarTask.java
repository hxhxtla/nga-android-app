package com.hxhxtla.ngaapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.hxhxtla.ngaapp.bean.PostInfo;

public class GetAvatarTask extends AsyncTask<String, String, String> {

	private DefaultHttpClient httpClient;

	private List<PostInfo> pi;

	private String avatarURL;

	private Bitmap avatar;

	public GetAvatarTask(PostInfo value) {
		super();
		httpClient = new DefaultHttpClient();
		pi = new ArrayList<PostInfo>();
		pi.add(value);
	}

	public void addTaskDestination(PostInfo value) {
		if (avatar == null) {
			pi.add(value);
		} else {
			value.callAvatarHandler(avatar);
		}
	}

	@Override
	protected String doInBackground(String... arg0) {
		avatarURL = arg0[0];
		if (avatarURL == null) {
			// TODO
			return null;
		}
		HttpGet httpRequest = new HttpGet(avatarURL);
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (httpResponse != null
				&& httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity httpEntity = httpResponse.getEntity();
			try {
				InputStream is = httpEntity.getContent();
				avatar = BitmapFactory.decodeStream(is);
				is.close();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (avatar != null) {
			for (PostInfo item : pi) {
				item.callAvatarHandler(avatar);
			}
		}
	}
}
