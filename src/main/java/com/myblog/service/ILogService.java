package com.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblog.domain.LogDO;
import com.myblog.mapper.ILogMapper;

@Service
public class ILogService {

	@Autowired
	private ILogMapper logDao;
	
	public int insertLog(LogDO logDo) {
		return logDao.insertLog(logDo);
	}

	public List<LogDO> listLog(Integer start,Integer limit) {
		return logDao.listLog(start, limit);
	}

	public int countLog() {
		return logDao.countLog();
	}

}
