package com.kb.test.client.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kb.test.client.book.vo.BookVo;

@Repository
public interface BookRepository extends JpaRepository<BookVo, Integer> {
	
	// 키워드로 조회한다.
	public BookVo findBykeyword(String keyword);
	// list 조회
	public List<BookVo> findTop10ByOrderBySearchcntDescIdxAsc();
}