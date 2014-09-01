package com.wits.dzwillpower.android.home;
import android.app.Activity;  
import android.content.Context;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.os.Bundle;  
import android.os.Handler;  
import android.view.View;  
  
public class TestHandler2 extends Activity {  
    private MyView myView;  
    private Handler mHandler;  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        myView = new MyView(this);  
        //创建一个Handler
        mHandler = new Handler();
        //调用Handler.post(Runnable r)方法
        mHandler.post(new Runnable(){  
            @Override  
            public void run() {  
                //直接调用View.invalidate()，更新组件
                myView.invalidate();  
                //延迟5毫秒后执行线程
                mHandler.postDelayed(this, 5);  
            }  
         });  
        setContentView(myView);  
    }  
      
    class MyView extends View{  
        private float x = 0f;  
        public MyView(Context context) {  
            super(context);  
              
    }  
        protected void onDraw(Canvas canvas) {  
            super.onDraw(canvas);  
            x+=1;  
            Paint mPaint = new Paint();  
            mPaint.setColor(Color.BLUE);  
            canvas.drawRect(x, 40, x+40, 80, mPaint);  
        }  
          
    }  
}  