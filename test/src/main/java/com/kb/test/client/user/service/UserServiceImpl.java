package com.kb.test.client.user.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kb.test.client.user.dao.UserHistoryRepository;
import com.kb.test.client.user.dao.UserRepository;
import com.kb.test.client.user.vo.UserHistoryVo;
import com.kb.test.client.user.vo.UserVo;
import com.kb.test.common.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{
	
	private final String secretKey = "289F40E6640124B2628640168C3C5464";
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserHistoryRepository userHistoryRepository;
	
	public HashMap<String, String> getLogin(UserVo userVo) {
		HashMap<String, String> response = new HashMap<String, String>();

		try {
			UserVo resultUser = userRepository.findByid(userVo.getId());
			if(resultUser != null) {
				//Password Check
				String tempPass = CommonUtil.getPassword(userVo.getPassword(), secretKey);
				
				if(resultUser.getPassword().equals(tempPass)) {
					// Success
					response.put("result", "SUCCESS");
					response.put("msg", "로그인되었습니다.");
				} else {
					//Error
					response.put("result", "FAIL");
					response.put("msg", "아이디나 패스워드가 잘못되었습니다. 다시 확인해주시기 바랍니다.");
				}
				
			} else {
				//Error
				response.put("result", "FAIL");
				response.put("msg", "아이디나 패스워드가 잘못되었습니다. 다시 확인해주시기 바랍니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", "FAIL");
			response.put("msg", "예기치 못한 오류가 발생하였습니다. 잠시 후 다시 시도해주시기 바랍니다.");
		}
		return response;
	}
	
	public HashMap<String, String> signup(UserVo userVo) {
		HashMap<String, String> response = new HashMap<String, String>();

		try {
			UserVo resultUser = userRepository.findByid(userVo.getId());
			if(resultUser != null) {
				//Error
				response.put("result", "FAIL");
				response.put("msg", "현재 사용중인 아이디입니다. 다른 아이디로 가입부탁드립니다.");
			} else {
				String tempPass = CommonUtil.getPassword(userVo.getPassword(), secretKey);
				
				UserVo signupUser = new UserVo();
				
				signupUser.setId(userVo.getId());
				signupUser.setName(userVo.getName());
				signupUser.setPassword(tempPass);
				
				userRepository.save(signupUser);
				response.put("result", "SUCCESS");
				response.put("msg", "회원가입이 완료되었습니다. 로그인후 이용해주시기 바랍니다.");
			}
		} catch (Exception e) { 
			e.printStackTrace();
			response.put("result", "FAIL");
			response.put("msg", "예기치 못한 오류가 발생하였습니다. 잠시 후 다시 시도해주시기 바랍니다.");
		}
		return response;
	}
	
	@Override
	public List<UserHistoryVo> getUserHistoryList(String id) {

		List<UserHistoryVo> response = null;
		try {
			List<UserHistoryVo> result = userHistoryRepository.findTop10ByidOrderBySearchdtimeDescIdxAsc(id);

			response = result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	@Transactional
	public HashMap<String, String> setUserHistory(String keyword, String id) {
		HashMap<String, String> response = new HashMap<String, String>();

		try {
			// Insert or Update
			UserHistoryVo resultKeyword = userHistoryRepository.findByIdAndKeyword(id, keyword);
			if(resultKeyword != null) {
				//update
				resultKeyword.setSearchdtime(new Timestamp(System.currentTimeMillis()));
				userHistoryRepository.save(resultKeyword);
			} else {
				//insert
				resultKeyword = new UserHistoryVo();
				
				resultKeyword.setKeyword(keyword);
				resultKeyword.setId(id);
				resultKeyword.setSearchdtime(new Timestamp(System.currentTimeMillis()));
				
				userHistoryRepository.save(resultKeyword);
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
