package com.hxhxtla.ngaapp.homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.TopicInfo;
import com.hxhxtla.ngaapp.controller.ConfigController;
import com.hxhxtla.ngaapp.controller.PointTabController;

public class NgaAppMainActivity extends Activity implements OnGestureListener,
		OnTouchListener {

	private static final int MENU_DEL = 1;

	private static final float FLING_MIN_DISTANCE = 80;

	private static AllTopicListAdapter atla;

	private AlertDialog alert;

	private ViewFlipper vf;

	private GestureDetector gd;

	private PointTabController pointTabController;

	private ConfigController cctrl;

	private Button btn_next;
	private Button btn_pre;

	// private static Animation nextInAnimation;
	// private static Animation preInAnimation;
	// private static Animation nextOutAnimation;
	// private static Animation preOutAnimation;

	private void initView() {
		setContentView(R.layout.main);

		gd = new GestureDetector(this, this);

		vf = (ViewFlipper) findViewById(R.id.home_list);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.home_tab_bar);

		pointTabController = new PointTabController(linearLayout);

		this.addNewPage(HomeListAdapter.getCurrentPageCount());

		pointTabController.changePageOn(this.vf.getDisplayedChild());

		btn_next = (Button) findViewById(R.id.home_btn_next);
		btn_pre = (Button) findViewById(R.id.home_btn_pre);

		OnClickListener btnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (vf.getChildCount() > 1) {
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
		GridView gv = (GridView) vf.getCurrentView();
		return (HomeListAdapter) gv.getAdapter();
	}

	private void addNewPage(int num) {
		for (int i = 0; i < num; i++) {
			GridView gv = (GridView) getLayoutInflater().inflate(
					R.layout.home_list_itemview, null);

			HomeListAdapter hla = new HomeListAdapter(this);

			hla.setIndex_view(vf.getChildCount());

			gv.setAdapter(hla);

			vf.addView(gv);

			gv.setOnTouchListener(this);

			gv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					if (getCurrentHomeListAdapter()
							.getItem(position)
							.getId()
							.equals(NgaAppMainActivity.this
									.getString(R.string.add_topic_id))) {
						showTopicPicker();
					} else {

					}
				}
			});

			this.registerForContextMenu(gv);
		}

		int pageNum = vf.getChildCount();

		// if (pageNum > 1
		// && (nextInAnimation == null || preInAnimation == null
		// || nextOutAnimation == null || preOutAnimation == null)) {
		//
		// nextInAnimation = AnimationUtils.loadAnimation(this,
		// R.anim.push_right_in);
		// nextOutAnimation = AnimationUtils.loadAnimation(this,
		// R.anim.push_left_out);
		// preInAnimation = AnimationUtils.loadAnimation(this,
		// R.anim.push_left_in);
		// preOutAnimation = AnimationUtils.loadAnimation(this,
		// R.anim.push_right_out);
		// }

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
		int numVFChildren = vf.getChildCount();
		for (int index = 0; index < numVFChildren; index++) {
			GridView gv = (GridView) vf.getChildAt(index);
			HomeListAdapter hla = (HomeListAdapter) gv.getAdapter();
			hla.notifyDataSetChanged();
		}
	}

	private void showNextPage() {
		// this.vf.setInAnimation(nextInAnimation);
		// this.vf.setOutAnimation(nextOutAnimation);
		this.vf.showNext();
		pointTabController.changePageOn(this.vf.getDisplayedChild());
	}

	private void showPreviousPage() {
		// this.vf.setInAnimation(preInAnimation);
		// this.vf.setOutAnimation(preOutAnimation);
		this.vf.showPrevious();
		pointTabController.changePageOn(this.vf.getDisplayedChild());
	}

	private void dragPage(float distance) {
		vf.getCurrentView().setX(vf.getX() + distance);
//		vf.getChildAt(vf.getDisplayedChild() + 1)
//				.setX(vf.getWidth() + distance);
		// float abs_distace = Math.abs(distance);
		// if (distance > 0) {
		//
		// } else if (distance < 0) {
		//
		// }
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.gd.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float distace = e1.getX() - e2.getX();
		float abs_distacex = Math.abs(distace);
		if (abs_distacex > FLING_MIN_DISTANCE) {
			if (distace > 0) {
				showPreviousPage();
				return true;
			} else if (distace < 0) {
				showNextPage();
				return true;
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// float distance = e1.getX() - e2.getX();
		dragPage(distanceX);

		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return this.gd.onTouchEvent(event);
	}
}