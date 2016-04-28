package com.maq.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public ResponseMessage loginCheck(Account account, HttpSession session) {
		ResponseMessage rm = new ResponseMessage();
		Map<String, String> rmMap = new HashMap<String, String>();
		String email;
		String password;
		try {
			// 捕获空指针异常
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
		if (account2 != null) {
			// 将用户信息保存至session
			session.setAttribute("userAccount", account2);
			rm.setSuccess(true);
			rm.setMessage(rmMap);
		}
		return rm;
	}

	public ResponseMessage sendValidateCode(String phone, String email, String reason, HttpSession session) {
		ResponseMessage message = new ResponseMessage();
		// 存放某手机或邮箱的验证码和验证码产生的毫秒数 object[0] 验证码，object[1]时间
		Map<String, Object[]> valiCodeTimeMap = (Map<String, Object[]>) session.getAttribute("valiCodeTimeMap");
		if (valiCodeTimeMap == null) {
			valiCodeTimeMap = new HashMap<String, Object[]>();
		}
		// 在发送完验证码后将验证码保存在session中，
		Object[] preValiCodeAndTime = new Object[2];
		String validateCode = "";
		if (phone != null) {
			String regRule = "/^(1)\\d{10}$/";
			Pattern p = Pattern.compile(regRule);
			Matcher m = p.matcher(phone);
			if (!m.matches()) {
				// 如果手机号码格式不正确
				message.setSuccess(false);
				return message;
			}
			if (valiCodeTimeMap.containsKey(phone)) {
				// 获取该entry的value
				preValiCodeAndTime = valiCodeTimeMap.get(phone);
				if (preValiCodeAndTime != null) {
					long preTime = (Long) preValiCodeAndTime[1];
					// 如果验证码发送未超过60秒不允许再次请求验证，如果不限制时间，可能会出现机器盗号现象
					if (System.currentTimeMillis() - preTime < 1000 * 60) {
						// 让请求失败
						message.setSuccess(false);
						// 可以在message中增加信息，这样前端页面反馈给用户不一样的信息，增强用户体验
						return message;
					}
				}
			}

			if ("register".equals(reason)) {
				// TODO 发送短信注册验证码
				/**
				 * 产生验证码并发送
				 */
				validateCode = "验证码XXXXX";

			} else if ("changePass".equals(reason)) {
				// TODO 发送短信改密验证码
				/**
				 * 产生验证码并发送
				 */

			}
			validateCode = "验证码XXXXX";
			Object[] codeAndTime = { validateCode, System.currentTimeMillis() };
			valiCodeTimeMap.put(phone, codeAndTime);

			session.setAttribute("valiCodeTimeMap", valiCodeTimeMap);

		} else if (email != null) {
			String regRule = "/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,9})$/";
			Pattern p = Pattern.compile(regRule);
			Matcher m = p.matcher(email);
			if (!m.matches()) {
				// 如果邮箱格式不正确
				message.setSuccess(false);
				return message;
			}
			preValiCodeAndTime = (Object[]) session.getAttribute("valiCodeTimeMap");

			if (valiCodeTimeMap.containsKey(email)) {
				// 获取该entry的value
				preValiCodeAndTime = valiCodeTimeMap.get(email);
				if (preValiCodeAndTime != null) {
					long preTime = (Long) preValiCodeAndTime[1];
					// 如果验证码发送未超过60秒不允许再次请求验证，如果不限制时间，可能会出现机器盗号现象
					if (System.currentTimeMillis() - preTime < 1000 * 60) {
						// 让请求失败
						message.setSuccess(false);
						// 可以在message中增加信息，这样前端页面反馈给用户不一样的信息，增强用户体验
						return message;
					}
				}
			}
			if ("register".equals(reason)) {
				// TODO 发送邮箱注册验证码
				validateCode = "验证码XXXXX";
			} else if ("changePass".equals(reason)) {
				// TODO 发送邮箱改密验证码
				validateCode = "验证码XXXXX";
			}
			Object[] codeAndTime = { validateCode, System.currentTimeMillis() };
			valiCodeTimeMap.put(phone, codeAndTime);
			session.setAttribute("valiCodeTimeMap", valiCodeTimeMap);
		}
		System.out.println("XXX");
		return message;

	}

	// TODO
	public ResponseMessage register(Account account, HttpSession session) {
		ResponseMessage rm = new ResponseMessage();
		String email = account.getEmail();
		String phone = account.getPhone();
		boolean b = false;
		String regRule = "";
		if (email != null) {
			// 验证格式账号，不正确的格式不继续执行
			regRule = "/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,9})$/";
		} else if (phone != null) {
			// 验证格式账号，不正确的格式不继续执行
			regRule = "/^(1)\\d{10}$/";
		}
		// ①构造一个模式.
		Pattern p = Pattern.compile(regRule);
		// ②建造一个匹配器
		Matcher m = p.matcher(phone);
		// ③进行判断，得到结果
		b = m.matches();
		if (!b) {
			rm.setSuccess(false);
			return rm;
		}
		// 查找是否已存在该注册号码/邮箱，存在则不能注册
		if (email != null) {
			query = new Query(Criteria.where("email").is(email));
		} else if (phone != null) {
			query = new Query(Criteria.where("phone").is(phone));
		}
		Account account2 = accountDao.queryOne(query);
		if (account2 != null) {
			// 存在
			rm.setSuccess(false);
		} else {
			// 不存在该账号，继续注册
			account.setDel(false);
			accountDao.save(account);
			rm.setSuccess(true);
			session.setAttribute("account", account);
		}
		return rm;
	}

}
