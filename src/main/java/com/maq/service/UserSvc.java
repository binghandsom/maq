package com.maq.service;

import com.maq.bean.UserInfo;

public interface UserSvc {

	public UserInfo getByName(String username);
}
