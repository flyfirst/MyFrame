package com.xiao.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class StreamUtils {
	
	/**
	 * 将输入流转换成String字符
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String stream2String(InputStream in) throws IOException{
		
		//使用ByteArrayOutputStream类的原因ByteArrayOutputStream里面自定义了不断扩大的byte数组
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
			
		byte[] buf=new byte[1024*8];
		int length=0;
		
		while((length=in.read(buf))!=-1){
			outputStream.write(buf,0,length);
		}
		
		return outputStream.toString();
	}
	
	
}
