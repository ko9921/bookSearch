package com.kb.test.client.user.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kb.test.client.user.service.UserService;
import com.kb.test.client.user.vo.UserVo;

@RequestMapping("/user")
@Controller
public class UserController {
	
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
	
	@PostMapping("/loginAction")
	@ResponseBody
	public Map<String, String> loginAction(UserVo userVo) {
		
		HashMap<String, String> result = userService.getLogin(userVo);
		
		return result;
	}
	
	@PostMapping("/signupAction")
	@ResponseBody
	public Map<String, String> signupAction(UserVo userVo) {
		
		HashMap<String, String> result = userService.signup(userVo);
		
		return result;
	}
}
