package com.wits.dzwillpower.ui;

import com.wits.dzwillpower.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class SecondActivity extends Activity {
    private Button mButton;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        mButton = (Button)findViewById(R.id.btn_click);
        mTextView = (TextView)findViewById(R.id.tv);
        mTextView.setText(this+"\n taskid: "+getTaskId());

    }

}
