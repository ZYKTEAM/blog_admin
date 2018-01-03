package com.myblog.ulits;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Lists;
import com.myblog.domain.Permission;
import com.myblog.domain.User;
import com.myblog.security.SecurityUser;

public class UserUtil {

	public static User getUser(){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUser();
	}
	
	public static List<Permission> getPermissions(){
		 Collection<GrantedAuthority> authorities = ((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
		 List<Permission> permissions = Lists.newArrayList();
		 if(authorities !=null && authorities.size()>0){
			 for (GrantedAuthority grantedAuthority : authorities) {
				 permissions.add((Permission)grantedAuthority);
			 }
		 }
		 return permissions;
	}
}
