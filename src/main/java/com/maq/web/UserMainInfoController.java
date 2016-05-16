package com.maq.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.maq.base.utils.DateUtils;
import com.maq.base.utils.ImageUtil;
import com.maq.base.utils.dto.ResponseMessage;
import com.maq.bean.Account;
import com.maq.bean.UserMainInfo;
import com.maq.service.UserMainInfoSvc;

/**
 * 
 * --^.^-- @author 王兵兵（QQ--1435489083） @date 2016年5月13日 下午3:49:06 --^.^--
 * --------------------------------------------------------------------------
 * Description:用户信息视图控制层
 * --------------------------------------------------------------------------
 */
@Controller
@RequestMapping("userMainInfo")
public class UserMainInfoController {

	@Autowired
	private UserMainInfoSvc umiSvc;

	@RequestMapping("editMainInfo")
	public String editMainInfo() {
		// 编辑基本信息
		return "userInfo/editMainInfo";
	}

	@ResponseBody
	@RequestMapping(value = "checkNickName", method = RequestMethod.POST)
	public ResponseMessage checkNickName(String nickName) {
		ResponseMessage rm = umiSvc.checkNickName(nickName);
		return rm;
	}

	@ResponseBody
	@RequestMapping("loadPhoto")
	public void loadPhoto(String picName, HttpServletResponse response, HttpServletRequest request, String picType)
			throws IOException {
		String fileAbsolutePath = "";
		if (ImageUtil.TEMP_PICTURE.equals(picType)) {
			fileAbsolutePath = request.getSession().getServletContext()
					.getRealPath("files/picture/userHeadPictures_temp") + "/" + picName;
			System.out.println(fileAbsolutePath);
		} else if (ImageUtil.HEAD_PICTURE.equals(picType)) {
			fileAbsolutePath = request.getSession().getServletContext().getRealPath("userHeadPictures") + "/" + picName;
		}
		FileInputStream hFile = new FileInputStream(fileAbsolutePath);
		ImageUtil.photoOutStreamWriting(response, hFile);
	}

	@ResponseBody
	@RequestMapping(value = "isPictureFile", method = RequestMethod.POST)
	public ResponseMessage isPictureFile(@RequestParam(value = "headPic", required = true) MultipartFile headPic,
			HttpSession session) {
		ResponseMessage rm = new ResponseMessage();
		Map<String, String> failReason = new HashMap<String, String>();
		String path = session.getServletContext().getRealPath("files/picture/userHeadPictures_temp");
		Account account = new Account();
		account.setId("xxx");
		session.setAttribute("account", account);
		/**
		 * 真实环境中session是有account的
		 */
		String fileName = ((Account) session.getAttribute("account")).getId() + ".jpg";
		if (session.getAttribute("headPicPath") != null) {
			String preFile = session.getAttribute("headPicPath").toString();
			// 当用户第二次之后点击选择图像时，删除上一次的文件。防止服务器垃圾文件产生
			File f = new File(preFile); // 输入要删除的文件位置
			if (f.exists()) {
				f.delete();
			}
		}
		session.setAttribute("headPicPath", path + "/" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			headPic.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!ImageUtil.isImageFile(targetFile)) {
			rm.setSuccess(false);
			failReason.put("reason", "不是图片文件");
			rm.setMessage(failReason);
		} else if (targetFile.length() > 6 * 1024 * 1024) {
			rm.setSuccess(false);
			failReason.put("reason", "图片文件大小不可超过6Mb，请检查");
			rm.setMessage(failReason);
		} else {
			Map<String, String> okMap = new HashMap<String, String>();
			okMap.put("picName", fileName);
			rm.setMessage(okMap);
			rm.setSuccess(true);
		}
		System.out.println(rm.toString());
		return rm;
	}

	@RequestMapping(value = "doEditMainInfo", method = RequestMethod.POST)
	public String doEditMainInfo(HttpSession session, ModelMap modelM, String birthDay, String constellation,
			String nickName, int gender, int marriage, String declaration, int height, int salary) {
		String headPicPath = session.getAttribute("headPicPath").toString();
		File headPic = new File(headPicPath);
		UserMainInfo baseInfo = new UserMainInfo();
		Date _birthDay = DateUtils.strToDate(birthDay, DateUtils.CH_DATE_FORMATE_STR);

		baseInfo.setNickName(nickName);
		baseInfo.setHeight(height);
		baseInfo.setBirthDay(_birthDay);
		baseInfo.setConstellation(constellation);
		baseInfo.setCreateDate(new Date());
		baseInfo.setDeclaration(declaration);
		baseInfo.setGender(gender);
		baseInfo.setSalary(salary);

		System.out.println(baseInfo);

		String path = session.getServletContext().getRealPath("files/picture/userHeadPictures");
		Account account = new Account();
		account.setId("xxx");
		session.setAttribute("account", account);
		/**
		 * 真实环境中session是有account的
		 */
		String fileName = ((Account) session.getAttribute("account")).getId() + new Date().getTime() + ".jpg";
		System.out.println(fileName);
		File targetFile = new File(path);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存将temp文件移动到正规文件中
		try {
			headPic.renameTo(new File(targetFile, fileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
		baseInfo.setHeadPicPath(path + File.separator + fileName);

		umiSvc.save(baseInfo);

		return "userInfo/editMainInfo";
	}

	@RequestMapping("editDetailInfo")
	public String editDetailInfo() {
		// 编辑详细信息
		return "userInfo/editDetailInfo";
	}
}
