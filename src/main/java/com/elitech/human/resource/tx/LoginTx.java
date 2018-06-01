package com.elitech.human.resource.tx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitech.human.resource.service.LoginService;
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.MenuVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class LoginTx {

	@Autowired
	private LoginService service;

	@Transactional
	public boolean isLoggedIn (String sessionId) {
		return service.isLoggedIn(sessionId);
	}

	@Transactional
	public RedisVO findLoginInfo (String sessionId) {
		return service.findLoginInfo(sessionId);
	}

	@Transactional
	public List<MenuVO> findUserMenus (String userId) throws Exception {
		return service.findUserMenus(userId);
	}

	@Transactional
	public void setExpire (String key, int expireTime) {
		service.setExpire(key, expireTime);
	}

	/**
	 * 把登入資訊存到Cache
	 * 
	 * @create by Adam
	 * @create date: Nov 8, 2017
	 *
	 * @param key
	 * @param redisVO
	 * @param expireTime
	 */
	@Transactional
	public void saveLoginInfo (String key, RedisVO redisVO, int expireTime) {
		service.saveLoginInfo(key, redisVO, expireTime);
	}

	@Transactional
	public List<ButtonVO> findUserButtons (String userId) throws Exception {
		return service.findUserButtons(userId);
	}

	@Transactional
	public void logout (String key) {
		service.logout(key);
	}
	
}


