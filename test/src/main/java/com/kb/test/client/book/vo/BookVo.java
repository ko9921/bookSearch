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
	private String isbn;			//isbn
	@Transient
	private String resultCode;		//결과코드
	@Transient
	private String resultMsg;		//결과메시지
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}
