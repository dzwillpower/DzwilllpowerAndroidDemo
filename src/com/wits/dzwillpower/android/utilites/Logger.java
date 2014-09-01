package com.wits.dzwillpower.android.utilites;

import android.util.Log;

import com.wits.dzwillpower.android.BuildConfig;

public class Logger
{
    private static boolean DEBUG = BuildConfig.DEBUG;

    public static String  TAG   = "iPanel";

    public static void d(String msg)
    {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void d(String msg, Throwable t)
    {
        if (DEBUG)
            Log.d(TAG, msg, t);
    }

    public static void e(String msg)
    {
        if (DEBUG)
            Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable t)
    {
        if (DEBUG)
            Log.e(TAG, msg, t);
    }

    public static void printMemory(String msg)
    {
        d(msg);
        d("maxMemory: " + Runtime.getRuntime().maxMemory()/1024 + "KB");
        d("totalMemory: " + Runtime.getRuntime().totalMemory()/1024 + "KB");
        d("freeMemory: " + Runtime.getRuntime().freeMemory()/1024 + "KB");
        d("nativeHeapSize: " + android.os.Debug.getNativeHeapSize()/1024 + "KB");
        d("nativeHeapAllocatedSize: " + android.os.Debug.getNativeHeapAllocatedSize()/1024 + "KB");
        d("nativeHeapFreeSize: " + android.os.Debug.getNativeHeapFreeSize()/1024 + "KB");
    }
}
