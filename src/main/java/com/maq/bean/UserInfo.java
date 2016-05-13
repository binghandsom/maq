package com.maq.bean;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * 
 * --^.^--	@author 王兵兵（QQ--1435489083） @date 2016年5月13日 下午4:12:49  --^.^--
 * --------------------------------------------------------------------------	
 * Description:用户信息 
 * --------------------------------------------------------------------------
 */
public class UserInfo {
	@Id
	private String id;// 用户id
	/** 昵称 */
	private String nickName;
	/** 创建时间 */
	private Date createDate;
	/** 最后一次登录时间 */
	private Date lastLoginDate;

	/** 是否使用 0 - Del ; 1 - Used */

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateDate() {
		return createDate == null ? null : (Date) createDate.clone();
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate == null ? null : (Date) createDate.clone();
	}

	public Date getLastLoginDate() {
		return lastLoginDate == null ? null : (Date) lastLoginDate.clone();
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate == null ? null : (Date) lastLoginDate.clone();
	}

}
