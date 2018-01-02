package com.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myblog.domain.Permission;
@Mapper
public interface PermissionMapper {

	List<Permission> findPermissionsByPId(@Param("pid") Long pid);

	List<Permission> findPermissionsPathAndLeaf(@Param("path")String path, @Param("leaf")Integer leaf);

}
