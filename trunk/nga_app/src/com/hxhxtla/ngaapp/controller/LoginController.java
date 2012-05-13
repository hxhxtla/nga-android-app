package com.hxhxtla.ngaapp.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.task.LoginTask;

public class LoginController {

	public static boolean logged = false;

	public static String ngaPassportCid;
	public static String ngaPassportUid;

	public AlertDialog dialog;

	public LoginTask loginTask;

	private EditText input_act;

	private EditText input_pwd;

	private Activity activity;

	private ConfigController configController;

	private String act;

	private String pwd;

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
		Builder br = new AlertDialog.Builder(activity);
		br.setTitle(R.string.menu_log);
		if (logged) {
			br.setMessage(R.string.menu_askForconfirm);
			br.setPositiveButton(R.string.menu_confirm,
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
								showLoginWindow(activity, configController);
							} else {
								// TODO
							}
						}
					});
			br.setNegativeButton(R.string.menu_cancel, null);
		} else {
			final View input_login = activity.getLayoutInflater().inflate(
					R.layout.login_window, null);

			input_act = (EditText) input_login.findViewById(R.id.input_account);
			input_pwd = (EditText) input_login
					.findViewById(R.id.input_passwoed);
			if (act != null) {
				input_act.setText(act);
			}
			if (pwd != null) {
				input_pwd.setText(pwd);
			}
			br.setView(input_login);
			br.setPositiveButton(R.string.menu_login,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							act = input_act.getText().toString().trim();
							pwd = input_pwd.getText().toString().trim();
							if (act.isEmpty() || pwd.isEmpty()) {
								Toast.makeText(activity,
										R.string.msg_login_cantNull,
										Toast.LENGTH_SHORT).show();
								showLoginWindow(activity, configController);
							} else if (activity instanceof ITaskActivity) {
								loginTask = new LoginTask(
										(ITaskActivity) activity);
								loginTask.execute(act, pwd);
							}
						}
					});

		}
		dialog = br.create();
		dialog.show();
	}
}
