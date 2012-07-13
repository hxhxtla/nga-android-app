package com.hxhxtla.ngaapp.homepage;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.articleslistpage.ArticlesListPageActivity;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.bean.TopicInfo;
import com.hxhxtla.ngaapp.controller.ConfigController;
import com.hxhxtla.ngaapp.controller.LoginController;
import com.hxhxtla.ngaapp.controller.PointTabController;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.optionscontrol.OptionsControlActivity;

public class NgaAppMainActivity extends Activity implements ITaskActivity {

	private static final int MENU_DEL = 1;

	private int curPageIndex = 0;

	private static AllTopicListAdapter atla;

	private AlertDialog alert;

	private ViewFlow vf;

	private PointTabController pointTabController;

	private ConfigController cctrl;

	private ImageButton btn_next;
	private ImageButton btn_pre;

	private ProgressDialog progressDialog;

	private void initView() {
		setContentView(R.layout.main);

		vf = (ViewFlow) findViewById(R.id.home_list);

		PageListAdapter pla = new PageListAdapter();

		vf.setAdapter(pla);
		vf.setOnViewSwitchListener(new ViewSwitchListener() {

			@Override
			public void onSwitched(View arg0, int arg1) {

				curPageIndex = arg1;

				pointTabController.changePageOn(arg1);
			}
		});

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.home_tab_bar);

		pointTabController = new PointTabController(linearLayout);

		this.addNewPage(HomeListAdapter.getCurrentPageCount());

		pointTabController.changePageOn(curPageIndex);

		btn_next = (ImageButton) findViewById(R.id.home_btn_next);
		btn_pre = (ImageButton) findViewById(R.id.home_btn_pre);

		OnClickListener btnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (vf.getViewsCount() > 1) {
					if (v == btn_next) {
						showNextPage();
					} else if (v == btn_pre) {
						showPreviousPage();
					}
				}
			}

		};
		btn_next.setOnClickListener(btnClickListener);
		btn_pre.setOnClickListener(btnClickListener);
	}

	private void initData() {
		if (SharedInfoController.SERVER_URL == null) {
			SharedInfoController.SERVER_URL = this
					.getString(R.string.server_url);
		}
		cctrl = new ConfigController(this);
		BasicHttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 10000);
		SharedInfoController.httpClient = new DefaultHttpClient(params);
		SharedInfoController.CTRL_AVATAR_SHOW = cctrl.getCtrlAvatarShow();
		SharedInfoController.CTRL_AVATAR_SHOW_WIFI = cctrl
				.getCtrlAvatarShowWifi();
		SharedInfoController.CTRL_PREFIX_DISPLAY = cctrl.getCtrlPrefixShow();
		LoginController.initializeHttpClient(cctrl.getNgaPassportUid(),
				cctrl.getNgaPassportCid());
		HomeListAdapter.setTopicInfoList(cctrl.getTopiclist());
	}

	public boolean saveTopicInfoListToConfig() {
		return cctrl.saveTopiclist(HomeListAdapter.getTopicInfoList());
	}

	private HomeListAdapter getCurrentHomeListAdapter() {
		GridView gv = (GridView) vf.getSelectedView();
		return (HomeListAdapter) gv.getAdapter();
	}

	private void addNewPage(int num) {
		PageListAdapter pla = (PageListAdapter) vf.getAdapter();
		for (int i = 0; i < num; i++) {
			GridView gv = (GridView) getLayoutInflater().inflate(
					R.layout.home_list_item_view, null);

			HomeListAdapter hla = new HomeListAdapter(this);

			hla.setIndex_view(vf.getViewsCount());

			gv.setAdapter(hla);

			gv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					TopicInfo ti = getCurrentHomeListAdapter()
							.getItem(position);
					int curTopicId = ti.getId();
					if (curTopicId == R.string.add_topic_id) {
						showTopicPicker();
					} else {
						SharedInfoController.addTopicHistory(ti);
						startActivity(new Intent(NgaAppMainActivity.this,
								ArticlesListPageActivity.class));
					}
				}
			});

			this.registerForContextMenu(gv);

			pla.addPage(gv);
		}

		pla.notifyDataSetChanged();

		int pageNum = vf.getViewsCount();

		pointTabController.setNumPage(pageNum);
	}

	private void showTopicPicker() {
		final View topicPicker = getLayoutInflater().inflate(
				R.layout.topic_picker_alert, null);

		final ListView lv = (ListView) topicPicker
				.findViewById(R.id.topic_picker_toast_list);
		if (atla == null) {
			atla = new AllTopicListAdapter(this);
			atla.syncAllTopicInfoList(HomeListAdapter.getTopicInfoList());
		}

		lv.setAdapter(atla);

		Builder br = new AlertDialog.Builder(this);

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HomeListAdapter curhla = getCurrentHomeListAdapter();
				TopicInfo ati = atla.getItem(arg2);
				if (curhla.checkItemNotExist(ati)) {
					ati.setView(null);
					if (curhla.addNewItem(ati) == HomeListAdapter.ADD_STATUS_FULL) {
						addNewPage(1);
					}
					notifyAllDataSetChanged();
					atla.removeItem(ati);
					alert.cancel();
					saveTopicInfoListToConfig();
				} else {
					Toast.makeText(NgaAppMainActivity.this,
							R.string.msg_topicExist, Toast.LENGTH_SHORT).show();
				}
			}

		});

		br.setView(topicPicker);
		br.setTitle(R.string.topic_picker_toast_add);
		br.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});

		alert = br.create();

		alert.show();
	}

	private void menuHandler_deleteTopic(int position) {
		TopicInfo item = getCurrentHomeListAdapter().deleteItemAt(position);
		if (item != null) {
			while (vf.getViewsCount() > HomeListAdapter.getCurrentPageCount()) {
				PageListAdapter pla = (PageListAdapter) vf.getAdapter();
				pla.removePage();
				pla.notifyDataSetChanged();
				pointTabController.setNumPage(vf.getViewsCount());
			}

			notifyAllDataSetChanged();
			Toast.makeText(NgaAppMainActivity.this, R.string.msg_delTopicSucc,
					Toast.LENGTH_SHORT).show();
			saveTopicInfoListToConfig();
			if (atla != null) {
				item.setView(null);
				atla.addItem(item);
			}
		}
	}

	private void notifyAllDataSetChanged() {
		int numVFChildren = vf.getViewsCount();
		for (int index = 0; index < numVFChildren; index++) {
			GridView gv = (GridView) vf.getChildAt(index);
			HomeListAdapter hla = (HomeListAdapter) gv.getAdapter();
			hla.notifyDataSetChanged();
		}
	}

	private void showNextPage() {
		vf.setSelection(curPageIndex + 1);
	}

	private void showPreviousPage() {
		vf.setSelection(curPageIndex - 1);
	}

	// /////////////////////////////////////////////////////////Override
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initData();
		this.initView();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		if (!getCurrentHomeListAdapter().isAddItem(info.position)) {
			super.onCreateContextMenu(menu, v, menuInfo);
			menu.setHeaderTitle(getCurrentHomeListAdapter().getItem(
					info.position).getName());
			menu.add(Menu.NONE, MENU_DEL, Menu.NONE, R.string.menu_topic_del);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case MENU_DEL:
			int index = info.position;
			menuHandler_deleteTopic(index);
			break;

		}

		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mainmenu_login:
			LoginController.getInstance().showLoginWindow(this, cctrl);
			break;
		case R.id.mainmenu_setting:
			startActivity(new Intent(NgaAppMainActivity.this,
					OptionsControlActivity.class));
			break;
		case R.id.mainmenu_exit:
			this.finish();
			break;

		}
		return true;
	}

	@Override
	public void callbackHander(String doc) {
		int msg;
		closeContectionProgressDialog();
		if (LoginController.getInstance().loginTask != null) {
			LoginController.getInstance().loginTask = null;
		}
		if (LoginController.logged) {
			msg = R.string.msg_login_success;
			LoginController.getInstance().dialog.dismiss();
			cctrl.saveLoginInfo(LoginController.ngaPassportUid,
					LoginController.ngaPassportCid);
			LoginController.clearCache();
		} else {
			msg = R.string.msg_login_fail;
			LoginController.getInstance().showLoginWindow(this, cctrl);
		}
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

	}

	@Override
	public void showContectionProgressDialog() {
		progressDialog = ProgressDialog.show(this,
				getString(R.string.menu_login), getString(R.string.btn_login),
				false, true, new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						if (LoginController.getInstance().loginTask != null) {
							LoginController.getInstance().loginTask
									.cancel(false);
							LoginController.getInstance().loginTask = null;
						}
					}
				});
	}

	@Override
	public void showGettingProgressDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLoadingProgressDialog() {
		// TODO Auto-generated method stub

	}

	public void closeContectionProgressDialog() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	@Override
	public Activity getActivity() {
		return this;
	}
}