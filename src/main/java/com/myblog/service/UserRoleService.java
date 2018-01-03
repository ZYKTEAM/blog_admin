package com.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.myblog.domain.UserRole;
import com.myblog.mapper.UserMapper;
import com.myblog.mapper.UserRoleMapper;

@Service
public class UserRoleService {

	@Autowired
	public UserRoleMapper userRoleDao;
	
	@Autowired
	public UserMapper userDao;
	
	public List<UserRole> findRoleUserByRoleId(Long userId,Long roleId){
		return userRoleDao.findRolePermissionByUserId(userId, roleId);
	}
	
	public Integer saveUserRoles(@RequestBody List<UserRole> userRoles){
		return userRoleDao.saveUserRoles(userRoles);
	}
	
	public Integer delRoleUserByUserId(Long[] delteaIds,Long roleId){
		return userRoleDao.delRoleUserByUserId(delteaIds,roleId);
	}
 
}
