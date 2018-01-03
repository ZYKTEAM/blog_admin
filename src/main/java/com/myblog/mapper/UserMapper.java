package com.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myblog.domain.User;

@Mapper
public interface UserMapper {
	
	public User getByUsername(@Param("account")String account);
	
	public User getUserByUserId(@Param("userId")Long userId);
	
	public List<User> listUser(@Param("start") Integer start,@Param("limit") Integer limit,@Param("query")String query);
	
	public Integer countListUser(@Param("query")String query);
	
}
