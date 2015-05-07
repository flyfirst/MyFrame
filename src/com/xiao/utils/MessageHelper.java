package com.xiao.utils;

import java.util.List;

import android.app.PendingIntent;
import android.telephony.SmsManager;

/**
 * 短信操作帮助类
 * @author xiao
 *
 */
public class MessageHelper {

	/**
	 * 发送短信
	 * @param phoneNum 短信发送目标号码
	 * @param scAddress 需要发送的内容
	 * @param content
	 * @param sentIntent
	 * @param deliveryIntent
	 */
	public static void sendMessage(String phoneNum,  String scAddress, String content , PendingIntent sentIntent, PendingIntent deliveryIntent) {
		SmsManager smsManager = SmsManager.getDefault();
		if (content.length() > 70) {
			// 返回多条短信
			List<String> contents = smsManager.divideMessage(content);
			for (String sms : contents) {
				// 1.目标地址：电话号码 2.原地址：短信中心服号码3.短信内容4.意图
				smsManager.sendTextMessage(phoneNum, scAddress, sms, sentIntent, deliveryIntent);
			}
		} else {
			smsManager.sendTextMessage(phoneNum, scAddress, content, sentIntent, deliveryIntent);
		}
	}
	
	/**
	 * 发送短信
	 * @param phoneNum 短信发送目标号码
	 * @param content 需要发送的内容
	 */
	public static void sendMessage(String phoneNum,String content){
		sendMessage(phoneNum, null, content, null, null);
	}
}
