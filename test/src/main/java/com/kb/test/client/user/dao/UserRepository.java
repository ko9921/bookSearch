package com.kb.test.client.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kb.test.client.user.vo.UserVo;

@Repository
public interface UserRepository extends JpaRepository<UserVo, Integer> {
	
	// 아이디로 조회한다.
	public UserVo findByid(String id);
}