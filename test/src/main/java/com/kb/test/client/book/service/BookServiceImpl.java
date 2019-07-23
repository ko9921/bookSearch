package com.kb.test.client.book.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kb.test.client.book.dao.BookRepository;
import com.kb.test.client.book.vo.BookVo;

@Service
public class BookServiceImpl implements BookService{
	
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
}
