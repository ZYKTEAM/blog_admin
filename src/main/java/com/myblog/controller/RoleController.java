package com.myblog.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myblog.domain.Role;
import com.myblog.domain.UserRole;
import com.myblog.service.RoleService;
import com.myblog.service.UserRoleService;
import com.myblog.ulits.HttpResults;
import com.myblog.ulits.PageList;
import com.myblog.ulits.PageParam;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@RequestMapping(value="/role", method=RequestMethod.GET)
	public String index(Model model){
		return "user/role-list";
    }
	
	@RequestMapping(value="/roles", method=RequestMethod.GET)
	public @ResponseBody PageList<Role> roles(Integer start,Integer limit,String query){
		PageParam page = new PageParam(start, limit);
		Integer count = roleService.countRoles(query);
		PageList<Role> data = new PageList<>(count, limit);
		if(count>page.getStart()) data.setList(roleService.findRoles(query,page.getStart(), page.getLimit()));
		return data;
    }
	
	@RequestMapping(value="/roles", method=RequestMethod.POST)
	public @ResponseBody Object saveRole(Role role){
		HttpResults result = new HttpResults();
		if(null == role.getId()){
			roleService.saveRole(role);
		}else{
			roleService.updateRoleSelected(role);
		} 
		return result;
    }
	
	@RequestMapping(value="/roles/{id}", method=RequestMethod.GET)
	public @ResponseBody Object getRoleById(@PathVariable("id")Long id){
		HttpResults result = new HttpResults();
		Role role = roleService.getRoleById(id);
		result.put("role", role);
		return result;
    }
	
	@RequestMapping(value="/findRoleUserByRoleId", method=RequestMethod.GET)
	public @ResponseBody List<UserRole> findRoleUserByRoleId(Long roleId){
		return userRoleService.findRoleUserByRoleId(null,roleId);
    }
	
	@RequestMapping(value = "/roleUser", method = RequestMethod.POST)
	public @ResponseBody Integer roleUser(@RequestParam(value = "userIds[]", required = false) Long[] userIds,
			@RequestParam(value = "delteaIds[]", required = false) Long[] delteaIds, Long roleId) {
		int count = 1;
		List<UserRole> roles = new ArrayList<UserRole>();
		if (userIds != null) {
			for (Long userId : userIds) {
				UserRole role = new UserRole();
				role.setUserId(userId);
				role.setRefId(roleId);
				role.setType(1);
				roles.add(role);
			}
		}
		if (userIds == null && delteaIds == null) {
			count = 0;// 没有进行操作直接返回
		} else if (delteaIds == null) {
			count = 1;
			userRoleService.saveUserRoles(roles);
		} else if (userIds == null) {
			count = 1;
			userRoleService.delRoleUserByUserId(delteaIds,roleId);
		} else {
			count = 1;
			for (Long userId : userIds) {
				List<UserRole> a = userRoleService.findRoleUserByRoleId(roleId, userId);
				if (a.size() ==0) {
					userRoleService.saveUserRoles(roles);
				}
			}
			userRoleService.delRoleUserByUserId(delteaIds,roleId);
		}
		return count;
	}
}
