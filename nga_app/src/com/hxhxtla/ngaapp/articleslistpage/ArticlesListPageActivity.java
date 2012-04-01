package com.hxhxtla.ngaapp.articleslistpage;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;

import android.app.Activity;
import android.os.Bundle;

import com.hxhxtla.ngaapp.bean.IActivity;
import com.hxhxtla.ngaapp.task.GetArticlesListTask;

public class ArticlesListPageActivity extends Activity implements IActivity {

	private GetArticlesListTask galt;
	private String curTopicId;

	private void initView() {
		// TODO Auto-generated method stub

	}

	private void initData() {
		if (galt == null) {
			galt = new GetArticlesListTask(ArticlesListPageActivity.this);
		}
		galt.execute(curTopicId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initData();
		this.initView();
	}

	@Override
	public void callbackGetArticlesList(Document doc) {
		Element channel = (Element) doc.getRootElement().element("channel");
		Iterator i = channel.elementIterator("item");

	}

}
