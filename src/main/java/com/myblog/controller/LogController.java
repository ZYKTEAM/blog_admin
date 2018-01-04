package com.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myblog.domain.LogDO;
import com.myblog.log.annotation.Log;
import com.myblog.service.ILogService;
import com.myblog.ulits.PageList;
import com.myblog.ulits.PageParam;

@Controller
public class LogController {
	
	@Autowired
	public ILogService iLogService;
 
	@RequestMapping(value="/log", method=RequestMethod.GET)
    public String index(Model model) {
        return "monitor/monitor-log";
    }

	@RequestMapping(value="/listLog", method=RequestMethod.GET)
    @Log("日志查询列表")
	public @ResponseBody PageList<LogDO> listUser(Integer start,Integer limit){
		PageParam page = new PageParam(start, limit);
		int count = iLogService.countLog();
		PageList<LogDO> result = new PageList<>(count, limit);
		if(count>page.getStart())
			result.setList(iLogService.listLog(page.getStart(), page.getLimit()));
		return result;
    }
}
