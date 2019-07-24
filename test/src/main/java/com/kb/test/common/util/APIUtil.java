package com.kb.test.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
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
			
			// Kakao API와 변수 Json 변수 맞춰주기
			JSONObject convertJson = convertKakaoJson(new JSONObject(returnText));
			//response 결과값을 json으로 변환한다.
			resultJson.put("data", convertJson);
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
	
	public static JSONObject convertKakaoJson(JSONObject naverJson) {
		JSONObject convertJson = new JSONObject();
		
		JSONArray itemsJson = naverJson.getJSONArray("items");
		
		String strItemsJson = itemsJson.toString();
		
		strItemsJson = strItemsJson.replace("\"link\":","\"translators\": [],\"url\":");
		strItemsJson = strItemsJson.replace("\"image\":","\"status\": \"정상판매\",\"thumbnail\":");
		strItemsJson = strItemsJson.replace("\"author\":","\"authors\":");
		strItemsJson = strItemsJson.replace("\"discount\":","\"sale_price\":");
		strItemsJson = strItemsJson.replace("\"pubdate\":","\"datetime\":");
		strItemsJson = strItemsJson.replace("\"description\":","\"contents\":");
		// 안바꿔도 되는 친구들
		//strItemsJson.replace("\"title\":","\"title\":");
		//strItemsJson.replace("\"price\":","\"price\":");
		//strItemsJson.replace("\"publisher\":","\"publisher\":");
		//strItemsJson.replace("\"isbn\":","\"isbn\":");
		
		// meta
		JSONObject metaJson = new JSONObject();
		metaJson.put("is_end", false);
		metaJson.put("pageable_count", naverJson.get("total"));
		metaJson.put("pageable_count", naverJson.get("total"));
		
		// documents
		JSONArray documentsJson = new JSONArray(strItemsJson);
		
		for(int i=0; i<documentsJson.length(); i++) {
			JSONObject tempJson = (JSONObject)documentsJson.get(i);
			String strDate = (String)tempJson.get("datetime");
			
			SimpleDateFormat naverDateFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat kakaoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Date datetime = naverDateFormat.parse(strDate);
				tempJson.put("datetime", kakaoDateFormat.format(datetime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		convertJson.put("documents", documentsJson);
		convertJson.put("meta", metaJson);
		
		return convertJson;
	}
}
