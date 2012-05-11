package com.hxhxtla.ngaapp.postlistpage;

import java.util.regex.Matcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.ITaskActivity;
import com.hxhxtla.ngaapp.bean.PostInfo;
import com.hxhxtla.ngaapp.controller.PostContentBuilder;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.GetServerDataTask;

public class PostListPageActivity extends Activity implements ITaskActivity {

	private static final int MENU_ADDHIGHLIGHT = 1;

	private ProgressDialog progressDialog;

	private PostListAdapter pla;

	private ListView lv;
	private TextView title;

	private boolean initialization = true;

	private int curPageNum = 1;
	private int maxPageNum = 1;

	private String urlKeyword2;

	private ImageButton btn_next;
	private ImageButton btn_pre;
	private ImageButton btn_top;
	private ImageButton btn_end;

	private Button btn_refresh;
	private Button btn_pageTo;
	private Button btn_floorTo;

	private TextView tv;

	private int navigateFloorInPage = 0;

	private void initView() {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.post_list_page);

		this.setTitle(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST.get(0)
				.getName());

		title = (TextView) findViewById(R.id.post_title);

		lv = (ListView) findViewById(R.id.post_list);

		pla = new PostListAdapter(this);

		lv.setAdapter(pla);

		LinearLayout post_page_selector = (LinearLayout) findViewById(R.id.post_page_selector);

		btn_next = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_next);
		btn_pre = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_pre);
		btn_top = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_top);
		btn_end = (ImageButton) post_page_selector
				.findViewById(R.id.bar_btn_end);
		btn_refresh = (Button) post_page_selector
				.findViewById(R.id.bar_btn_refresh);

		btn_pageTo = (Button) findViewById(R.id.post_tab_pageTo);
		btn_floorTo = (Button) findViewById(R.id.post_tab_floorTo);

		tv = (TextView) findViewById(R.id.post_tab_maxPageNum);

		OnClickListener postTabBtnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				int targetPageNum = 0;
				if (v == btn_next) {
					targetPageNum = curPageNum + 1;
				} else if (v == btn_pre && curPageNum > 1) {
					targetPageNum = curPageNum - 1;
				} else if (v == btn_top) {
					targetPageNum = 1;
				} else if (v == btn_end) {
					targetPageNum = maxPageNum;
				} else if (v == btn_refresh) {
					refreshView(true);
					return;
				}
				if (targetPageNum > 0 && targetPageNum <= maxPageNum) {
					curPageNum = targetPageNum;
					if (pla.checkLoaded(targetPageNum)) {
						locatePageByIndex(curPageNum);
					} else {
						refreshView(false);
					}
				}
			}

		};
		btn_next.setOnClickListener(postTabBtnClickListener);
		btn_pre.setOnClickListener(postTabBtnClickListener);
		btn_top.setOnClickListener(postTabBtnClickListener);
		btn_end.setOnClickListener(postTabBtnClickListener);
		btn_refresh.setOnClickListener(postTabBtnClickListener);

		this.registerForContextMenu(lv);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				PostInfo pi = pla.getItem(arg2);
				if (pi.getPageIndex() != 0) {
					curPageNum = pi.getPageIndex();
					refreshView(false);
				}

			}
		});

		OnClickListener navigateBtnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				final View input_navigate = getLayoutInflater().inflate(
						R.layout.post_list_navigate_dialog, null);
				Builder br = new AlertDialog.Builder(PostListPageActivity.this);
				br.setView(input_navigate);
				br.setTitle(R.string.post_list_navigate_dialog_title);
				final EditText et_input = (EditText) input_navigate
						.findViewById(R.id.plnd_input);
				if (v == btn_pageTo) {
					br.setPositiveButton(R.string.post_list_page,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String input = et_input.getText()
											.toString().trim();
									if (!input.isEmpty()) {
										int targetNum = Integer.parseInt(input);
										if (targetNum > 0
												&& targetNum <= maxPageNum) {
											curPageNum = targetNum;
											curPageNum = targetNum;
											if (pla.checkLoaded(targetNum)) {
												locatePageByIndex(curPageNum);
											} else {
												refreshView(false);
											}
											dialog.dismiss();
										} else {
											Toast.makeText(
													PostListPageActivity.this,
													R.string.msg_outOfPageIndex,
													Toast.LENGTH_SHORT).show();
										}
									}
								}
							});
				} else if (v == btn_floorTo) {
					br.setPositiveButton(R.string.post_list_floor,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String input = et_input.getText()
											.toString().trim();
									if (!input.isEmpty()) {
										int targetNum = Integer.parseInt(input);
										int pageIndexNum = pla
												.getPageIndexByFloorIndex(targetNum);
										if (pageIndexNum > 0
												&& pageIndexNum <= maxPageNum) {
											curPageNum = pageIndexNum;
											navigateFloorInPage = pla
													.getFloorInPageByFloorIndex(targetNum);
											if (pla.checkLoaded(curPageNum)) {
												locatePageByIndex(curPageNum);
											} else {
												refreshView(false);
											}
											dialog.dismiss();
										} else {
											Toast.makeText(
													PostListPageActivity.this,
													R.string.msg_outOfPageIndex,
													Toast.LENGTH_SHORT).show();
										}
									}
								}
							});
				}

				AlertDialog dialog = br.create();
				dialog.show();

			}
		};
		btn_pageTo.setOnClickListener(navigateBtnClickListener);
		btn_floorTo.setOnClickListener(navigateBtnClickListener);
	}

	private void initData() {
		refreshView(true);
		urlKeyword2 = this.getString(R.string.article_keyword2);
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		SharedInfoController.HAS_WIFI = (wifi == State.CONNECTED);
	}

	private void refreshView(boolean status) {
		initialization = status;
		GetServerDataTask gsdt = new GetServerDataTask(
				PostListPageActivity.this);
		String url = SharedInfoController.RECENT_POST.getLink() + "&"
				+ urlKeyword2 + String.valueOf(curPageNum);

		gsdt.execute(url);
	}

	private void locatePageByIndex(int index) {
		btn_refresh.setText(String.valueOf(index));
		lv.setSelection(pla.getPositionByPageIndex(index, navigateFloorInPage));
		navigateFloorInPage = 0;
	}

	public void callbackHander(String doc) {
		if (doc != null) {
			Document document = Jsoup.parse(doc);
			if (document.title().equals(getString(R.string.keyword_ads_check))) {
				SharedInfoController.showCommonAlertDialog(this,
						R.string.msg_adsAlert);
			} else if (document.title().equals(
					getString(R.string.keyword_tip_check))) {
				SharedInfoController.showCommonAlertDialog(this,
						R.string.msg_needLogin);
			} else {
				if (initialization) {
					setTitle(document);
					setPageNum(document);
					pla.setHighlightAuthor(SharedInfoController.RECENT_POST
							.getAuthor());
				}
				pla.setData(document, curPageNum);
				pla.notifyDataSetChanged();
				locatePageByIndex(curPageNum);
				closeContectionProgressDialog();
			}
		}
	}

	private void setTitle(Document document) {
		if (document != null && title != null) {
			Elements maxpage = document.select(this
					.getString(R.string.post_title));
			if (maxpage.size() > 0) {
				title.setText(maxpage.get(0).text());
			} else {
				// TODO
			}
		}
	}

	private void setPageNum(Document document) {
		if (document != null) {
			Elements maxpage = document.select(this
					.getString(R.string.post_maxpage));
			if (maxpage.size() > 0) {
				Matcher matcher = PostContentBuilder.P_PAGENUM.matcher(maxpage
						.get(0).text());
				matcher.find();
				maxPageNum = Integer.parseInt(matcher.group());
			} else {
				maxPageNum = 1;
			}

			tv.setText("(共 " + String.valueOf(maxPageNum) + " 页)");
		}
	}

	private void menuHandler_addHighlight(int index) {
		pla.setHighlightAuthor(index);
		pla.refreshHighlightAuthor();
	}

	public void showContectionProgressDialog() {
		progressDialog = ProgressDialog.show(this,
				getString(R.string.articles_pd_title),
				getString(R.string.articles_pd_msg1));
	}

	public void showGettingProgressDialog() {
		progressDialog.setMessage(getString(R.string.articles_pd_msg2));
	}

	public void showLoadingProgressDialog() {
		progressDialog.setMessage(getString(R.string.articles_pd_msg3));
	}

	public void closeContectionProgressDialog() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initData();
		this.initView();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, MENU_ADDHIGHLIGHT, 0, R.string.menu_add_highlight);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case MENU_ADDHIGHLIGHT:
			int index = info.position;
			menuHandler_addHighlight(index);
			break;

		}

		return super.onContextItemSelected(item);
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	protected void onStop() {
		if (pla != null) {
			pla.clearWebViewCache();
		}
		super.onStop();
	}

}
