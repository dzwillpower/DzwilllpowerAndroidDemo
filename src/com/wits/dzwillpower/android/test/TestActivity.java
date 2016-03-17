
package com.wits.dzwillpower.android.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.customview.RotateTextImageView;
import com.wits.dzwillpower.android.utilites.MyLog;

public class TestActivity extends Activity {
    private static final String TAG = TestActivity.class.getSimpleName();
    RelativeLayout relativeLayout;
    private TextView textView;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.testactivity_main);
    	relativeLayout = (RelativeLayout)findViewById(R.id.rl_testactivity);
    	relativeLayout.addView(new GameView(this));
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // setContentView(new SampleView(TestActivity.this));
        // setContentView(new DemoView(this));
        // setContentView(new DrawText(this));

        // RotateTextImageView rotateTextImageView = new RotateTextImageView(this);
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        // rotateTextImageView.setBitmap(bitmap);
        // rotateTextImageView.setText("测试图片");
        // rotateTextImageView.setShawHeight(50);
        // rotateTextImageView.setLayoutParams(new LayoutParams(300, 300));
        // setContentView(rotateTextImageView);

        setContentView(R.layout.testactivity_main);
        textView = (TextView) findViewById(R.id.film_name);
        textView.setText("测试跑马灯你为啥不跑呢？");
        textView.setSelected(true);

        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        MyLog.d(TAG,""+metrics.densityDpi);
        MyLog.d(TAG,""+metrics.density);



    }

    public float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}
