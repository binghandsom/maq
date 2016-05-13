package com.maq.dao;

import org.springframework.stereotype.Repository;

import com.maq.bean.UserInfo;
@Repository
public class UserDao extends CommonDao<UserInfo> {

	@Override
	protected Class<UserInfo> getEntityClass() {
		return UserInfo.class;
	}

}
