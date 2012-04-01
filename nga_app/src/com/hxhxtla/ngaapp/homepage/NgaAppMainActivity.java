package com.hxhxtla.ngaapp.homepage;

import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.TopicInfo;
import com.hxhxtla.ngaapp.controller.ConfigController;
import com.hxhxtla.ngaapp.controller.PointTabController;

public class NgaAppMainActivity extends Activity {

	private static final int MENU_DEL = 1;

	private int curPageIndex = 0;

	private static AllTopicListAdapter atla;

	private AlertDialog alert;

	private ViewFlow vf;

	private PointTabController pointTabController;

	private ConfigController cctrl;

	private Button btn_next;
	private Button btn_pre;

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

		this.addNewPage(HomeListAdapter.getCurrentPageCount(this));

		pointTabController.changePageOn(curPageIndex);

		btn_next = (Button) findViewById(R.id.home_btn_next);
		btn_pre = (Button) findViewById(R.id.home_btn_pre);

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
		cctrl = new ConfigController(this);
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
					String curTopicId = getCurrentHomeListAdapter().getItem(
							position).getId();
					if (curTopicId.equals(NgaAppMainActivity.this
							.getString(R.string.add_topic_id))) {
						showTopicPicker();
					} else {
						// TODO
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
					Toast.makeText(
							NgaAppMainActivity.this,
							NgaAppMainActivity.this
									.getString(R.string.msg_topicExist),
							Toast.LENGTH_SHORT).show();
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
			while (vf.getViewsCount() > HomeListAdapter
					.getCurrentPageCount(this)) {
				PageListAdapter pla = (PageListAdapter) vf.getAdapter();
				pla.removePage();
				pla.notifyDataSetChanged();
				pointTabController.setNumPage(vf.getViewsCount());
			}

			notifyAllDataSetChanged();
			Toast.makeText(
					NgaAppMainActivity.this,
					NgaAppMainActivity.this
							.getString(R.string.msg_delTopicSucc),
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
			menu.add(0, MENU_DEL, 0, this.getString(R.string.menu_name1));
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
}