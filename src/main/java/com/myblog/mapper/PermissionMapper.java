package com.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myblog.domain.Permission;
@Mapper
public interface PermissionMapper {

	List<Permission> findPermissionsByPId(@Param("pid") Long pid);

	List<Permission> findPermissionsPathAndLeaf(@Param("path")String path, @Param("leaf")Integer leaf);

	int countListPermission(@Param("query") String query);

	List<Permission> findPermissionList(@Param("start")Integer start,@Param("limit") Integer limit,@Param("query") String query);

	Permission getMenuById(Long id);

    void saveMenu(Permission menu);

	void updateMenu(Permission menu);
}
