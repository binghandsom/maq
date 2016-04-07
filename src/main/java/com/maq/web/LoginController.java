package com.maq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	/**
	 * @return
	 */
	@RequestMapping("login")
	public String login(){
		return "userAccount/login";
	}
}
