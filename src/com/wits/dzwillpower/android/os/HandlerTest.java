package com.wits.dzwillpower.android.os;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.wits.dzwillpower.android.R;
/**
 * 
 * @author dzwillpower
 * @2013-5-18下午3:16:33
 * http://www.blogjava.net/amplifier/archive/2012/02/09/369691.html 
 */
public class HandlerTest extends Activity {
	Handler handler = new Handler();
    /** Called when the activity is first created. */
	private TextView tvTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("1");
        handler.post(r);
        System.out.println("2");
        setContentView(R.layout.handlertest_main);
        tvTextView = (TextView)findViewById(R.id.tv_test);
        System.out.println(tvTextView.getText().toString());
        System.out.println("activity---->"+Thread.currentThread().getId());
        System.out.println("activity name--->"+Thread.currentThread().getName());
    }
    
    Runnable r = new Runnable() {
        
        @Override
        public void run() {
            // TODO Auto-generated method stub
            System.out.println("handler---->"+Thread.currentThread().getId());
            System.out.println("handlername---->"+Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("3");
        }
    };
}
