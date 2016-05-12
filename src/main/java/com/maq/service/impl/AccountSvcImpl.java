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

import com.maq.base.utils.AliDayuSMSUtil;
import com.maq.base.utils.dto.ResponseMessage;
import com.maq.bean.Account;
import com.maq.dao.AccountDao;
import com.maq.service.AccountSvc;

@Service
public class AccountSvcImpl implements AccountSvc {
	@Autowired
	private AccountDao accountDao;
	private Query query;
	private Map<String, Object[]> valiCodeTimeMap;

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
		valiCodeTimeMap = (Map<String, Object[]>) session.getAttribute("valiCodeTimeMap");
		Map<String, String> messageMap = new HashMap<String, String>();
		if (valiCodeTimeMap == null) {
			valiCodeTimeMap = new HashMap<String, Object[]>();
		}
		// 在发送完验证码后将验证码保存在session中，
		Object[] preValiCodeAndTime = new Object[2];
		String validateCode = "";
		if (phone != null) {
			String regRule = "^[1][0-9]{10}$";
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
				// 发送短信注册验证码
				/**
				 * 产生验证码并发送
				 */
				validateCode = AliDayuSMSUtil.sendAliDayuMSG(phone, AliDayuSMSUtil.SIGNNAME_REG);

			} else if ("changePass".equals(reason)) {
				// 发送短信改密验证码
				/**
				 * 产生验证码并发送
				 */
				validateCode = AliDayuSMSUtil.sendAliDayuMSG(phone, AliDayuSMSUtil.SIGNNAME_CHANGE_PASSWORD);
			}

			message.setSuccess(true);

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
			valiCodeTimeMap.put(email, codeAndTime);
			session.setAttribute("valiCodeTimeMap", valiCodeTimeMap);
		}
		return message;

	}

	public ResponseMessage register(Account account, HttpSession session) {
		ResponseMessage rm = new ResponseMessage();
		
		if (accountDao.isUsed(account)) {
			// 存在,号码、邮箱已经被注册过
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

	public boolean accountAvailable(Account account, HttpSession session, String valiCode) {
		// 判断账号可用不可用是根据session中是否有改账号来决定，因为注册前获取验证码成功后将账号放到了session中，如果session中没有此账号，则不予注册
		// 如果session中有那个账号，再验证验证码是否一致。两者一致才继续注册。否则不予注册
		// 判断验证码是否过期，过期不予注册
		if (valiCodeTimeMap == null || valiCodeTimeMap.size() == 0) {
			return false;
		} else {
			String phone = account.getPhone();
			String email = account.getEmail();
			long currentMil = System.currentTimeMillis();
			// 产生验证码的时间
			long beforeMil = 0L;
			if (valiCodeTimeMap.containsKey(phone)) {
				Object[] array = valiCodeTimeMap.get(phone);
				beforeMil = Long.parseLong((String) array[1]);
				if (currentMil - beforeMil > 60 * 1000) {
					return false;
				}
				if (array[0].toString().equalsIgnoreCase(valiCode)) {
					return true;
				}
			} else if (valiCodeTimeMap.containsKey(email)) {
				Object[] array = valiCodeTimeMap.get(email);
				beforeMil = Long.parseLong((String) array[1]);
				if (currentMil - beforeMil > 60 * 1000) {
					return false;
				}
				if (array[0].toString().equalsIgnoreCase(valiCode)) {
					return true;
				}
			}
			return false;
		}
	}

}
