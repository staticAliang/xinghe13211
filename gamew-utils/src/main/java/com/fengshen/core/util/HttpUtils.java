package com.fengshen.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * http请求工具类
 * @sine: 1.2.0
 *
 */
@Slf4j
public class HttpUtils {

	public static String doGet(String url) {
		return get(url);
	}
	
	public static String doGet(String url, Map<String, Object> params) {
		List<NameValuePair> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, Object> m:params.entrySet()) {
			list.add(new BasicNameValuePair(m.getKey(), String.valueOf(m.getValue())));
		}
		try {
			String string = EntityUtils.toString(new UrlEncodedFormEntity(list,"utf-8"));
			sb.append(url);
			sb.append("?");
			sb.append(string);
		} catch (Exception e) {
			log.error("GET请求异常");
			log.error(e.getMessage());
		}
		return get(sb.toString());
	}
	
	
	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建参数队列
		String content = null;
		try {
			// 参数转换为字符串
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 返回内容
				content = EntityUtils.toString(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			log.error("GET请求异常");
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error("GET请求异常");
			}
		}
		return content;
	}
	
	public static String doPost(String url, Map<String,Object> params) {
		CloseableHttpClient http = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<>();
		for(Map.Entry<String, Object> m:params.entrySet()) {
			list.add(new BasicNameValuePair(m.getKey(), String.valueOf(m.getValue())));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
			CloseableHttpResponse response = http.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			EntityUtils.toString(httpEntity);
			response.close();
		} catch (UnsupportedEncodingException e) {
			log.error("POST请求异常");
			return "error";
		}catch (ClientProtocolException e) {
			log.error("POST请求异常");
			return "error";
		} catch (IOException e) {
			log.error("POST请求异常");
			return "error";
		}finally {
			try {
				http.close();
			} catch (IOException e) {
				log.error("POST请求异常");
				return "error";
			}
		}
		return "ok";
	}
	
}
