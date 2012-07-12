package com.hxhxtla.ngaapp.utils;

import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.controller.LoginController;
import com.hxhxtla.ngaapp.controller.SharedInfoController;

public class HttpRequestUtils {
	public final static int GET = 1;
	public final static int POST = 2;

	public static HttpUriRequest getHttpRequest(int method, String url,
			ITaskActivity iactivity) {
		HttpUriRequest httpRequest;
		if (method == GET) {
			httpRequest = new HttpGet(url);
		} else if (method == POST) {
			httpRequest = new HttpPost(url);
		} else {
			return null;
		}
		httpRequest
				.setHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpRequest.setHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		httpRequest.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpRequest.setHeader("Accept-Language", "zh-cn,zh;q=0.8");
		httpRequest.setHeader("Cache-Control", "max-age=0");
		httpRequest.setHeader("Connection", "keep-alive");
		httpRequest.setHeader("Host", "bbs.ngacn.cc");
		httpRequest
				.setHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.43 Safari/536.11");

		String CookieStr = "";
		if (LoginController.logged) {
			CookieStr = CookieStr
					+ iactivity.getActivity().getString(R.string.nga_uid) + "="
					+ LoginController.ngaPassportUid + ";"
					+ iactivity.getActivity().getString(R.string.nga_cid) + "="
					+ LoginController.ngaPassportCid;

		} else {

			List<Cookie> cookies = SharedInfoController.httpClient
					.getCookieStore().getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(
						iactivity.getActivity().getString(R.string.nga_uid))) {
					if (CookieStr.length() > 0) {
						CookieStr = CookieStr + ";";
					}
					CookieStr = CookieStr + cookie.getName() + "="
							+ cookie.getValue();
				} else if (cookie.getName().equalsIgnoreCase("lastvisit")) {
					if (CookieStr.length() > 0) {
						CookieStr = CookieStr + ";";
					}
					CookieStr = CookieStr + "guestJs=" + cookie.getValue();
				}
			}
		}
		httpRequest.setHeader("Cookie", CookieStr);
		return httpRequest;
	}
}
