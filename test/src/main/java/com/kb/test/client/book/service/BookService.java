package com.kb.test.client.book.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.kb.test.client.book.vo.BookVo;

public interface BookService {
	
	public List<BookVo> getKeywordList();

	public HashMap<String, String> setBookKeyword(String keyword);
	
	public JSONObject getSearchBookList(BookVo bookVo, boolean isDetail);
}
