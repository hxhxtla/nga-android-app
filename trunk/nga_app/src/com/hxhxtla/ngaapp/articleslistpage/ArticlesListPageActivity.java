package com.hxhxtla.ngaapp.articleslistpage;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.hxhxtla.ngaapp.R;
import com.hxhxtla.ngaapp.bean.IActivity;
import com.hxhxtla.ngaapp.controller.SharedInfoController;
import com.hxhxtla.ngaapp.task.GetArticlesListTask;

public class ArticlesListPageActivity extends Activity implements IActivity {

	private GetArticlesListTask galt;

	private ArticlesListAdapter ala;

	private void initView() {
		setContentView(R.layout.articles_list_page);

		ListView lv = (ListView) findViewById(R.id.articles_list);

		ala = new ArticlesListAdapter(this);

		lv.setAdapter(ala);

	}

	private void initData() {
		if (galt == null) {
			galt = new GetArticlesListTask(ArticlesListPageActivity.this);
		}

		galt.execute(SharedInfoController.DISPLAYED_ARTICLE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initData();
		this.initView();
	}

	@Override
	public void callbackGetArticlesList(Document doc) {
		if (doc != null) {
			Element channel = (Element) doc.getRootElement().element(
					this.getString(R.string.channel));
			Iterator it = channel
					.elementIterator(this.getString(R.string.item));
			ala.setData(it);
			ala.notifyDataSetChanged();
		} else {
			// TODO
		}

	}

}
