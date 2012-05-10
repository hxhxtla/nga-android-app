package com.hxhxtla.ngaapp.task;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.LoginController;
import com.hxhxtla.ngaapp.controller.SharedInfoController;

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
		if (LoginController.logged) {
			url = url + "&"
					+ iactivity.getActivity().getString(R.string.nga_uid) + "="
					+ LoginController.ngaPassportUid + "&"
					+ iactivity.getActivity().getString(R.string.nga_cid) + "="
					+ LoginController.ngaPassportCid;
		}
		HttpGet httpRequest = new HttpGet(url);
		HttpResponse httpResponse = null;
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
