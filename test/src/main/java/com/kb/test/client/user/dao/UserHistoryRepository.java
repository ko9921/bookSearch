package com.kb.test.client.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kb.test.client.book.vo.BookVo;
import com.kb.test.client.user.vo.UserHistoryVo;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistoryVo, Integer> {
	
	// 아이디로 조회한다.
	public List<UserHistoryVo> findTop10ByidOrderBySearchdtimeDescIdxAsc(String id);
	// 아이디와 키워드로 조회한다.
	public UserHistoryVo findByIdAndKeyword(String id, String keyword);
}