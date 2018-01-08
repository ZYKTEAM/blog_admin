package com.myblog.domain;



/**
 * @author:zyk
 * @Description: 用户表
 * @Date:Create in 10:50 2017/11/22
 * @Modified By:
 **/
public class User {
	
	private Long id;
	// 用户唯一身份识别 ID
    private String openId;
    // 性别
    private String gender;
    //用户名
    private String loginName;
    //电话
    private String phone;
    //email
    private String email;
    //密码
    private String password;
    // 昵称
    private String avakName;
    // 头像
    private String avatar;
    //生日
    private String birthday;
    // 其他信息
    private String meta;
    // 用户信息 MD5 值，用于校验用户信息是否休息
    private String md5;
    
    private String loginType;
    //自我简介
    private String introduce;
    //地址
    private String addr;
    //QQ
    private String qq;

    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvakName() {
		return avakName;
	}

	public void setAvakName(String avakName) {
		this.avakName = avakName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    
}
