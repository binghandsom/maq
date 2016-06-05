package com.maq.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.maq.base.utils.dto.ResponseMessage;
import com.maq.bean.UserMainInfo;
import com.maq.dao.UserMainInfoDao;
import com.maq.service.UserMainInfoSvc;

@Service
public class UserMainInfoSvcImpl implements UserMainInfoSvc {
	@Autowired
	private UserMainInfoDao mainInfoDao;
	private Query query;

	/**
	 * 检查昵称是否已经被占用
	 */
	public ResponseMessage checkNickName(String nickName) {
		query = new Query();
		ResponseMessage rm = new ResponseMessage();
		Object objTheSame = mainInfoDao.queryOne(query.addCriteria(Criteria.where("nickName").is(nickName)));
		if (objTheSame != null) {
			rm.setSuccess(false);
		} else {
			rm.setSuccess(true);
		}
		return rm;
	}

	public void save(UserMainInfo baseInfo) {
		mainInfoDao.save(baseInfo);
	}

}
