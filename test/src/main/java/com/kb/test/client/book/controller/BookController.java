package com.kb.test.client.book.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kb.test.client.book.service.BookService;
import com.kb.test.client.book.vo.BookVo;
import com.kb.test.client.user.service.UserService;
import com.kb.test.common.util.CommonUtil;
import com.kb.test.common.util.JsonUtil;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/book")
@Controller
public class BookController {
	
	private Gson gson = new Gson();
	
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/searchList")
	@ResponseBody
	@Transactional
	public List<Map<String, Object>> getSearchList(HttpServletRequest req, @RequestParam Map<String, Object> params) {
		
		HttpSession session = req.getSession();
		String keyword = "";
		
		// 5. 인기 키워드 목록 (저장로직)
		if(params != null) {
			keyword = CommonUtil.getSafeString(params.get("search"));
		}
		
		if(keyword.equals("")) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		
		HashMap<String, String> response = bookService.setBookKeyword(keyword);
		
		if(response.get("msg").equals("FAIL")) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		// 4. 내 검색 히스토리 (로그인시에만 검색히스토리 작성) 
		if(!CommonUtil.getSafeString(session.getAttribute("id")).equals("")) {
			response = userService.setUserHistory(keyword, CommonUtil.getSafeString(session.getAttribute("id")));
			if(response.get("msg").equals("FAIL")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		
		// 2. 책 검색
		
		/*String strResponse = APIUtil.requestAPIJsonData(APIUtil.GET, params, apiContext + "/train/getTrainList");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		JSONObject jsonOb = new JSONObject(strResponse);
		
		if(jsonOb.get("msg").toString().equals("SUCCESS") && !jsonOb.get("result").toString().equals("null")) {
			JSONArray tempJson = new JSONArray(jsonOb.get("result").toString());
			
			result = JsonUtil.getListMapFromJsonArray(tempJson);
		}*/
		
		//return result;
		return null;
	}
	
	@GetMapping("/keywordList")
	@ResponseBody
	public List<Map<String, Object>> getKeywordList() {
		// 5. 인기 키워드 목록 (리스트 가져오기)
		List<BookVo> response = bookService.getKeywordList();
		JSONArray tempJson = new JSONArray();
		
		if(response != null) {
			for(int i=0; i<response.size(); i++) {
				BookVo keywordItem = response.get(i);
				String strKeyword = gson.toJson(keywordItem).trim().replace("\"", "'");
				
				JSONObject jsonOb = new JSONObject(strKeyword);
				
				tempJson.put(jsonOb);
			}
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = JsonUtil.getListMapFromJsonArray(tempJson);
		
		return result;
	}
}
