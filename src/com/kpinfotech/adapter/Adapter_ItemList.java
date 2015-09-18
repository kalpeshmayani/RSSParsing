package com.kpinfotech.adapter;

import java.util.ArrayList;

import com.kpinfotech.global.AppConstant;
import com.kpinfotech.global.AppMethod;
import com.kpinfotech.imageloader.AnimateFirstDisplayListener;
import com.kpinfotech.imageloader.DisplayImageOption;
import com.kpinfotech.model.RssItem;
import com.kpinfotech.rssparsing.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_ItemList extends BaseAdapter {
	
	LayoutInflater mInflater;
	Activity activity;

	ArrayList<RssItem> list;
	
	public Adapter_ItemList(Activity activity, ArrayList<RssItem> list) {
		mInflater = LayoutInflater.from(activity);
		this.activity = activity;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	public class Holder {
		TextView tvtitle, tvdescription;
		Button btndownload;
		ImageView ivthumbnail;
		LinearLayout llbg, llview;
	}

	public void intialize(View ConvertView, Holder holder) {
		holder.tvtitle = (TextView) ConvertView.findViewById(R.id.tvtitle);
		holder.tvdescription = (TextView) ConvertView.findViewById(R.id.tvdescription);
		holder.btndownload = (Button) ConvertView.findViewById(R.id.btndownload);
		holder.ivthumbnail = (ImageView) ConvertView.findViewById(R.id.ivthumbnail);
		holder.llbg = (LinearLayout) ConvertView.findViewById(R.id.llbg);
		holder.llview = (LinearLayout) ConvertView.findViewById(R.id.llview);
		
//		holder.tvtitle.setSelected(true);
//		holder.tvdescription.setSelected(true);
		
		holder.llbg.getBackground().setAlpha(150);
		holder.btndownload.getBackground().setAlpha(150);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final Holder holder;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_itemlist, null);
			holder = new Holder();
			intialize(convertView, holder);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
//		String imageURL = "http://i1.sndcdn.com/avatars-000166470535-uoye4q-original.jpg";
		String imageURL = list.get(position).getThumbnail();
		ImageLoader.getInstance().displayImage(imageURL, holder.ivthumbnail, DisplayImageOption.getDisplayRoundedImage(), new AnimateFirstDisplayListener());
		
		String title = list.get(position).getTitle();
		holder.tvtitle.setText(AppMethod.getString(title, AppConstant.DASH));
		
		String description = list.get(position).getTitle();
		holder.tvdescription.setText(AppMethod.getString(description, AppConstant.DASH));
		
		OnClickListener onclick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				
				case R.id.llview:
					break;
					
				default:
					break;
				}
			}
		};
		
		holder.llview.setOnClickListener(onclick);

		return convertView;
	}

}