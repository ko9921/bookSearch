package com.kb.test.client.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kb.test.client.book.vo.BookVo;
import com.kb.test.client.user.service.UserService;
import com.kb.test.client.user.vo.UserHistoryVo;
import com.kb.test.client.user.vo.UserVo;
import com.kb.test.common.util.CommonUtil;
import com.kb.test.common.util.JsonUtil;

@RequestMapping("/user")
@Controller
public class UserController {
	
	private Gson gson = new Gson();
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signup(HttpServletRequest req, Locale locale, Model model) {
		
		return "client/user/signUP";
	}
	
	@GetMapping("/login")
	public String login(HttpServletRequest req, Locale locale, Model model) {
		
		return "client/user/login";
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest req) 
	{
		req.getSession().removeAttribute("id");
		req.getSession().removeAttribute("name");
		
		return "Logout";
	}
	
	@PostMapping("/loginAction")
	@ResponseBody
	public Map<String, String> loginAction(HttpServletRequest req, UserVo userVo) {
		
		HttpSession session = req.getSession();
		
		// 1. 로그인
		HashMap<String, String> result = userService.getLogin(userVo);
		
		if(result.get("result").equals("SUCCESS") ) {
			session.setAttribute("id", userVo.getId());
			session.setAttribute("name", userVo.getName());
		}
		
		return result;
	}
	
	@PostMapping("/signupAction")
	@ResponseBody
	public Map<String, String> signupAction(UserVo userVo) {
		
		// 1. 회원가입
		HashMap<String, String> result = userService.signup(userVo);
		
		return result;
	}
	
	@GetMapping("/userHistoryList")
	@ResponseBody
	public List<Map<String, Object>> getUserHistoryList(HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		// 4. 내 검색히스토리 가져오기
		List<UserHistoryVo> response = userService.getUserHistoryList(CommonUtil.getSafeString(session.getAttribute("id")));
		JSONArray tempJson = new JSONArray();
		
		if(response != null) {
			for(int i=0; i<response.size(); i++) {
				UserHistoryVo keywordItem = response.get(i);
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
