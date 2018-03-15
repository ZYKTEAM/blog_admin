package com.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblog.domain.User;
import com.myblog.mapper.UserMapper;

 
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	
    public User getUserByAccount(String username) {
        return userMapper.getByUsername(username);
    }
    
    public List<User> listUser(Integer start,Integer limit,String query) {
        return userMapper.listUser(start, limit, query);
    }
    
    public Integer countListUser(String query) {
        return userMapper.countListUser(query);
    }
	
    public Integer userAdd(User user) {
        return userMapper.userAdd(user);
    }
	
}
