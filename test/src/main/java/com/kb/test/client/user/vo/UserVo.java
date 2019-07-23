package com.kb.test.client.user.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class UserVo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;				//시퀀스
	
	@Column(length = 64)
	private String id;				//아이디
	
	@Column(length = 64)
	private String name;			//이름
	
	@Column(length = 128)
	private String password;		//암호
	
	@Transient
	private String passwordConfirm;	//암호확인

	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
