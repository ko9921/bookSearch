package com.kb.test.common.util;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonUtil {
	
	public static String getSafeString(Object obj) {
		
		String rtnStr = "";
		
		if(obj == null) {
			rtnStr = "";
		} else if(obj.toString().trim().equals("") || obj.toString().trim().equals("null") || obj.toString().trim().equals("NULL")) {
			rtnStr = "";
		} else {
			rtnStr = obj.toString();
		}
		
		return rtnStr;
		
	}
	
	public static String getPassword(String inputPass, String secretKey) {

		String password = secretKey + "?" + inputPass;
		
		return encryptSHA256(password);
	}
	
	public static String encryptSHA256(String value){
		try{
			byte[] plainText = value.getBytes("UTF-8");
			byte[] hashValue = null;

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			hashValue = md.digest(plainText);

			return toHexString(hashValue);
		}catch(Exception e){
			System.out.println("[encryptSHA256]Exception : " + e);	
		}

		return "";
	}
	
	public static String toHexString(byte[] letter){
		StringBuffer sbHex = new StringBuffer();
		for (int j = 0; j <letter.length; j++) {
			String hexValue = Integer.toHexString(letter[j] & 0xff); 

			if(hexValue.length() == 1) sbHex.append("0");
			sbHex.append(hexValue.toUpperCase());
		} 

		return sbHex.toString();
	}
	
	public static String getSessionValue(HttpServletRequest request, String key) {
		
		String strSessionValue = "";
		
		try {
			HttpSession session = request.getSession();
			Object objTemp = session.getAttribute(key);
			
			if(objTemp != null && !objTemp.equals("")) {
				strSessionValue = (String)objTemp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strSessionValue;
		
	}
	
	public static void setSessionValue(HttpServletRequest request, String key, String value) {
		try {
			HttpSession session = request.getSession();
			session.setAttribute(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static JSONObject getRestApi(String strUrl, String strApiKey) {
		
		JSONObject objReturnJson = new JSONObject();
		
		try {
			
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "KakaoAK " + strApiKey);
			
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) { // 정상 호출
				
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
			} else {  // 에러 발생시
				
				objReturnJson.put("resultCode", "-1");
				return objReturnJson;
				
			}
			
			String inputLine;
			
			StringBuffer resData = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				resData.append(inputLine);
			}
			br.close();
			
			//response 결과값을 json으로 변환한다.
			JSONParser parser = new JSONParser();
			objReturnJson = (JSONObject)parser.parse(resData.toString());
			objReturnJson.put("resultCode", "99");
			objReturnJson.put("apiType", "kakao");
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
			objReturnJson.put("resultCode", "-1");
			return objReturnJson;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			objReturnJson.put("resultCode", "-1");
			return objReturnJson;
			
		}  catch (ParseException e) {
			
			e.printStackTrace();
			
			objReturnJson.put("resultCode", "-1");
			return objReturnJson;
			
		}
		
		return objReturnJson;
	}*/
	
	/*public static JSONObject getRestApiForNaver(String strUrl, String strClientId, String strClientSecret) {
		
		JSONObject objReturnJson = new JSONObject();
		
		try {
			
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", strClientId);
			con.setRequestProperty("X-Naver-Client-Secret", strClientSecret);
			
			int responseCode = con.getResponseCode();
			
			BufferedReader br;
			if(responseCode == 200) { // 정상 호출
				
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
			} else {  // 에러 발생시
				
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				objReturnJson.put("resultCode", "-1");
				return objReturnJson;
				
			}
			
			String inputLine;
			
			StringBuffer resData = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				resData.append(inputLine);
			}
			br.close();
			
			System.out.println(resData);
			
			//response 결과값을 json으로 변환한다.(xml -> json 변환)
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(resData.toString());
				
			JSONParser parser = new JSONParser();
			objReturnJson = (JSONObject)parser.parse(xmlJSONObj.toString());
			objReturnJson.put("resultCode", "99");
			objReturnJson.put("apiType", "naver");
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
			objReturnJson.put("resultCode", "-1");
			return objReturnJson;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			objReturnJson.put("resultCode", "-1");
			return objReturnJson;
			
		}  catch (ParseException e) {
			
			e.printStackTrace();
			
			objReturnJson.put("resultCode", "-1");
			return objReturnJson;
			
		}
		
		return objReturnJson;
	}*/
	
	
	/**
	 * 
	 * @param contextPath
	 * @param function
	 * @param totCnt : 페이징 대상의 총 갯수
	 * @param pageLimit : 10
	 * @param pageBlock : 10
	 * @param pageNo : 현재 페이지 번호
	 * @return
	 */
	public static StringBuffer pageingFront(String contextPath, String function, int totCnt, int pageLimit, int pageBlock, int pageNo) {
		
		StringBuffer sb = new StringBuffer();
		
		//전체 페이지 개수
		int lastPageNum = ((totCnt - 1) / pageLimit) + 1;
		
		//화면에 보여질 시작 페이지 번호
		int startPageNum = (((pageNo - 1) / pageBlock) * pageBlock) + 1;
		
		//화면에 보여질 종료 페이지 번호
		int endPageNum = startPageNum + pageBlock - 1;
		
		//종료 페이지 범위 처리
		if(endPageNum > lastPageNum) {
			
			endPageNum = lastPageNum;
			
		}
		
		int prevPageGroup = 1;
		int nextPageGroup = lastPageNum;
		
		if(startPageNum - pageBlock < 1) {
			
			prevPageGroup = 1;
			
		} else {
			
			prevPageGroup = startPageNum - pageBlock;
			if(prevPageGroup <= 0) {
				
				prevPageGroup = 1;
			}
			
		}
		
		if(endPageNum + 1 > lastPageNum) {
			
			nextPageGroup = lastPageNum;
			
		} else {
			
			nextPageGroup = endPageNum + 1;
			
		}
		
		//처음 페이지로 이동
		sb.append("<div class=\"ctrl\"><a href=\"javascript:" + function + "('1', '')\" class=\"first\"><img src=\"" + contextPath + "/paging_first.png\" alt=\"첫페이지\" /></a></div>");
		
		sb.append("<ol>");
		
		//페이지 번호 표시
		for(int i = startPageNum; i<= endPageNum; i++) {
			
			if(i == pageNo) {
				
				sb.append("<li class=\"on\"><strong>" + i + "</strong></li>");
				
			} else {
				
				sb.append("<li><a href=\"javascript:" + function + "('" + i + "', '')\">" + i + "</a></li>");
				
			}
			
		}
		
		sb.append("<ol>");
		
		//마지막 페이지로 이동
		sb.append("<div class=\"ctrl\"><a href=\"javascript:" + function + "('" + lastPageNum + "', '')\" class=\"last\"><img src=\"" + contextPath + "/paging_last.png\" alt=\"마지막페이지\" /></a></div>");
		
		return sb;
		
	}
	
}
