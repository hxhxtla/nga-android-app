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
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.hxhxtla.ngaapp.controller.LoginController;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.postaction.PostActionActivity;
import com.hxhxtla.ngaapp.task.GetServerDataTask;
import com.hxhxtla.ngaapp.task.PostContentBuilder;

public class PostListPageActivity extends Activity implements ITaskActivity {

	private static final int MENU_QUOTE = 0;

	private static final int MENU_ADDHIGHLIGHT = 1;

	private ProgressDialog progressDialog;

	private PostListAdapter pla;

	private ListView lv;

	private boolean initialization = true;

	private int curPageNum = 1;
	private int maxPageNum = 1;

	private String urlKeyword2;

	private ImageButton btn_next;
	private ImageButton btn_pre;
	private ImageButton btn_top;
	private ImageButton btn_end;

	private Button btn_refresh;
	private TextView tv;

	private int navigateFloorInPage = 0;

	private GetServerDataTask gsdt;

	private void initView() {

		setContentView(R.layout.post_list_page);

		this.setTitle(SharedInfoController.DISPLAYED_HISTORY_TOPICLIST.get(0)
				.getName());

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
				PostInfo pi = pla.getItem(arg2 - 1);
				if (pi.getPageIndex() != 0) {
					curPageNum = pi.getPageIndex();
					refreshView(false);
				}

			}
		});
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
		gsdt = new GetServerDataTask(PostListPageActivity.this);
		String url = SharedInfoController.SERVER_URL + "/"
				+ SharedInfoController.RECENT_POST.getLink() + "&"
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
						R.string.msg_adsAlert, null);
			} else if (document.title().equals(
					getString(R.string.keyword_tip_check))) {
				int startIndex = doc.lastIndexOf("guestJs=");
				if (startIndex != -1) {
					refreshView(true);
					return;
				} else {
					SharedInfoController.showCommonAlertDialog(this,
							R.string.msg_errerMsg, null);
				}
			} else {
				if (initialization) {
					setPageNum(document);
					pla.setHighlightAuthor(SharedInfoController.RECENT_POST
							.getAuthor());
				}
				int indexS = doc.indexOf("commonui.userInfo.setAll(");
				int indexE = getEndIndex(indexS + 25, indexS + 25, doc);
				String userInfo = doc.substring(indexS + 25, indexE);
				pla.setData(document, curPageNum, userInfo);
				pla.notifyDataSetChanged();
				locatePageByIndex(curPageNum);
			}
		}
		if (gsdt != null) {
			gsdt = null;
		}
		closeContectionProgressDialog();
	}

	public int getEndIndex(int startIndexS, int startIndexE, String value) {
		int indexL = value.indexOf("(", startIndexS);
		int indexR = value.indexOf(")", startIndexE);
		if (indexL == -1 || indexL > indexR) {
			return indexR;
		} else {
			return getEndIndex(indexL + 1, indexR + 1, value);
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
		if (progressDialog != null) {
			progressDialog.show();
			progressDialog.setTitle(R.string.articles_pd_title);
			progressDialog
					.setMessage(this.getString(R.string.articles_pd_msg1));
		} else {
			progressDialog = ProgressDialog.show(this,
					getString(R.string.articles_pd_title),
					getString(R.string.articles_pd_msg1));
			progressDialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK
							&& event.getAction() == KeyEvent.ACTION_DOWN) {
						dialog.cancel();
						if (gsdt != null) {
							gsdt.cancel(false);
							gsdt = null;
						}
					}
					return true;
				}
			});
		}
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

	private void doPostAction() {
		SharedInfoController.POST_ACTION_TYPE = R.string.post_action_type_reply;
		startActivity(new Intent(this, PostActionActivity.class));
	}

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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (LoginController.logged) {
			menu.add(0, MENU_QUOTE, 0, R.string.post_page_quote);
		}
		menu.add(0, MENU_ADDHIGHLIGHT, 1, R.string.menu_add_highlight);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int index;
		switch (item.getItemId()) {
		case MENU_QUOTE:
			index = info.position;
			SharedInfoController.POST_ACTION_CONTENT_PRE_ADD = pla
					.getQuoteInfo(index - 1);
			doPostAction();
			break;
		case MENU_ADDHIGHLIGHT:
			index = info.position;
			menuHandler_addHighlight(index - 1);
			break;

		}

		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_post_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.postlist_menu_floorTo
				|| itemId == R.id.postlist_menu_pageTo) {

			final View input_navigate = getLayoutInflater().inflate(
					R.layout.post_list_navigate_dialog, null);
			Builder br = new AlertDialog.Builder(PostListPageActivity.this);
			br.setView(input_navigate);
			br.setTitle(R.string.post_list_navigate_dialog_title);
			final EditText et_input = (EditText) input_navigate
					.findViewById(R.id.plnd_input);
			if (itemId == R.id.postlist_menu_pageTo) {
				br.setPositiveButton(R.string.post_list_page,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String input = et_input.getText().toString()
										.trim();
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
			} else if (itemId == R.id.postlist_menu_floorTo) {
				br.setPositiveButton(R.string.post_list_floor,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String input = et_input.getText().toString()
										.trim();
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
		} else if (itemId == R.id.postlist_menu_reply) {
			if (LoginController.logged) {
				doPostAction();
			} else {
				SharedInfoController.showCommonAlertDialog(this,
						R.string.msg_not_login, null);
			}
		}
		return true;
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
