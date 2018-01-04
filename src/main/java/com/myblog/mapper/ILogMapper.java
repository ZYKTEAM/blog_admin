package com.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myblog.domain.LogDO;
@Mapper
public interface ILogMapper {

	int insertLog(LogDO entity);
	
	List<LogDO> listLog(@Param("start") Integer start,@Param("limit") Integer limit);
	
	Integer countLog();
	 
}
