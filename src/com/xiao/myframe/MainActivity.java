package com.xiao.myframe;

import android.app.Activity;
import android.os.Bundle;

import com.xiao.data.demo.SharedPreferencesManagerDemo;
import com.xiao.utils.demo.ManifestHelperDemo;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferencesManagerDemo();
	}
	
	public void manifestTest(){
		ManifestHelperDemo.getMetaDataTest(MainActivity.this);
	}
	
	public void SharedPreferencesManagerDemo(){
		SharedPreferencesManagerDemo spmd=new SharedPreferencesManagerDemo(MainActivity.this);
		spmd.putStringTest();
		spmd.getString();
	}
}
