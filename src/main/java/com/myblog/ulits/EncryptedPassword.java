package com.myblog.ulits;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPassword {
	
	private static final String DEFAULT_PASSWORD = "123456";
	
	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
	
	public static String generateEncryptedPassword(String password){
	     return passwordEncoder.encode(password);
	}
	
	public static String generateEncryptedPassword(){
	     return passwordEncoder.encode(DEFAULT_PASSWORD);
	}
	
	/**
	 * 
	 * @param rawPassword: 输入时的密码
	 * @param encodedPassword: 登录用户密码
	 */
	public static boolean checkPassword(String rawPassword, String encodedPassword){
		 return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	public static void main(String[] args) {
		System.out.println(generateEncryptedPassword());
		System.out.println(checkPassword("123456","$2a$10$6LZEa1NVFMCQsifldN0wm.KLUyMpVq9MUzCcxc3EdewFsmYJm0KCy"));
	}
	
}
