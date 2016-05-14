package com.maq.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maq.bean.Account;

@Controller
@RequestMapping("common")
public class CommonController {

	@RequestMapping("index")
	public String index(HttpSession session) {
		Account account = (Account) session.getAttribute("userAccount");
		if (account != null) {
			System.out.println(account);
			return "common/index";
		}
		return "userAccount/regAndLogin";
	}

}
