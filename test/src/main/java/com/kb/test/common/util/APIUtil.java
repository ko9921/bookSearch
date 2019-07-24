package com.kb.test.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class APIUtil {
	
	// Kakao
	public static JSONObject getKakaoRestApi(String strUrl, String strApiKey) {
		
		JSONObject resultJson = new JSONObject();
		
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = null;
		String responseData = "";
		String returnText = "";
		
		try {
			url = new URL(strUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "KakaoAK " + strApiKey);
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			int HttpResult = conn.getResponseCode();
			if(HttpResult == HttpURLConnection.HTTP_OK) {
				// 정상 호출
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				sb = new StringBuffer();
				
				while ((responseData = br.readLine()) != null) {
					sb.append(responseData);
				}
				returnText = sb.toString();
			} else {  // 에러 발생시
				
				resultJson.put("result", "FAIL");
				return resultJson;
				
			}
			
			//response 결과값을 json으로 변환한다.
			resultJson.put("data", new JSONObject(returnText));
			resultJson.put("result", "SUCCESS");
			resultJson.put("apiType", "kakao");
			
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("result", "FAIL");
			return resultJson;
		} finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return resultJson;
	}
	
	//Naver
	public static JSONObject getNaverRestApi(String strUrl, String strClientID, String strClientSecret) {
		
		JSONObject resultJson = new JSONObject();
		
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = null;
		String responseData = "";
		String returnText = "";
		
		try {
			url = new URL(strUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-Naver-Client-Id", strClientID);
			conn.setRequestProperty("X-Naver-Client-Secret", strClientSecret);
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			int HttpResult = conn.getResponseCode();
			if(HttpResult == HttpURLConnection.HTTP_OK) {
				// 정상 호출
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				sb = new StringBuffer();
				
				while ((responseData = br.readLine()) != null) {
					sb.append(responseData);
				}
				returnText = sb.toString();
			} else {  // 에러 발생시
				
				resultJson.put("result", "FAIL");
				return resultJson;
				
			}
			
			//response 결과값을 json으로 변환한다.
			resultJson.put("data", new JSONObject(returnText));
			resultJson.put("result", "SUCCESS");
			resultJson.put("apiType", "naver");
			
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("result", "FAIL");
			return resultJson;
		} finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return resultJson;
	}
}
