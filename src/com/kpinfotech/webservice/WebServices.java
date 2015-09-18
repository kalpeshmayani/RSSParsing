package com.kpinfotech.webservice;

import com.kpinfotech.global.WSConstant;

import android.app.Activity;

public class WebServices {

	// RSS
	
	public void getItemList(Activity activity, Boolean isLoaderEnable) {
		new RssWS(activity, WSConstant.RSS_RT_GET_ITEM_LIST, null, WSConstant.RSS_WS_GET_ITEM_LIST, isLoaderEnable).execute();
	}
	
}