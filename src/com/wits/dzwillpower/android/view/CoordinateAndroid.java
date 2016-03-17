
package com.wits.dzwillpower.android.view;

import java.lang.reflect.Method;

import com.wits.dzwillpower.android.utilites.MyLog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.nfc.Tag;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 *
 * @author 80083204
 *
 *         2016年1月8日
 */
public class CoordinateAndroid extends Activity {
    private static final String TAG = CoordinateAndroid.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.d(TAG,"onResume");
        // View布局区域宽高等尺寸获取
        Rect windownRect = new Rect();
        getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(windownRect);

    }

    private void getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        MyLog.d(TAG,"widthPixels: " + widthPixels + " heightPixels: " + heightPixels);
    }

    // 获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int width = 0;
        int height = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            width = displayMetrics.heightPixels;
            height = displayMetrics.widthPixels;
            MyLog.d(TAG,"width: " + width + " height: " + height);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return width;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);
        int contentHeight = getScreenHeight(context);
        MyLog.d(TAG,"virtualkeyheight: " + (totalHeight - contentHeight));
        return totalHeight - contentHeight;
    }

    /**
     * 标题栏高度
     *
     * @return
     */
    public static int getTitleHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        MyLog.d(TAG,"scrrenheight: " + outMetrics.heightPixels);
        return outMetrics.heightPixels;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        MyLog.d(TAG,"onWindowFocusChanged");
        getDpi(this);
        // 获取屏幕区域的宽高等尺寸获取
        getScreenWidth();
        //获取虚拟按键的高度
        getBottomStatusHeight(this);
        //这个标题栏的高度是包含了 状态栏的高度
        MyLog.d(TAG,"titlebarheight: "+getTitleHeight(this));

        Rect rect= new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        MyLog.d(TAG,"statusBarHeight: "+statusBarHeight);
    }

}
