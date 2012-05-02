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

	public static boolean logged = false;

	public static String ngaPassportCid;
	public static String ngaPassportUid;

	public Button btn_login;

	public Button btn_logout;

	public AlertDialog dialog;

	private EditText input_act;

	private EditText input_pwd;

	private Activity activity;

	private ConfigController configController;

	private static LoginController _instance;

	public static void initializeHttpClient(String uid, String cid) {
		if (uid != null && !uid.isEmpty() && cid != null && !cid.isEmpty()) {
			ngaPassportUid = uid;
			ngaPassportCid = cid;
			logged = true;
		}
	}

	public static void clearCache() {
		_instance = null;
	}

	public static LoginController getInstance() {
		if (_instance == null) {
			_instance = new LoginController();
		}
		return _instance;
	}

	public void showLoginWindow(Activity valuea, ConfigController valuecc) {
		activity = valuea;
		configController = valuecc;
		final View input_login = activity.getLayoutInflater().inflate(
				R.layout.login_window, null);

		input_act = (EditText) input_login.findViewById(R.id.input_account);
		input_pwd = (EditText) input_login.findViewById(R.id.input_passwoed);
		Builder br = new AlertDialog.Builder(activity);
		br.setTitle(R.string.menu_log);
		br.setView(input_login);
		br.setPositiveButton(R.string.menu_login,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String act = input_act.getText().toString().trim();
						String pwd = input_pwd.getText().toString().trim();
						if (act.isEmpty() || pwd.isEmpty()) {
							Toast.makeText(activity,
									R.string.msg_login_cantNull,
									Toast.LENGTH_SHORT).show();
						} else if (activity instanceof ITaskActivity) {
							LoginTask loginTask = new LoginTask(
									(ITaskActivity) activity);
							loginTask.execute(act, pwd);
						}
					}
				});
		br.setNegativeButton(R.string.menu_logout,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						logged = false;
						ngaPassportCid = null;
						ngaPassportUid = null;
						if (configController.clearLoginInfo()) {
							Toast.makeText(activity,
									R.string.msg_logout_success,
									Toast.LENGTH_SHORT).show();
							btn_logout.setVisibility(View.GONE);
							btn_login.setVisibility(View.VISIBLE);
						} else {
							// TODO
						}
					}
				});
		dialog = br.create();
		dialog.show();
		btn_login = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		btn_logout = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
		if (logged) {
			btn_login.setVisibility(View.GONE);
			btn_logout.setVisibility(View.VISIBLE);
		} else {
			btn_logout.setVisibility(View.GONE);
			btn_login.setVisibility(View.VISIBLE);
		}
	}
}
