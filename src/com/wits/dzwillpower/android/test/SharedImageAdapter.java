package com.wits.dzwillpower.android.test;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.ipanel.android.net.imgcache.SharedImageFetcher;

import com.wits.dzwillpower.android.R;

public class SharedImageAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater = null;
	private List<String> listImgUrls;
	private Context context;

	public SharedImageAdapter(Context context, List<String> objects) {
		super();
		layoutInflater = LayoutInflater.from(context);
		listImgUrls = objects;
		this.context = context;

	}

	@Override
	public int getCount() {
		return listImgUrls.size();
	}

	@Override
	public Object getItem(int position) {
		return listImgUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.shareddimg_item, null);
			holder = new ViewHolder();
			holder.picture = (ImageView) convertView.findViewById(R.id.img_picture);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		SharedImageFetcher.getSharedFetcher(context).loadImage(listImgUrls.get(position), holder.picture);
		//如果是sdcard 文件
//		SharedImageFetcher.getSharedFetcher(context).loadImage("file://"+listImgUrls.get(position),holder.picture);
		// 如果是assets 中的文件
		SharedImageFetcher.getSharedFetcher(context).loadImage("assets://"+listImgUrls.get(position),holder.picture);
		return convertView;
	}

	private static class ViewHolder {
		private ImageView picture;
	}
}
