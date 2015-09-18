package com.kpinfotech.activity;

import java.util.ArrayList;

import com.kpinfotech.adapter.Adapter_ItemList;
import com.kpinfotech.global.AppConstant;
import com.kpinfotech.global.AppMethod;
import com.kpinfotech.global.WSConstant;
import com.kpinfotech.interfaces.RssWSAsync;
import com.kpinfotech.model.RssItem;
import com.kpinfotech.rssparsing.R;
import com.kpinfotech.webservice.WebServices;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ItemList extends Activity implements OnClickListener, OnRefreshListener, RssWSAsync {
	
	Activity activity;
	
	TextView tvtitle;
	Button btnleft, btnright;
	
	ListView lvlist;
	SwipeRefreshLayout swipeLayout;
	
	String userId;
	WebServices ws;
	
	ArrayList<RssItem> list;
	
	Adapter_ItemList a_ItemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_itemlist);
		
		init();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		default:
			break;
		}
	}
	
	private void init() {
		activity = (Activity) ItemList.this;
		userId = AppMethod.getStringPreference(activity, AppConstant.PREF_USERID);
		ws = new WebServices();
		
		btnleft = (Button) findViewById(R.id.btnleft);
		btnleft.setVisibility(View.INVISIBLE);
		
		btnright = (Button) findViewById(R.id.btnright);
		btnright.setVisibility(View.INVISIBLE);
		
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		tvtitle.setSelected(true);
		tvtitle.setText("Item list");
		
		lvlist = (ListView) findViewById(R.id.lvlist);
		
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
		
		getItemList();
	}
	
	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
                getItemList();
            }
        }, AppConstant.PULL_TO_REFRESH);
	}
	
	private void getItemList() {
		if (AppMethod.isNetworkConnected(activity)) {
			ws.getItemList(activity, true);
		} else
			AppMethod.showToast(activity, AppConstant.NO_INTERNET_CONNECTION);
	}
	
	@Override
	public void onRssWSResponse(String serviceType, ArrayList<RssItem> rssItems, Exception error) {
		
		if (error == null) {

			if (serviceType.equalsIgnoreCase(WSConstant.RSS_RT_GET_ITEM_LIST)) {
				showData(rssItems);
			}

		} else {
			AppMethod.showToast(activity, error.getLocalizedMessage());
		}
		
	}

	private void showData(ArrayList<RssItem> rssItems) {
		
		list = rssItems;
		
		if(list == null) {
			list = new ArrayList<RssItem>();
		}
		
		if(list.size() == 0) {
			AppMethod.showToast(activity, AppConstant.NO_LIST_FOUND);
		}
		
		a_ItemList = new Adapter_ItemList(activity, list);
		lvlist.setAdapter(a_ItemList);
		a_ItemList.notifyDataSetChanged();
		
	}

}