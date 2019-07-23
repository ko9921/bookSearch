package com.kb.test.client.book.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kb.test.client.book.vo.BookVo;

public interface BookService {
	
	public List<BookVo> getKeywordList();

	public HashMap<String, String> setBookKeyword(Map<String, Object> condition);
}
