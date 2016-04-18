package com.maq.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maq.bean.User;
import com.maq.service.UserSvc;

@Controller
public class LoginController {
	@Autowired
	private UserSvc userSvc;
	
	/**
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request){
		User user = userSvc.getByName("meihf");
		System.out.println(user.getLoginName());
		request.setAttribute("usr", user);
		return "userAccount/login";
	}
}
