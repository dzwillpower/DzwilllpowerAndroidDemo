package com.wits.dzwillpower.ui;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.util.GaussianBlur;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;


public class GaussianBlurExample extends Activity {
    private Bitmap mBitmap;
    private ImageView mImgBlur;
    private Bitmap mDecreaseBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaussianblurexample_xml);
        mImgBlur = (ImageView)findViewById(R.id.img_blur);
        GaussianBlur gaussian = new GaussianBlur(this);
        gaussian.setMaxImageSize(100);
        gaussian.setRadius(25); //max
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaussianblur30);
        mDecreaseBitmap = SetBrightness(mBitmap,-100);
        Bitmap output = gaussian.render(mDecreaseBitmap,true);
        Drawable d = new BitmapDrawable(getResources(),output);
//        mImgBlur.setImageBitmap(output);
        mImgBlur.setBackground(d);
    }

    public Bitmap SetBrightness(Bitmap src, int value) {
        // original image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

}
