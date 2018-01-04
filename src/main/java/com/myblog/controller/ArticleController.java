package com.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myblog.domain.User;
import com.myblog.service.UserService;
import com.myblog.ulits.PageList;
import com.myblog.ulits.PageParam;

@Controller
public class ArticleController {
	
	@Autowired
	public UserService userService;
 
	@RequestMapping(value="/article", method=RequestMethod.GET)
    public String index(Model model) {
        return "article/article-list";
    }
	
  /*  @RequestMapping(value="/listUser", method=RequestMethod.GET)
	public @ResponseBody PageList<User> listUser(Integer start,Integer limit,String query){
		PageParam page = new PageParam(start, limit);
		int count = userService.countListUser(query);
		PageList<User> result = new PageList<>(count, limit);
		if(count>page.getStart())
			result.setList(userService.listUser(page.getStart(), page.getLimit(),query));
		return result;
    }*/
    
}
