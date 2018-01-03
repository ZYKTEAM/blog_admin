package com.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myblog.domain.RolePermission;
@Mapper
public interface RolePermissionMapper {

//	Long saveRolePermission(RolePermission rolePermission);
	
	Integer saveRolePermissionBatch(@Param("list") List<RolePermission> list);
	
	List<RolePermission> findRolePermissions(@Param("type")Integer type,@Param("refIds")Long[] refIds);
	
	Integer removeRolePermissionsByRefId(@Param("permIds")Long[] permIds,@Param("type")Integer type,@Param("refId")Long refId);

}
