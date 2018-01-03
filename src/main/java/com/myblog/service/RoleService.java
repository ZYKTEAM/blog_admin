package com.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.myblog.domain.Role;
import com.myblog.mapper.RoleMapper;

@Service
public class RoleService {

	@Autowired
	public RoleMapper roleMapper;
	
	public Role getRoleById(@PathVariable("id")Long id){
		return roleMapper.getRoleById(id);
	}
	
	public Integer countRoles(String query){
		return roleMapper.countRoles(query);
	}
	
	public List<Role> findRoles(String query,Integer start,Integer limit){
		return roleMapper.findRoles(query, start, limit);
	}
	
	public Long saveRole(@RequestBody Role role){
		roleMapper.saveRole(role);
		return role.getId();
	}
	
	public Integer updateRole(@RequestBody Role role){
		return roleMapper.updateRole(role);
	}
	
	public Integer updateRoleSelected(@RequestBody Role role){
		return roleMapper.updateRoleSelected(role);
	}
	
}
