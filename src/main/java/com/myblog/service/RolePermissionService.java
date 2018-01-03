package com.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.myblog.domain.RolePermission;
import com.myblog.mapper.RolePermissionMapper;


@Service
public class RolePermissionService {

	@Autowired
	public RolePermissionMapper rolePermissionDao;
	
	public Integer saveRolePermissionBatch(@RequestBody List<RolePermission> list){
		return rolePermissionDao.saveRolePermissionBatch(list);
	}
	
	public List<RolePermission> findRolePermissions(Integer type,Long... refIds){
		return rolePermissionDao.findRolePermissions(type, refIds);
	}
	
	public Integer removeRolePermissionsByRefId(Long[] permIds,Integer type,Long refId){
		return rolePermissionDao.removeRolePermissionsByRefId(permIds, type, refId);
	}

}
