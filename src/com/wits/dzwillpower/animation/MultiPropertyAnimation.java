package com.wits.dzwillpower.animation;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class MultiPropertyAnimation extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public class  MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener{

		public MyAnimationView(Context context) {
			super(context);
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			
		}
	}
}
