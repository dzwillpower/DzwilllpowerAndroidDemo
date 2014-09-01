package com.wits.dzwillpower.android.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.wits.dzwillpower.android.R;
/**
 * 测试图片的异步加载
 * @author dzwillpower
 * @time 2013年7月30日 下午3:19:40
 */
public class SharedImageFetcherExample extends Activity {
	private GridView gvSharedImage;
	private SharedImageAdapter sharedImageAdapter;
	private List<String> ListAssetsFiles;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListAssetsFiles = new ArrayList<String>();
		ListAssetsFiles.add("backgroundtest.jpg");
		setContentView(R.layout.sharedimagefetcherexample_activity);
		gvSharedImage = (GridView) findViewById(R.id.gv_imgfetcher);
//		sharedImageAdapter = new SharedImageAdapter(this, Arrays.asList(Constants.IMAGES));
		// 加载assets中的图片文件
		sharedImageAdapter  = new SharedImageAdapter(this, ListAssetsFiles);
		gvSharedImage.setAdapter(sharedImageAdapter);
	}

}
