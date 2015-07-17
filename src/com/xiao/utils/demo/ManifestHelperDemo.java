package com.xiao.utils.demo;

import com.xiao.utils.LogHelper;
import com.xiao.utils.ManifestHelper;

import android.content.Context;

public class ManifestHelperDemo {
	public static String TAG="ManifestHelperDemo";
	
	/**
	 * 获取Manifest文件中name为metaTest的meta-data的值
	 * @param context
	 */
	public static void getMetaDataTest(Context context){
		String resultString=ManifestHelper.getMetaData(context, "metaTest");
		
		LogHelper.d(TAG,resultString);
	}
}
