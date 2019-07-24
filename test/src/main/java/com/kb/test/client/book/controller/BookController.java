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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
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
	public Map<String, Object> getSearchList(HttpServletRequest req, BookVo bookVo) {
		
		HttpSession session = req.getSession();
		String keyword = "";
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 5. 인기 키워드 목록 (저장로직)
		if(bookVo != null) {
			keyword = CommonUtil.getSafeString(bookVo.getKeyword());
		}
		
		if(keyword.equals("")) {
			result.put("result", "FAIL");
			result.put("msg", "검색어를 입력해주세요.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		HashMap<String, String> response = bookService.setBookKeyword(keyword);
		
		if(response.get("msg").equals("FAIL")) {
			result.put("result", "FAIL");
			result.put("msg", "검색도중 오류가 발생하였습니다. 다시 시도해 주시기 바랍니다.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		// 4. 내 검색 히스토리 (로그인시에만 검색히스토리 작성) 
		if(!CommonUtil.getSafeString(session.getAttribute("id")).equals("")) {
			response = userService.setUserHistory(keyword, CommonUtil.getSafeString(session.getAttribute("id")));
			if(response.get("msg").equals("FAIL")) {
				result.put("result", "FAIL");
				result.put("msg", "검색도중 오류가 발생하였습니다. 다시 시도해 주시기 바랍니다.");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		
		// 2. 책 검색
		JSONObject resultJson = bookService.getSearchBookList(bookVo, false);
		
		if(!((String)resultJson.get("result")).equals("SUCCESS")) {
			// 결과 없음
			result.put("result", "FAIL");
			result.put("msg", "검색 결과가 없습니다.");
		} else {
			// 결과값 전송
			result.put("result", "SUCCESS");
			result.put("msg", "");
			result.put("bookList", resultJson.get("data").toString());
		}
		
		return result;
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
	
	@GetMapping("/detail")
	@ResponseBody
	@Transactional
	public Map<String, Object> getBookDetail(HttpServletRequest req, BookVo bookVo) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		// 2. 책 검색
		JSONObject resultJson = bookService.getSearchBookList(bookVo, true);
		
		if(!((String)resultJson.get("result")).equals("SUCCESS")) {
			// 결과 없음
			result.put("result", "FAIL");
			result.put("msg", "정보를 가져오던중 문제가 발생했습니다. 다시 시도 해주시기 바랍니다.");
		} else {
			// 결과값 전송
			result.put("result", "SUCCESS");
			result.put("msg", "");
			result.put("bookList", resultJson.get("data").toString());
		}
		
		return result;
	}
}
