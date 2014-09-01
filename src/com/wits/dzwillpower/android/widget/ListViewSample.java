package com.wits.dzwillpower.android.widget;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.wits.dzwillpower.android.R;

public class ListViewSample extends ListActivity {
	private ArrayList<ImageBean> images = new ArrayList<ImageBean>();
	private ImageAdapter imageAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initData();
		imageAdapter = new ImageAdapter(ListViewSample.this, images);
		setListAdapter(imageAdapter);
		registerForContextMenu(getListView());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ImageBean image = images.get(position);
		Toast.makeText(ListViewSample.this, image.getImgname() + ":" + image.getImgurl(), Toast.LENGTH_LONG).show();
		return;
	}

	private void initData() {
		images.add(new ImageBean("圣彼得堡", "https://lh5.googleusercontent.com/-DRnqmK0t4VU/URqu8XYN9yI/AAAAAAAAAbs/LgvF_592WLU/s1024/Rice%252520Fields.jpg"));
		images.add(new ImageBean("黑头鹀", "https://lh6.googleusercontent.com/-lEQE4h6TePE/URqu6t_lSkI/AAAAAAAAAbs/zvGYKOea_qY/s1024/Over%252520there.jpg"));
		images.add(new ImageBean("莫斯克内斯岛", "https://lh4.googleusercontent.com/-e9NHZ5k5MSs/URqvMIBZjtI/AAAAAAAAAbs/1fV810rDNfQ/s1024/Yosemite%252520Tree.jpg"));
		images.add(new ImageBean("莫斯克内斯岛", "https://lh4.googleusercontent.com/-PDT167_xRdA/URqvK36mLcI/AAAAAAAAAbs/oi2ik9QseMI/s1024/Windmills.jpg"));
		images.add(new ImageBean("莫斯克内斯岛", "https://lh5.googleusercontent.com/-biyiyWcJ9MU/URqvKculiAI/AAAAAAAAAbs/jyPsCplJOpE/s1024/Windmill.jpg"));
		images.add(new ImageBean("莫斯克内斯岛", "https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s160-c/Antelope%252520Butte.jpg"));
		images.add(new ImageBean("莫斯克内斯岛", "https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s160-c/Antelope%252520Hallway.jpg"));
		

	}
}