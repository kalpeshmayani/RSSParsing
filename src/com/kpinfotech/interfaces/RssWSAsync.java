package com.kpinfotech.interfaces;

import java.util.ArrayList;

import com.kpinfotech.model.RssItem;

public interface RssWSAsync {
	void onRssWSResponse(String serviceType, ArrayList<RssItem> rssItems, Exception error);
}