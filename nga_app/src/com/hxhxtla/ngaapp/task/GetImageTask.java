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

import com.hxhxtla.ngaapp.bean.IImageTask;
import com.hxhxtla.ngaapp.utils.SaveFileToLocalUtils;

public class GetImageTask extends AsyncTask<String, String, String> {

	private DefaultHttpClient httpClient;

	private List<IImageTask> pcb;

	public String imageUUID;

	public String imageLocalURL;

	public boolean needToSave = false;

	private Bitmap image;

	public GetImageTask(IImageTask value) {
		super();
		httpClient = new DefaultHttpClient();
		pcb = new ArrayList<IImageTask>();
		pcb.add(value);
	}

	public void addTaskDestination(IImageTask value) {
		if (image == null) {
			pcb.add(value);
		} else {
			value.callImageBackHander(this);
		}
	}

	@Override
	protected String doInBackground(String... arg0) {
		String imageURL = arg0[0];
		if (imageURL == null) {
			// TODO
			return null;
		}
		HttpGet httpRequest = new HttpGet(imageURL);
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
				image = BitmapFactory.decodeStream(is);
				if (needToSave) {
					imageLocalURL = SaveFileToLocalUtils.saveImage(image,
							imageUUID);
				}
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
		if (image != null) {
			for (IImageTask item : pcb) {
				item.callImageBackHander(this);
			}
		}
	}

	public Bitmap getImage() {
		return image;
	}
}
