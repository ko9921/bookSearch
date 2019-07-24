package com.kb.test.client.home.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/")
@Controller
public class HomeController {
	
	@GetMapping("")
	public String home(HttpServletRequest req, Locale locale, Model model) {
		
		return "client/home/home";
	}
}
