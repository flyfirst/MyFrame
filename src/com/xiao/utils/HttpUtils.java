package com.xiao.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 进行网络访问操作
 * @author xiao
 *
 */
public class HttpUtils {

	/**
	 * 使用apache的库，进行post请求
	 * @param strUrlPath 需要请求的url地址
	 * @param params 需要放在请求的方法体中的参数
	 * @param encode 提交的编码格式
	 * @return 获取返回值
	 */
	public static String postRequest(String strUrlPath,Map<String, String> params, String encode){
		String result=null;
		
		// 第一步，创建HttpPost对象 
        HttpPost httpPost = new HttpPost(strUrlPath); 
 
		// 设置HTTP POST请求参数必须用NameValuePair对象
		List<NameValuePair> paramlist = new ArrayList<NameValuePair>();

        for(Map.Entry<String, String> entry : params.entrySet()) {
         paramlist.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        
        HttpResponse httpResponse = null; 
        try { 
            // 设置httpPost请求参数 
            httpPost.setEntity(new UrlEncodedFormEntity(paramlist, HTTP.UTF_8)); 
            httpResponse = new DefaultHttpClient().execute(httpPost); 
            //System.out.println(httpResponse.getStatusLine().getStatusCode()); 
            if (httpResponse.getStatusLine().getStatusCode() == 200) { 
                // 第三步，使用getEntity方法活得返回结果 
                result = EntityUtils.toString(httpResponse.getEntity()); 
            } 
        } catch (ClientProtocolException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        
        return result;
	}

	/*
	 * Function : 发送Post请求到服务器 Param : params请求体内容，encode编码格式
	 */
	public static String submitPostData(String strUrlPath,
			Map<String, String> params, String encode) {

		byte[] data = getRequestData(params, encode).toString().getBytes();// 获得请求体
		try {

			// String urlPath = "http://192.168.1.9:80/JJKSms/RecSms.php";
			URL url = new URL(strUrlPath);

			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setConnectTimeout(3000); // 设置连接超时时间
			httpURLConnection.setDoInput(true); // 打开输入流，以便从服务器获取数据
			httpURLConnection.setDoOutput(true); // 打开输出流，以便向服务器提交数据
			httpURLConnection.setRequestMethod("POST"); // 设置以Post方式提交数据
			httpURLConnection.setUseCaches(false); // 使用Post方式不能使用缓存
			// 设置请求体的类型是文本类型
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 设置请求体的长度
			httpURLConnection.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			// 获得输出流，向服务器写入数据
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(data);

			int response = httpURLConnection.getResponseCode(); // 获得服务器的响应码
			if (response == HttpURLConnection.HTTP_OK) {
				InputStream inptStream = httpURLConnection.getInputStream();
				return dealResponseResult(inptStream); // 处理服务器的响应结果
			}
		} catch (IOException e) {
			// e.printStackTrace();
			return "err: " + e.getMessage().toString();
		}
		return "-1";
	}

	/*
	 * Function : 封装请求体信息 Param : params请求体内容，encode编码格式
	 */
	public static StringBuffer getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // 存储封装好的请求体信息
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/*
	 * Function : 处理服务器的响应结果（将输入流转化成字符串） Param : inputStream服务器的响应输入流
	 */
	public static String dealResponseResult(InputStream inputStream) {
		String resultData = null; // 存储处理结果
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultData = new String(byteArrayOutputStream.toByteArray());
		return resultData;
	}

}
