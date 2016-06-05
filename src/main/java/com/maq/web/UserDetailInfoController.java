package com.maq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("detailInfos")
public class UserDetailInfoController {
	/*
	 * 编辑兴趣
	 */
	@RequestMapping("editInterests")
	public String interests() {
		return "userInfo/detailInfos/editInterests";
	}

	/*
	 * 编辑照片
	 */
	@RequestMapping("editPhotos")
	public String photoList() {
		System.out.println("#########");
		return "userInfo/detailInfos/editPhotos";
	}

	@RequestMapping("editLivingCondition")
	public String livingCondition() {
		return "userInfo/detailInfos/editLivingCondition";
	}

	@RequestMapping("editWorkingCondition")
	public String workingCondition() {
		return "userInfo/detailInfos/editWorkingCondition";
	}

	@RequestMapping("editLoveAttitude")
	public String editLoveAttitude() {
		return "userInfo/detailInfos/editLoveAttitude";
	}

	@RequestMapping("editMatingPreference")
	public String editMatingPreference() {
		return "userInfo/detailInfos/editMatingPreference";
	}

}
