package com.maq.dao;

import org.springframework.stereotype.Repository;

import com.maq.bean.Account;

@Repository
public class AccountDao extends CommonDao<Account> {
	@Override
	protected Class<Account> getEntityClass() {
		return Account.class;
	}

}
