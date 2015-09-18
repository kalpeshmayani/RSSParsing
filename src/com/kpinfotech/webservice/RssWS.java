package com.kpinfotech.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.kpinfotech.global.AppConstant;
import com.kpinfotech.global.AppMethod;
import com.kpinfotech.interfaces.RssWSAsync;
import com.kpinfotech.model.RssItem;
import com.kpinfotech.parser.RssParser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

public class RssWS extends AsyncTask<Void, Void, String> implements OnCancelListener {
	
	Activity activity;
	Fragment fragment;
	
	List<NameValuePair> nameValuePair;
	
	String serviceType, wsName;
	Boolean isLoaderEnable = true;
	
	Exception error = null;
	
	RssWSAsync i_RssWSAsync;
	
	ArrayList<RssItem> rssItems;
	
	public RssWS(Activity activity, String serviceType, List<NameValuePair> nameValuePair, String wsName, boolean isLoaderEnable) {
		this.i_RssWSAsync = (RssWSAsync) activity;
		this.activity = activity;
		this.serviceType = serviceType;
		this.nameValuePair = nameValuePair;
		this.wsName = wsName;
		this.isLoaderEnable = isLoaderEnable;
	}
	
	public RssWS(Activity activity, Fragment fragment, String serviceType, List<NameValuePair> nameValuePair, String wsName, boolean isLoaderEnable) {
		this.i_RssWSAsync = (RssWSAsync) fragment;
		this.activity = activity;
		this.fragment = fragment;
		this.serviceType = serviceType;
		this.nameValuePair = nameValuePair;
		this.wsName = wsName;
		this.isLoaderEnable = isLoaderEnable;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		if (isLoaderEnable)
			AppMethod.showProgressDialog(activity, AppConstant.PLEASE_WAIT);
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		this.cancel(true);
		AppMethod.dismissProgressDialog(activity);
	}
	
	@Override
	protected String doInBackground(Void... params) {
		
		rssItems = null;
		try {
			RssParser parser = new RssParser();
			rssItems = parser.parse(getInputStream(wsName));
		} catch (Exception e) {
            this.error = e;
            Log.e("RssWS =>", e.getMessage());
		}
		
		return null;
	}
	
	public InputStream getInputStream(String link) {
		try {
			URL url = new URL(link);
			return url.openConnection().getInputStream();
		} catch (IOException e) {
			Log.w("RSS", "Exception while retrieving the input stream", e);
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		if (isLoaderEnable) {
            AppMethod.dismissProgressDialog(activity);
        }
		
		i_RssWSAsync.onRssWSResponse(serviceType, rssItems, error);
	}

}