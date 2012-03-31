package com.hxhxtla.ngaapp.homepage;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class PageListAdapter extends BaseAdapter {

	private static List<GridView> pageList;

	public PageListAdapter() {
		pageList = new ArrayList<GridView>();
	}

	public void addPage(GridView value) {
		pageList.add(value);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pageList.size();
	}

	@Override
	public GridView getItem(int index) {
		// TODO Auto-generated method stub
		return pageList.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return pageList.get(position);
	}

}
