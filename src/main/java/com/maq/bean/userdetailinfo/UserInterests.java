package com.maq.bean.userdetailinfo;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * 
 * --^.^-- @author 王兵兵（QQ--1435489083） @date 2016年5月30日 下午9:53:55 --^.^--
 * --------------------------------------------------------------------------
 * Description:兴趣类
 * --------------------------------------------------------------------------
 */
public class UserInterests {
	@Id
	private String id;// 用户id
	// 2.兴趣 ：1运动2活动（电影/聊天/逛街。。。）3音乐类型4美食（甜食。。。）5影视类型6宠物（狗/猫/鸡）7最想去的地方
	private List<String> sports;// 喜爱的运动
	private List<String> activities;// 喜爱的活动
	private List<String> places;// 想去的地方
	private List<String> pets;// 喜爱的宠物
	private List<String> musics;// 喜爱的音乐
	private List<String> foods;// 喜爱的食物
	private List<String> films;// 喜爱的影视

}
