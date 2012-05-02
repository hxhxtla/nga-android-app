package com.hxhxtla.ngaapp.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.task.LoginTask;

public class LoginController {
	public static Button btn_login;

	public static AlertDialog dialog;

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
		final View input_login = activity.getLayoutInflater().inflate(
				R.layout.login_window, null);

		final EditText input_act = (EditText) input_login
				.findViewById(R.id.input_account);
		final EditText input_pwd = (EditText) input_login
				.findViewById(R.id.input_passwoed);

		Builder br = new AlertDialog.Builder(activity);
		br.setTitle(R.string.menu_login);
		br.setView(input_login);
		br.setPositiveButton(R.string.menu_login,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String act = input_act.getText().toString().trim();
						String pwd = input_pwd.getText().toString().trim();
						if (act.isEmpty() || pwd.isEmpty()) {
							Toast.makeText(
									activity,
									activity.getString(R.string.msg_login_cantNull),
									Toast.LENGTH_SHORT).show();
						} else if (activity instanceof ITaskActivity) {
							LoginTask loginTask = new LoginTask(
									(ITaskActivity) activity);
							loginTask.execute(act, pwd);
						}
					}
				});
		dialog = br.create();
		dialog.show();
		btn_login = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
	}
}
