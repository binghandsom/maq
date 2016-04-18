package com.maq.dao;

import org.springframework.stereotype.Repository;

import com.maq.bean.User;
@Repository
public class UserDao extends CommonDao<User> {

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

}
