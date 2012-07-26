package com.hxhxtla.ngaapp.optionscontrol;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.controller.ConfigController;
import com.hxhxtla.ngaapp.controller.SharedInfoController;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OptionsControlActivity extends Activity {
	private CheckBox ctrl_avatar_cb_show;
	private CheckBox ctrl_avatar_cb_show_wifi;
	private CheckBox ctrl_image_cb_show;
	private CheckBox ctrl_image_cb_show_wifi;
	private CheckBox ctrl_prefix_cb_show;

	private ConfigController cc;

	private void initView() {
		setContentView(R.layout.controls);
		ctrl_avatar_cb_show = (CheckBox) findViewById(R.id.ctrl_avatar_cb_show);
		ctrl_avatar_cb_show_wifi = (CheckBox) findViewById(R.id.ctrl_avatar_cb_show_wifi);
		ctrl_image_cb_show = (CheckBox) findViewById(R.id.ctrl_image_cb_show);
		ctrl_image_cb_show_wifi = (CheckBox) findViewById(R.id.ctrl_image_cb_show_wifi);
		ctrl_prefix_cb_show = (CheckBox) findViewById(R.id.ctrl_prefix_cb_show);

		ctrl_avatar_cb_show.setChecked(SharedInfoController.CTRL_AVATAR_SHOW);
		ctrl_avatar_cb_show_wifi
				.setChecked(SharedInfoController.CTRL_AVATAR_SHOW_WIFI);

		ctrl_image_cb_show.setChecked(SharedInfoController.CTRL_IMAGE_SHOW);
		ctrl_image_cb_show_wifi
				.setChecked(SharedInfoController.CTRL_IMAGE_SHOW_WIFI);

		ctrl_prefix_cb_show
				.setChecked(SharedInfoController.CTRL_PREFIX_DISPLAY);

		ctrl_avatar_cb_show
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (cc.saveCtrlAvatarShow(isChecked)) {
							SharedInfoController.CTRL_AVATAR_SHOW = isChecked;
						} else {
							ctrl_avatar_cb_show
									.setChecked(SharedInfoController.CTRL_AVATAR_SHOW);
							// TODO: show alert
						}
						ctrl_avatar_cb_show_wifi
								.setEnabled(SharedInfoController.CTRL_AVATAR_SHOW);
					}
				});

		ctrl_avatar_cb_show_wifi
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (cc.saveCtrlAvatarShowWifi(isChecked)) {
							SharedInfoController.CTRL_AVATAR_SHOW_WIFI = isChecked;
						} else {
							ctrl_avatar_cb_show_wifi
									.setChecked(SharedInfoController.CTRL_AVATAR_SHOW_WIFI);
							// TODO: show alert
						}
					}
				});

		ctrl_image_cb_show
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (cc.saveCtrlImageShow(isChecked)) {
							SharedInfoController.CTRL_IMAGE_SHOW = isChecked;
						} else {
							ctrl_image_cb_show
									.setChecked(SharedInfoController.CTRL_IMAGE_SHOW);
							// TODO: show alert
						}
						ctrl_image_cb_show_wifi
								.setEnabled(SharedInfoController.CTRL_IMAGE_SHOW);
					}
				});

		ctrl_image_cb_show_wifi
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (cc.saveCtrlImageShowWifi(isChecked)) {
							SharedInfoController.CTRL_IMAGE_SHOW_WIFI = isChecked;
						} else {
							ctrl_avatar_cb_show_wifi
									.setChecked(SharedInfoController.CTRL_IMAGE_SHOW_WIFI);
							// TODO: show alert
						}
					}
				});

		ctrl_prefix_cb_show
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (cc.saveCtrlPrefixShow(isChecked)) {
							SharedInfoController.CTRL_PREFIX_DISPLAY = isChecked;
						} else {
							ctrl_prefix_cb_show
									.setChecked(SharedInfoController.CTRL_PREFIX_DISPLAY);
							// TODO: show alert
						}
					}
				});
	}

	private void initData() {
		cc = new ConfigController(this);
	}

	// /////////////////////////////////////////////////////////Override
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initData();
		this.initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedInfoController.CURRENT_ACTIVITY = this;
	}
}
