package com.xiao.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * AndroidManifest帮助类
 * 	主要功能：
 * 		* 获取meta-data属性信息
 * @author Administrator
 *
 */
public class ManifestHelper {
	
	/**
	 * 根据meta-data的name属性获取meta-data的value值
	 * @param context
	 * @param metaDataName meta-data的name属性
	 * @return meta-data中的值
	 */
	public static String getMetaData(Context context,String metaDataName){
		String result=null;
		
		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo appInfo = pm.getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = appInfo.metaData;
			result = bundle.getString(metaDataName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
