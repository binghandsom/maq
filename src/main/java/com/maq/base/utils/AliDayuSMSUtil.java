package com.maq.base.utils;

import org.apache.commons.lang.RandomStringUtils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliDayuSMSUtil {
	private final static String URL = "http://gw.api.taobao.com/router/rest";
	private final static String APPKEY = "23342306";
	private final static String SECRET = "c65e954cadcc6c819d4b0415ad521a3b";
	private static TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);

	/**
	 * 
	 * @param targetPhoneNum
	 *            接收短信的手机
	 * @return validateCode
	 */
	public static String sendAliDayuMSG(String targetPhoneNum) {
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("大鱼测试");
		String validateCode = generateRandomCode();
		System.out.println(validateCode);
		req.setSmsParamString("{\"code\":\" " + validateCode + "\",\"product\":\"miaiqing\"}");
		req.setRecNum(targetPhoneNum);
		req.setSmsTemplateCode("SMS_7510346");
		// 验证码${code}，您正在登录${product}，若非本人操作，请勿泄露。
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
			System.out.println(rsp.getMsg());
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return validateCode;
	}

	private static String generateRandomCode() {
		return RandomStringUtils.random(6, true, true).toLowerCase();
	}

	public static void main(String[] args) {
		sendAliDayuMSG("13095531769");
	}
}
