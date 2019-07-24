package com.kb.test.client.book.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kb.test.client.book.dao.BookRepository;
import com.kb.test.client.book.vo.BookVo;
import com.kb.test.common.util.APIUtil;

@Service
public class BookServiceImpl implements BookService{
	
	private final String kakaoBookAPI = "https://dapi.kakao.com/v3/search/book";
	private final String kakaoBookAPIKey = "3a2bfa9a131b45176b835a71d7eaefb8";
	
	private final String naverBookAPI = "https://openapi.naver.com/v1/search/book.json";
	private final String naverClientID = "wCtP7aTLJ9awlv_2NPDl";
	private final String naverClientSecret = "ApXnoOP4J_";
	
	@Autowired
	BookRepository bookRepository;
	
	@Override
	public List<BookVo> getKeywordList() {

		List<BookVo> response = null;
		try {
			List<BookVo> result = bookRepository.findTop10ByOrderBySearchcntDescIdxAsc();

			response = result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Transactional
	public HashMap<String, String> setBookKeyword(String keyword) {
		HashMap<String, String> response = new HashMap<String, String>();

		try {
			// Insert or Update
			BookVo resultKeyword = bookRepository.findBykeyword(keyword);
			if(resultKeyword != null) {
				//update
				resultKeyword.setSearchcnt(resultKeyword.getSearchcnt() + 1);
				bookRepository.save(resultKeyword);
			} else {
				//insert
				resultKeyword = new BookVo();
				
				resultKeyword.setKeyword(keyword);
				resultKeyword.setSearchcnt(1);
				
				bookRepository.save(resultKeyword);
			}
				
			response.put("msg", "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			response.put("msg", "FAIL");

			// rollback status 수동 발생 - try catch 내에서 exception 이 처리되어 함수의 @Transactinal 작동안됨
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return response;
	}
	
	@Transactional
	public JSONObject getSearchBookList(BookVo bookVo, boolean isDetail) {
		JSONObject resultJson = new JSONObject();
		String searchParam = "";

		try {
			if(!bookVo.getKeyword().equals("")) {
				if(searchParam.equals("")) {
					searchParam = searchParam + "?query=" + URLEncoder.encode(bookVo.getKeyword(), "UTF-8");
				} else {
					searchParam = searchParam + "&query=" + URLEncoder.encode(bookVo.getKeyword(), "UTF-8");
				}
			}
			
			if(!isDetail && bookVo.getPageNo() != 0) {
				if(searchParam.equals("")) {
					searchParam = searchParam + "?page=" + bookVo.getPageNo();
				} else {
					searchParam = searchParam + "&page=" + bookVo.getPageNo();
				}
			}
			
			resultJson = APIUtil.getKakaoRestApi(kakaoBookAPI+searchParam, kakaoBookAPIKey);
			
			// Naver
			if(!((String)resultJson.get("result")).equals("SUCCESS")) {
				searchParam = "";
				
				if(!bookVo.getKeyword().equals("")) {
					if(searchParam.equals("")) {
						searchParam = searchParam + "?query=" + URLEncoder.encode(bookVo.getKeyword(), "UTF-8");
					} else {
						searchParam = searchParam + "&query=" + URLEncoder.encode(bookVo.getKeyword(), "UTF-8");
					}
				}
				
				if(!isDetail && bookVo.getPageNo() != 0) {
					if(searchParam.equals("")) {
						searchParam = searchParam + "?start=" + (bookVo.getPageNo()*10 -9);
					} else {
						searchParam = searchParam + "&start=" + (bookVo.getPageNo()*10 -9);
					}
				}
				System.out.println("resultJson (KKO) : " + resultJson.toString());
				resultJson = APIUtil.getNaverRestApi(naverBookAPI+searchParam, naverClientID, naverClientSecret);
				System.out.println("resultJson (Naver) : " + resultJson.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("result", "FAIL");

			// rollback status 수동 발생 - try catch 내에서 exception 이 처리되어 함수의 @Transactinal 작동안됨
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultJson;
	}
	
	
}
