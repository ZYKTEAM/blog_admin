package com.myblog.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.collect.Lists;
import com.myblog.domain.User;
import com.myblog.service.UserService;

public class CustomUserDetailsService implements UserDetailsService{	
	
	/*@Autowired
	private PermissionService permissionService;*/
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		User user = userService.getUserByAccount(account);
		if(user.getId() == 0){
			throw new UsernameNotFoundException("account \"" + account + "\" not found");
		}
		List<GrantedAuthority> authorities = Lists.newArrayList();
		/*if(!user.getAccount().equalsIgnoreCase("admin")){
			List<Permission> permissions = permissionService.findPermissions(user.getId(),"1.");
			Collections.reverse(permissions);
			authorities.addAll(permissions);
		}*/
		return new SecurityUser(user, authorities);
	}
}
