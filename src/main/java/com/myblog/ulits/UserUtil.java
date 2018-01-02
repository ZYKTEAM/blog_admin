package com.myblog.ulits;

import org.springframework.security.core.context.SecurityContextHolder;

import com.myblog.domain.User;
import com.myblog.security.SecurityUser;

public class UserUtil {

	public static User getUser(){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUser();
	}
	
	/*public static List<Permission> getPermissions(){
		 Collection<GrantedAuthority> authorities = ((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
		 List<Permission> permissions = Lists.newArrayList();
		 if(authorities !=null && authorities.size()>0){
			 for (GrantedAuthority grantedAuthority : authorities) {
				 permissions.add((Permission)grantedAuthority);
			 }
		 }
		 return permissions;
	}*/
}
