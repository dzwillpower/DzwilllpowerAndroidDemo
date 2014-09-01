package com.wits.dzwillpower.android.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.wits.dzwillpower.android.R;

public class SampleView extends View {
	// CONSTRUCTOR
	public SampleView(Context context) {
		super(context);
		setFocusable(true);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();

		canvas.drawColor(Color.YELLOW);

		paint.setFilterBitmap(true);
		Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.defaultimage);
		int targetWidth = bitmapOrg.getWidth() * 2;
		int targetHeight = bitmapOrg.getHeight() * 2;
		Bitmap bmp = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
		// RectF rectf = new RectF(0, 0, targetWidth, targetHeight);
		Canvas c = new Canvas(bmp);
		// Path path = new Path();
		// path.addRect(rectf, Path.Direction.CW);
		// c.clipPath(path);
		c.drawBitmap(bitmapOrg, new Rect(0, 0, bitmapOrg.getWidth(), bitmapOrg.getHeight()), new Rect(0, 0,
				targetWidth, targetHeight), paint);
		Matrix matrix = new Matrix();
		matrix.postScale(1f, -1f);
		Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, targetWidth, targetHeight, matrix, true);
		int h = bitmapOrg.getHeight();
		canvas.drawBitmap(bitmapOrg, 10, 10, paint);
		canvas.drawBitmap(resizedBitmap, 10, 10 + h + 10, paint);

	}

}
