package com.wits.dzwillpower.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.wits.dzwillpower.android.R;

public class ActivityLifeStyle extends Activity{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitylifestyle);
        // TODO Auto-generated method stub
        System.out.println("====================================ActivityLifeStyle--->onCreate");
        try {
        	System.out.println(new File(Environment.getExternalStorageDirectory(),  
                    "version").toString());
			//ReadVersion(new File(Environment.getExternalStorageDirectory(),  
                    //"version"));
                    ReadVersion("/mnt/sdcard/version");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("====================================ActivityLifeStyle--->onDestory");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        System.out.println("====================================ActivityLifeStyle--->onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        System.out.println("====================================ActivityLifeStyle--->onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        System.out.println("====================================ActivityLifeStyle--->onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        System.out.println("====================================ActivityLifeStyle--->onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        System.out.println("====================================ActivityLifeStyle--->onStop");
        super.onStop();
    }
    
    public void ReadVersion(String filename) throws Exception
    {
    	FileInputStream fis;
    	fis = new FileInputStream(filename);
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	byte[] buffer = new byte[1024];
    	int length;
    	while((length = fis.read(buffer)) != -1) {
    		bos.write(buffer, 0, length);
    		fis.close();
    		bos.close();
    		System.out.println(new String(bos.toByteArray()));
    		System.out.println(new String(bos.toByteArray()).length());
    	}

}
}
	


