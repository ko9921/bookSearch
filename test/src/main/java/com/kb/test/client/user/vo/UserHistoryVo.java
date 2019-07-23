package com.kb.test.client.user.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "userhistory")
public class UserHistoryVo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;				//시퀀스
	
	@Column(length = 64)
	private String id;				//아이디
	
	@Column(length = 128)
	private String keyword;			//키워드
	
	@Column
	private Timestamp searchdtime;	//등록일시

	
	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Timestamp getSearchdtime() {
		return searchdtime;
	}

	public void setSearchdtime(Timestamp searchdtime) {
		this.searchdtime = searchdtime;
	}
}
