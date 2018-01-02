package com.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myblog.domain.Permission;
import com.myblog.mapper.PermissionMapper;

@Service
public class PermissionService {

//	@Autowired
//	private RolePermissionDao rolePermissionDao;
	@Autowired
	private PermissionMapper permissionMapper;
	/*@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserDao userDao;*/
	
	@RequestMapping(value="/permissions/pid", method=RequestMethod.GET)
	public List<Permission> findPermissionsByPId(Long pid){
		return permissionMapper.findPermissionsByPId(pid);
	}
	
	/*@RequestMapping(value="/permissions/user", method=RequestMethod.GET)
	public List<Permission> findMyPerMissions(String path,@RequestParam("userId") Long userId){
		User user = userDao.getUserByUserId(userId);
		path = Strings.isNullOrEmpty(path)?null:path+"%";
		List<Permission> perms = permissionDao.findPermissionsPathAndLeaf(path,null);
		if("admin".equals(user.getAccount())) return perms;
	
		List<UserRole> userRoles = userRoleDao.findRolePermissionByUserId(user.getId(),null);
		List<Long> roleIds = Lists.newArrayList();
//		List<Long> orgIds = Lists.newArrayList();
		for (UserRole userRole : userRoles) {
			if(userRole.getType().equals(1)){
				roleIds.add(userRole.getRefId());
			} else if(userRole.getType().equals(1)){
				orgIds.add(userRole.getRefId());
			} 
		}
		
		//个人权限校验
		Long[] userIds = {userId};
		List<RolePermission> myRolePerms =rolePermissionDao.findRolePermissions(2, userIds);
		
		//角色权限校验
		if(roleIds.size()>0){
			List<RolePermission> rolePerms = rolePermissionDao.findRolePermissions(1, roleIds.toArray(new Long[]{}));
			myRolePerms.addAll(rolePerms);
		}
		
		Set<Long> panelRolePermIdSet = myRolePerms.stream().collect(Collectors.mapping(RolePermission::getPermissionId, Collectors.toSet()));
		perms = perms.stream().filter(item->panelRolePermIdSet.contains(item.getId())).collect(Collectors.toList());
	
		return perms;
	}*/

}
