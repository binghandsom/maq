package com.maq.bean;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * 用户表
 * @author meihf
 */
public class User {
	/** 登录名*/
	@Id
	private String loginName;
	/** 密码 */
	private String loginPwd;
	/** 昵称*/
	private String nickName;
	/** 邮箱 */
	private String email;
	/** 电话 */
	private String tel;
	/** 权限 0 - Administrator ; 1 - Guest */
	private short role;
	/** 创建时间 */
	private Date createDate;
	/** 最后一次登录时间 */
	private Date lastLoginDate;
	/** 自动登录IP */
	private String autoLoginIP;
	/** 登录IP集合 */
	private List<String> loginIPs;
	/** 是否使用  0 - Del ; 1 - Used */
	private short isUsed;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public short getRole() {
		return role;
	}
	public void setRole(short role) {
		this.role = role;
	}
	public Date getCreateDate() {
		return createDate==null?null:(Date)createDate.clone();
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate==null?null:(Date)createDate.clone();
	}
	public Date getLastLoginDate() {
		return lastLoginDate==null?null:(Date)lastLoginDate.clone();
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate==null?null:(Date)lastLoginDate.clone();
	}
	public String getAutoLoginIP() {
		return autoLoginIP;
	}
	public void setAutoLoginIP(String autoLoginIP) {
		this.autoLoginIP = autoLoginIP;
	}
	public List<String> getLoginIPs() {
		return loginIPs;
	}
	public void setLoginIPs(List<String> loginIPs) {
		this.loginIPs = loginIPs;
	}
	public short getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(short isUsed) {
		this.isUsed = isUsed;
	}
}
