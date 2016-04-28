package com.maq.service;

import javax.servlet.http.HttpSession;

import com.maq.base.utils.dto.ResponseMessage;
import com.maq.bean.Account;

public interface AccountSvc {
	void add(Account account);

	ResponseMessage loginCheck(Account account, HttpSession session);
}
