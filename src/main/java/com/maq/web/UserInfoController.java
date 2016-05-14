package com.maq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * --^.^-- @author 王兵兵（QQ--1435489083） @date 2016年5月13日 下午3:49:06 --^.^--
 * --------------------------------------------------------------------------
 * Description:用户信息视图控制层
 * --------------------------------------------------------------------------
 */
@Controller
@RequestMapping("userInfo")
public class UserInfoController {

	@RequestMapping("editMainInfo")
	public String editMainInfo() {
		// 编辑基本信息
		return "userInfo/editMainInfo";
	}

	@RequestMapping("editDetailInfo")
	public String editDetailInfo() {
		// 编辑详细信息
		return "userInfo/editDetailInfo";
	}
}
