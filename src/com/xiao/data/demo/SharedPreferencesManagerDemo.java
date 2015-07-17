package com.xiao.data.demo;

import android.content.Context;

import com.xiao.data.SharedPreferencesManager;
import com.xiao.utils.LogHelper;

public class SharedPreferencesManagerDemo {
	public static String TAG="SharedPreferencesManagerDemo";
	
	 private SharedPreferencesManager spm;
	 
	 public SharedPreferencesManagerDemo(Context context){
		 spm=spm.getInstance(context);
	 }
	 
	 public void putStringTest(){
		 spm.saveData("stringTest", "测试数据");
	 }
	 
	 public void getString(){
		 LogHelper.d(TAG+".getString() ", spm.getString("stringTest1"));
		 LogHelper.d(TAG+".getString() ", spm.getString("stringTest"));
		 
	 }
}
