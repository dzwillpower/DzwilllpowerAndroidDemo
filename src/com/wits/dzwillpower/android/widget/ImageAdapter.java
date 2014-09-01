package com.wits.dzwillpower.android.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ipanel.android.net.imgcache.SharedImageFetcher;

import com.wits.dzwillpower.android.R;

public class ImageAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater = null;
	private List<ImageBean> images;
	private Context context;

	public ImageAdapter(Context context, List<ImageBean> objects) {
		super();
		layoutInflater = LayoutInflater.from(context);
		images = objects;
		this.context = context;

	}

	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.listviewsample_main, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.img_name);
			holder.url = (TextView) convertView.findViewById(R.id.img_url);
			holder.picture = (ImageView) convertView.findViewById(R.id.img_picture);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(images.get(position).getImgname());
		holder.url.setText(images.get(position).getImgurl());
		SharedImageFetcher.getSharedFetcher(context).loadImage(images.get(position).getImgurl(), holder.picture);
		return convertView;
	}

	private static class ViewHolder {
		private TextView name;
		private TextView url;
		private ImageView picture;
	}

}
