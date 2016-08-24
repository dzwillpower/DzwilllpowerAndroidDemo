package com.wits.dzwillpower.ui;

import com.wits.dzwillpower.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class TestActivity extends Activity {
    private Button mButton;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mTextView = (TextView)findViewById(R.id.tv);
        mButton = (Button)findViewById(R.id.btn_click);
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, SecondActivity.class));
                startActivity(new Intent(TestActivity.this, SecondActivity.class));
                startActivity(new Intent(TestActivity.this, SecondActivity.class));

            }
        });
        mTextView.setText(this+"\ntaskid: "+getTaskId());
    }

}
