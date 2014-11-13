package com.wits.dzwillpower.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.wits.dzwillpower.android.R;


public class MultiThreadWriteImg extends Activity {

    private Bitmap bitmap;
    private Bitmap bitmap2;
    Handler handler;
    private ImageView imgImageView;
    private String path;
    private Object object = new Object();
    private Object object2 = new Object();
    private boolean bool = false;
    private InputStream inputStream;
    private InputStream inputStream2;
    private static final String TAG ="dz_will";

    private class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 0:
                Bitmap bitmap = (Bitmap) msg.obj;
                Log.d(TAG, "display bitmap: " + bitmap.getByteCount());
                imgImageView.setImageBitmap(bitmap);
                break;

            default:
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        path = Environment.getExternalStorageDirectory().getPath();
        Log.d(TAG, "path: " + path);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.james);
        bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fengjing);
        inputStream = getResources().openRawResource(R.drawable.james);
        inputStream2 = getResources().openRawResource(R.drawable.fengjing);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, null);
        Log.d(TAG, "bitmap: "+bitmap);
        imgImageView = (ImageView) findViewById(R.id.img);
        imgImageView.setImageBitmap(bitmap);
        handler = new MainHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                synchronized (object2) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    long startTime2 = System.currentTimeMillis();
//                    Log.d(TAG, "read bitmap");
//                    Bitmap bitmap = getBitmapByDex(MainActivity.this, path
//                            + "/com.dzwillpower.writeimageview/.baidu_cache/55555555.jpg");
//                    Message message = handler.obtainMessage();
//                    message.what = 0;
//                    message.obj = bitmap;
//                    handler.sendMessage(message);
//                    Log.d(TAG, "read bitmap end " + (System.currentTimeMillis() - startTime2));
//                }
//
//            }
//        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateThumbToCard(MultiThreadWriteImg.this, path
                        + "/com.dzwillpower.writeimageview/.baidu_cache/55555555.jpg", bitmap2);
            }
        }).start();
    }

    private void updateThumbToCard(Context context, final String path, final Bitmap bm) {
        Bitmap copyBitmap = null;
        try {
//            synchronized (object) {
                if (!ensureFileExists(path)) {
                    throw new IllegalStateException("Unable to create new file: " + path);
                } else {
                    long startTime = System.currentTimeMillis();
                    Log.d(TAG, "start write image");
                    File file = new File(path);
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(file, false);
                    copyBitmap = bm.copy(Config.RGB_565, true);
                    boolean bool = copyBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    if (fos != null) {
                        fos.close();
                    }
                    Log.d(TAG, "start write image end: "
                            + (System.currentTimeMillis() - startTime));
                }
//            }

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (copyBitmap != null && !copyBitmap.isRecycled()) {
                copyBitmap.recycle();
            }
        }
    }

        private void updateThumbToCard(Context context, final String path, final InputStream is) {
            try {
//                synchronized (object) {
                    if (!ensureFileExists(path)) {
                        throw new IllegalStateException("Unable to create new file: " + path);
                    } else {
                        long startTime = System.currentTimeMillis();
                        Log.d(TAG, "start write image");
                        File file = new File(path);
                        OutputStream os = null;
                        os = new FileOutputStream(file);
                        byte buffer[] = new byte[1024];
                        int len = 0;
                        while((len = is.read(buffer)) != -1){
                            os.write(buffer, 0, len);
                        }
                        os.flush();
                        os.close();
                        Log.d(TAG, "start write image end: "
                                + (System.currentTimeMillis() - startTime));
                    }
//                }

            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                
            }
        }

        private static Bitmap getBitmapByDex(Context context, String path) {

            Bitmap bm = null;
            FileInputStream fis = null;
            try {

                File file = new File(path);
                if (file != null && file.exists()) {
                    fis = new FileInputStream(file);
                    bm = BitmapFactory.decodeStream(fis);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {}
            }
            return bm;
        }

        public boolean ensureFileExists(String path) {
            File file = new File(path);
            if (file.exists()) {
                //            file.delete();
                return true;
            }
            int secondSlash = path.indexOf('/', 1);
            if (secondSlash < 1) {
                return false;
            }
            String directoryPath = path.substring(0, secondSlash);
            Log.d(TAG, "directoryPath: " + directoryPath);
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                return false;
            }
            file.getParentFile().mkdirs();
            try {
                return file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return false;
        }

}

