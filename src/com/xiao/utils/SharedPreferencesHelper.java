package com.xiao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 以单例的方式维持一个SharePreferences对象，用于简化与SharePreference相关的操作
 * @author xiao
 *
 */
public class SharedPreferencesHelper {
	private static SharedPreferencesHelper spHelper;
	
	private SharedPreferences sp;
	private Editor editor;

	private SharedPreferencesHelper(Context context,String fileName) {
		sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static SharedPreferencesHelper getInstance(Context context,String fileName) {
		if (spHelper == null) {
			synchronized (SharedPreferences.class) {
				if (spHelper == null) {
					spHelper = new SharedPreferencesHelper(context,fileName);
				}
			}
		}

		return spHelper;
	}
	
	public void saveData(String key,String value){
		editor.putString(key, value);
		editor.commit();
	}
	
	public void saveData(String key, boolean value){
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public void saveData(String key,float value){
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public void saveData(String key,int value){
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void saveData(String key,long value){
		editor.putLong(key, value);
		editor.commit();
	}
	
	public String getString(String key){
		return sp.getString(key, "");
	}
	
	public String getData(String key, String defValue){
		return sp.getString(key, defValue);
	}
	
	public boolean getBoolean(String key){
		return sp.getBoolean(key, false);
	}
	
	public boolean getData(String key, boolean defValue){
		return sp.getBoolean(key, defValue);
	}
	
	public float getFloat(String key){
		return sp.getFloat(key, -1);
	}
	
	public float getData(String key, float defValue){
		return sp.getFloat(key, defValue);
	}
	
	public int getInt(String key){
		return sp.getInt(key, -1);
	}
	
	public int getData(String key, int defValue){
		return sp.getInt(key, defValue);
	}
	
	public long getLong(String key){
		return sp.getLong(key, -1);
	}
	
	public long getData(String key, long defValue){
		return sp.getLong(key, defValue);
	}
	
	/**
	 * 移除一个字段
	 * @param key
	 */
	public void removeItem(String key){
		removeItems(new String[]{key});
	}
	
	/**
	 * 移除多个字段
	 * @param keys
	 */
	public void removeItems(String[] keys){
		for (String key : keys) {
			editor.remove(key);
		}
		editor.commit();
	}
	
	public void clean(){
		editor.clear();
		editor.commit();
	}
}
