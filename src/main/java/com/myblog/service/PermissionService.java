package com.myblog.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.myblog.domain.Permission;
import com.myblog.domain.RolePermission;
import com.myblog.domain.User;
import com.myblog.domain.UserRole;
import com.myblog.mapper.PermissionMapper;
import com.myblog.mapper.RolePermissionMapper;
import com.myblog.mapper.UserMapper;
import com.myblog.mapper.UserRoleMapper;

@Service
public class PermissionService {

	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private UserMapper userMapper;
	
	public List<Permission> findPermissionsByPId(Long pid){
		return permissionMapper.findPermissionsByPId(pid);
	}
	
	public List<Permission> findPermissions(String path,@RequestParam("userId") Long userId){
		User user = userMapper.getUserByUserId(userId);
		path = Strings.isNullOrEmpty(path)?null:path+"%";
		List<Permission> perms = permissionMapper.findPermissionsPathAndLeaf(path,null);
		if("admin".equals(user.getLoginName())) return perms;
	
		List<UserRole> userRoles = userRoleMapper.findRolePermissionByUserId(user.getId(),null);
		List<Long> roleIds = Lists.newArrayList();
		for (UserRole userRole : userRoles) {
			if(userRole.getType().equals(1)){
				roleIds.add(userRole.getRefId());
			} 
		}
		
		//个人权限校验
		Long[] userIds = {userId};
		List<RolePermission> myRolePerms =rolePermissionMapper.findRolePermissions(2, userIds);
		
		//角色权限校验
		if(roleIds.size()>0){
			List<RolePermission> rolePerms = rolePermissionMapper.findRolePermissions(1, roleIds.toArray(new Long[]{}));
			myRolePerms.addAll(rolePerms);
		}
		
		Set<Long> panelRolePermIdSet = myRolePerms.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toSet()));
		perms = perms.stream().filter(item->panelRolePermIdSet.contains(item.getId())).collect(Collectors.toList());
	
		return perms;
	}

	public int countListPermisson(String query) {
		return permissionMapper.countListPermission(query);
	}

	public List<Permission> findList(Integer start, Integer limit, String query) {
		return permissionMapper.findPermissionList(start,limit,query);
	}

	public Permission getMenuById(Long id) {
		return permissionMapper.getMenuById(id);
	}

	public void saveMenu(Permission menu) {
		if(menu.getId()==null){
			menu.setIsDeleted(0);
			menu.setLeaf(0);
			permissionMapper.saveMenu(menu);
		}else {
			permissionMapper.updateMenu(menu);
		}
	}

	public void deleteMenu(Long id) {
		permissionMapper.deleteMenu(id);
	}
}
