package com.myblog.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.myblog.domain.User;
import com.myblog.log.annotation.Log;
import com.myblog.service.UserService;
import com.myblog.ulits.EncryptedPassword;
import com.myblog.ulits.PageList;
import com.myblog.ulits.PageParam;
import com.myblog.ulits.UUIDGenerator;

@Controller
public class UserController {
	
	@Autowired
	public UserService userService;
 
	@RequestMapping(value="/user", method=RequestMethod.GET)
    public String index(Model model) {
        return "user/user-list";
    }
	
	@RequestMapping(value="/user-add", method=RequestMethod.GET)
    public String userAdd(Model model) {
        return "user/user-add";
    }
	
    @RequestMapping(value="/listUser", method=RequestMethod.GET)
    @Log("查询用户信息")
	public @ResponseBody PageList<User> listUser(Integer start,Integer limit,String query){
		PageParam page = new PageParam(start, limit);
		int count = userService.countListUser(query);
		PageList<User> result = new PageList<>(count, limit);
		if(count>page.getStart())
			result.setList(userService.listUser(page.getStart(), page.getLimit(),query));
		return result;
    }
    
    @RequestMapping(value="/userAdd", method=RequestMethod.POST)
    @Log("添加用户信息")
	public @ResponseBody Integer userAdd(HttpServletRequest request,MultipartFile file,String loginName, 
			String sex,String phone,String address,String email,String introduce,String addr,String qq) throws Exception{
    	User user =saveUserEntity(request,file,loginName,sex, phone, address, email, introduce, addr, qq);
    	userService.userAdd(user);
		return 0;
    }
    
    
    private User saveUserEntity(HttpServletRequest request,MultipartFile file,String loginName, 
			String sex,String phone,String address,String email,String introduce,String addr,String qq) throws Exception{
    	User user = new User();
    	String rootPath = null;
    	if(file !=null){
    		rootPath = request.getServletContext().getRealPath("/images/logo");
    		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    		String fileName = String.valueOf(System.currentTimeMillis())+suffix;
    		File files = new File(rootPath+"/"+fileName);
    		if(!files.exists()) {
    			files.mkdirs();
    		}
//			file.transferTo(files);
    	}
		String openId = UUIDGenerator.getUUID();
		user.setOpenId(openId);
	    user.setGender(sex);
	    user.setLoginName(loginName);
	    user.setPhone(phone);;
	    user.setEmail(email);
	    user.setPassword(EncryptedPassword.generateEncryptedPassword());
	    user.setAvatar(rootPath);
	    user.setIntroduce(introduce);
	    user.setAddr(addr);
	    user.setQq(qq);
		return user;
    }
    
}
