package com.maq.service;

import com.maq.base.utils.dto.ResponseMessage;
import com.maq.bean.UserMainInfo;

public interface UserMainInfoSvc {

	ResponseMessage checkNickName(String nickName);

	void save(UserMainInfo baseInfo);
	
}
