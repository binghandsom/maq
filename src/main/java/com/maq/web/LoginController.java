package com.maq.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maq.bean.Account;
import com.maq.bean.User;
import com.maq.dao.AccountDao;
import com.maq.service.AccountSvc;
import com.maq.service.UserSvc;

@Controller
public class LoginController {
	@Autowired
	private UserSvc userSvc;
	@Autowired
	private AccountSvc accountSvc;
	/**
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request){
//		Account account=new Account();
//		account.setEmail("ssss");
//		account.setId("98djfjskdfksd20");
//		accountSvc.add(account);
		return "common/index";
	}
}
