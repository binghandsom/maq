package com.maq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maq.base.utils.dto.ResponseMessage;
import com.maq.bean.Account;
import com.maq.service.AccountSvc;
import com.maq.service.UserSvc;

@Controller
@RequestMapping("userAccount")
public class AccountController {
	@Autowired
	private UserSvc userSvc;
	@Autowired
	private AccountSvc accountSvc;

	/**
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		// Account account=new Account();
		// account.setEmail("ssss");
		// account.setId("98djfjskdfksd20");
		// accountSvc.add(account);
		return "common/index";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseMessage register(Account account, HttpSession session,Model model) {

		ResponseMessage rm = accountSvc.register(account,session);

		return rm;
	}

	@RequestMapping("index")
	public String index() {
		if (1 == 2) {
			// session中有保存有用户账号
			return "common/index";
		} else {
			// session中没有保存有用户账号
			return "userAccount/regAndLogin";
		}
	}

	@RequestMapping(value = "loginCheck", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage loginCheck(Account account, HttpSession session) {
		System.out.println(account);
		// 如果此用户登录成功则将用户账号信息保存在session中，service层中以做此保存操作
		ResponseMessage rm = accountSvc.loginCheck(account, session);
		System.out.println(rm);
		return rm;

	}

	/**
	 * 
	 * @param phoneNum
	 * @param email
	 * @param reason
	 *            发送验证码的原因，可以是注册，也可以是改密
	 * @param session
	 *            将验证码保存起来
	 */
	@RequestMapping(value = "sendRegValidateCode", method = RequestMethod.POST)
	public void sendValidateCode(String phone, String email, String reason, HttpSession session) {
		accountSvc.sendValidateCode(phone, email, reason, session);
	}

}
