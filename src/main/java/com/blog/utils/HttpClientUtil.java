package com.blog.utils;
 
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 类: HttpClient <br>
 * 描述: httpclient工具类 <br>
 * 作者: song<br>
 * 时间: 2017年7月21日 下午3:15:27
 */
public class HttpClientUtil {
	
	static CloseableHttpClient client = null;
	static {
		//1、创建httpClient实例
		client = HttpClients.createDefault();
	}
	
	/**
	 * 
	 * 方法: get <br>
	 * 描述: get请求 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年7月21日 下午3:15:25
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String get(String url,HashMap<String, Object> params){
		try {
			//2、创建get对象
			HttpGet httpGet = new HttpGet();
			
			//3、拼接完成的get请求路径
			Set<String> keySet = params.keySet();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(url).append("?t=").append(System.currentTimeMillis());
			for (String key : keySet) {
				stringBuffer.append("&").append(key).append("=").append(params.get(key));
			}
			
			httpGet.setURI(new URI(stringBuffer.toString()));
			//4、执行execute方法，发送请求
			CloseableHttpResponse execute = client.execute(httpGet);
			//5、获取请求的状态码
			int statusCode = execute.getStatusLine().getStatusCode();
			//判断请求是否成功
			if (200 != statusCode) {
				return "";
			}
			return EntityUtils.toString(execute.getEntity(), "utf-8");
		}catch (Exception e) {
			//6、关闭连接
			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * 方法: post <br>
	 * 描述: post请求 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年7月21日 下午3:20:31
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url,HashMap<String, Object> params) {
		try {
			HttpPost httpPost = new HttpPost();
			httpPost.setURI(new URI(url));
			
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				NameValuePair e = new BasicNameValuePair(key, params.get(key).toString());
				parameters.add(e);
			}
			
			HttpEntity entity = new UrlEncodedFormEntity(parameters , "utf-8");
			httpPost.setEntity(entity);
			
			CloseableHttpResponse execute = client.execute(httpPost);
			
			int statusCode = execute.getStatusLine().getStatusCode();
			if (200 != statusCode) {
				return "";
			}
			return EntityUtils.toString(execute.getEntity(), "utf-8");
		}catch (Exception e) {
			//6、关闭连接
			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			e.printStackTrace();
			return null;
		}
	}
	
	//传字符串
		public static String post2(String url,String params) {
			try {
				HttpPost httpPost = new HttpPost();
				httpPost.setURI(new URI(url));
				httpPost.setHeader("Content-Type", "application/json");
				
				StringEntity stringEntity = new StringEntity(params,"utf-8");
				httpPost.setEntity(stringEntity);
				
				CloseableHttpResponse execute = client.execute(httpPost);
				
				int statusCode = execute.getStatusLine().getStatusCode();
				if (200 != statusCode) {
					return "";
				}
				return EntityUtils.toString(execute.getEntity(), "utf-8");
			}catch (Exception e) {
				//6、关闭连接
				try {
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				e.printStackTrace();
				return null;
			}
		}
		
		//处理短信调用：header需要传参
		public static String post3(String url,HashMap<String, Object> params,HashMap<String, Object> heanderParams) {
			try {
				HttpPost httpPost = new HttpPost();
				httpPost.setURI(new URI(url));
				
				Set<String> headerkey = heanderParams.keySet();
				for (String key : headerkey) {
					httpPost.addHeader(key,heanderParams.get(key).toString());
				}
				
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				Set<String> keySet = params.keySet();
				for (String key : keySet) {
					NameValuePair e = new BasicNameValuePair(key, params.get(key).toString());
					parameters.add(e);
				}
				
				HttpEntity entity = new UrlEncodedFormEntity(parameters , "utf-8");
				httpPost.setEntity(entity);
				
				CloseableHttpResponse execute = client.execute(httpPost);
				
				int statusCode = execute.getStatusLine().getStatusCode();
				if (200 != statusCode) {
					return "";
				}
				return EntityUtils.toString(execute.getEntity(), "utf-8");
			}catch (Exception e) {
				//6、关闭连接
				try {
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				e.printStackTrace();
				return null;
			}
		}
	
	

		//天气接口
		/*public static void main(String[] args) {
			String url =  "http://t.weather.sojson.com/api/weather/city/101110101";
			HashMap<String, Object> params = new HashMap<String, Object>();
			String string = get(url, params);
			//System.out.println(string);
			JSONObject jsonObj = JSON.parseObject(string);
			JSONObject data = jsonObj.getJSONObject("data");
			JSONObject yesterday = data.getJSONObject("yesterday");
			String high = yesterday.getString("high");
			System.out.println(high);
			String low = yesterday.getString("low");
			System.out.println(low);

			JSONArray forecast = data.getJSONArray("forecast");
			JSONObject tom = forecast.getJSONObject(1);
			int intValue = tom.getIntValue("aqi");
			System.out.println(intValue);
			
		}

		//短信接口
		public static void main(String[] args) {
			String url = "https://api.netease.im/sms/sendcode.action";
			//header参数
			HashMap<String, Object> header = new HashMap<String, Object>();
			header.put("AppKey", CommonConf.APP_KEY);//开发者平台分配的appkey
			String nonce = UUID.randomUUID().toString();
			header.put("Nonce", nonce);//随机数（最大长度128个字符）
			String curTime = String.valueOf(System.currentTimeMillis()/1000);
			header.put("CurTime", curTime);//当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
			//SHA1(AppSecret + Nonce + CurTime)，三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)
			String appSecret = CommonConf.APP_SECRET;
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
			header.put("CheckSum",checkSum);
			
			//body的参数
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", "18210845283");//手机号
			params.put("templateid", 14841053);//模板编号(如不指定则使用配置的默认模版)
			//验证码 999999 100000
			long code = Math.round((Math.random()*899999+100000));
			params.put("authCode", code);//客户自定义验证码，长度为4～10个数字
			
			String str = post3(url, params,header);
			System.out.println(str);
		}
	
	*/
}
