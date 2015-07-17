package com.xiao.data;

import android.content.Context;

import com.xiao.utils.SharedPreferencesHelper;

/**
 * 每个APP都应该有一个名叫AppSharedPreferencesData的文件，用于存储需要使用SharedPreferences持久化的一些东西
 * @author xiao
 */
public class SharedPreferencesManager {
	private static SharedPreferencesHelper spHelper;
	
	private static SharedPreferencesManager spManager;
	
	private SharedPreferencesManager(){}
	
	public static SharedPreferencesManager getInstance(Context context){
		if(spManager==null){
			synchronized (SharedPreferencesManager.class) {
				if(spManager==null){
					spManager=new SharedPreferencesManager();
				}
			}
		}
		
		spHelper=SharedPreferencesHelper.getInstance(context, "AppSharedPreferencesData");
		
		return spManager;
	}
	
	public void saveData(String key,String value){
		spHelper.saveData(key, value);
	}
	
	public void saveData(String key, boolean value){
		spHelper.saveData(key, value);
	}
	
	public void saveData(String key,float value){
		spHelper.saveData(key, value);
	}
	
	public void saveData(String key,int value){
		spHelper.saveData(key, value);
	}
	
	public void saveData(String key,long value){
		spHelper.saveData(key, value);
	}
	
	public String getString(String key){
		return spHelper.getData(key, "");
	}
	
	public String getData(String key, String defValue){
		return spHelper.getData(key, defValue);
	}
	
	public boolean getBoolean(String key){
		return spHelper.getData(key, false);
	}
	
	public boolean getData(String key, boolean defValue){
		return spHelper.getData(key, defValue);
	}
	
	public long getData(String key, long defValue){
		return spHelper.getData(key, defValue);
	}
	
	public float getFloat(String key){
		return spHelper.getFloat(key);
	}
	
	public float getData(String key, float defValue){
		return spHelper.getFloat(key);
	}
	
	public int getInt(String key){
		return spHelper.getInt(key);
	}
	
	public int getData(String key, int defValue){
		return spHelper.getInt(key);
	}
	
	public long getLong(String key){
		return spHelper.getLong(key);
	}
	
}
