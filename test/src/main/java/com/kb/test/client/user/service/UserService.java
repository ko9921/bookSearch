package com.kb.test.client.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kb.test.client.user.vo.UserHistoryVo;
import com.kb.test.client.user.vo.UserVo;

public interface UserService {
	
	public HashMap<String, String> getLogin(UserVo userVo);
	public HashMap<String, String> signup(UserVo userVo);
	public List<UserHistoryVo> getUserHistoryList(String userID);
	
	public HashMap<String, String> setUserHistory(String keyword, String userID);
}
