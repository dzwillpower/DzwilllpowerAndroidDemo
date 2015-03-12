package com.wits.dzwillpower.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.widget.StartPointSeekBar;
import com.wits.dzwillpower.android.widget.StartPointSeekBar.OnSeekBarChangeListener;


public class SeekBarTest extends Activity {
    private SeekBar mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_progress);
//        mSeekBar = (SeekBar)findViewById(R.id.seek_bar);
//        mSeekBar.setMax(1000);
//        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//              Log.d("SeekBarTest", "progress: "+progress);
//
//            }
//        });



        StartPointSeekBar seekBar = new StartPointSeekBar(0, +100, this);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener<Integer>()
        {
            @Override
            public void onOnSeekBarValueChange(StartPointSeekBar<?> bar, Integer value)
            {
                Log.d("SeekBarTest", "seekbar value:" + value);
            }
        });

        // add RangeSeekBar to pre-defined layout
        ViewGroup layout = (ViewGroup) findViewById(R.id.seekbarwrapper);
        layout.addView(seekBar);
    }

}
