package com.hxhxtla.ngaapp.controller;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.task.LoginTask;

public class LoginController {
	public static Button btn_login;

	public static Dialog dialog;

	public static boolean logged = false;

	public static String ngaPassportCid;
	public static String ngaPassportUid;

	public static void initializeHttpClient(String uid, String cid) {
		if (uid != null && !uid.isEmpty() && cid != null && !cid.isEmpty()) {
			ngaPassportUid = uid;
			ngaPassportCid = cid;
			logged = true;
		}
	}

	public static void showLoginWindow(final Activity activity) {
		dialog = new Dialog(activity);
		dialog.setContentView(R.layout.login_window);
		dialog.setTitle(R.string.menu_login);
		btn_login = (Button) dialog.findViewById(R.id.btn_login);
		final EditText input_act = (EditText) dialog
				.findViewById(R.id.input_account);
		final EditText input_pwd = (EditText) dialog
				.findViewById(R.id.input_passwoed);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String act = input_act.getText().toString().trim();
				String pwd = input_pwd.getText().toString().trim();
				if (act.isEmpty() || pwd.isEmpty()) {
					Toast.makeText(activity,
							activity.getString(R.string.msg_login_cantNull),
							Toast.LENGTH_SHORT).show();
				} else if (activity instanceof ITaskActivity) {
					LoginTask loginTask = new LoginTask(
							(ITaskActivity) activity);
					loginTask.execute(act, pwd);
				}

			}
		});
		dialog.show();
	}
}
