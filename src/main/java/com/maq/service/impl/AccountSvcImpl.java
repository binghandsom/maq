package com.maq.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.maq.base.utils.dto.ResponseMessage;
import com.maq.bean.Account;
import com.maq.dao.AccountDao;
import com.maq.service.AccountSvc;

@Service
public class AccountSvcImpl implements AccountSvc {
	@Autowired
	private AccountDao accountDao;
	private Query query;

	public void add(Account account) {
		accountDao.save(account);
	}

	public ResponseMessage loginCheck(Account account,HttpSession session) {
		ResponseMessage rm = new ResponseMessage();
		Map<String, String> rmMap = new HashMap<String, String>();
		String email;
		String password;
		try {
			//捕获空指针异常
			email = account.getEmail();
			password = account.getPassword();
		} catch (Exception ex) {
			rm.setSuccess(false);
			return rm;
		}
		if (email == null || password == null || "".equals(email) || "".equals(password)) {
			rm.setSuccess(false);
			return rm;
		}
		if (email.contains("@")) {
			// 是邮箱
			query = new Query(Criteria.where("email").is(email).andOperator(Criteria.where("password").is(password)));
		} else {
			// 账号是手机
			query = new Query(Criteria.where("phone").is(email).andOperator(Criteria.where("password").is(password)));
		}
		Account account2 = accountDao.queryOne(query);
		if (account2!=null) {
			//将用户信息保存至session
			session.setAttribute("userAccount", account2);
			rm.setSuccess(true);
			rm.setMessage(rmMap);
		}
		return rm;
	}

}
