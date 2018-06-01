package com.elitech.human.resource.dao.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Repository;

import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Repository
public class LoginRedisRepo {

    private RedisTemplate<String, RedisVO> redisTemplate;
    private ValueOperations<String, RedisVO> valueOperations;
    
    @Autowired
    public LoginRedisRepo(RedisTemplate<String, RedisVO> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    private void init() {
    	redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(RedisVO.class));
        valueOperations = redisTemplate.opsForValue();
    }

    public void save(String key, RedisVO vo) {
        valueOperations.set(key, vo);
    }
 
    public void update(String key, RedisVO vo) {
        valueOperations.set(key, vo);
    }
 
    public RedisVO find(String key) {
        return (RedisVO) valueOperations.get(key);
    }

	public boolean isLoggedIn (String sessionId) {
		if (StringUtils.isBlank(sessionId)) {return false;}
		RedisVO vo = find(sessionId);
		
		return vo == null ? false : true;
	}

	public void setExpire (String sessionId, int time) {
		redisTemplate.expire(sessionId, time, TimeUnit.MINUTES);
	}
	
	public void logout (String key) {
		redisTemplate.delete(key);
	}
    
}


