package com.kb.test.client.book.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "keywordcount")
public class BookVo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;				//시퀀스
	
	@Column(length = 128)
	private String keyword;			//키워드
	
	@Column(length = 64)
	private int searchcnt;			//조회회수
	
	@Transient
	private String isPageChange;	//페이지전환여부
	@Transient
	private int pageNo;			//페이지번호

	
	
	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getSearchcnt() {
		return searchcnt;
	}

	public void setSearchcnt(int searchCnt) {
		this.searchcnt = searchCnt;
	}
	
	public String getIsPageChange() {
		return isPageChange;
	}

	public void setIsPageChange(String isPageChange) {
		this.isPageChange = isPageChange;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}
