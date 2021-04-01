package com.example.mall.controller;

import com.example.common.utils.Constant;
import com.example.common.utils.JsonModel;
import com.example.common.utils.RASEncrypt;
import com.example.common.utils.redis.RedisUtils;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.UserDO;
import com.example.mall.service.UserService;
import com.example.mall.utils.EmailCodeMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

//	@Autowired
//	private RedisUtils redisUtils;

	/**
	 * 用户注册
	 */
	@RequestMapping(value="add", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel login(UserDO userDO, String code){
		JsonModel jsonModel = new JsonModel();
		//判断验证码是否正确/或者过时
//		System.out.println(EmailCodeMapUtil.emailCodeMap.get(userDO.getEmail()));
		if(EmailCodeMapUtil.emailCodeMap.get(userDO.getEmail()) == null){
			jsonModel.setCode(1);
			jsonModel.setMsg("验证码已过时请重新获取");
			return jsonModel;
		}
		if(EmailCodeMapUtil.emailCodeMap.get(userDO.getEmail()) != null && !EmailCodeMapUtil.emailCodeMap.get(userDO.getEmail()).equals(code)){
			jsonModel.setCode(1);
			jsonModel.setMsg("验证码有误，请重新输入");
			return jsonModel;
		}
		userDO.setRole("普通用户");
		jsonModel = userService.addUser(userDO);
		return jsonModel;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value="updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel update(String email, String passWord, String code){
		JsonModel jsonModel = new JsonModel();
		//判断时候有这个用户
		UserDO userDO = userService.findUserByEmail(email);
		if (userDO == null){
			jsonModel.setCode(1);
			jsonModel.setMsg("邮箱输入错误，找不到该用户！");
			return jsonModel;
		}
		//判断验证码是否正确/或者过时
		if(EmailCodeMapUtil.emailCodeMap.get(email) == null){
			jsonModel.setCode(1);
			jsonModel.setMsg("验证码已过时请重新获取");
			return jsonModel;
		}
		if(EmailCodeMapUtil.emailCodeMap.get(email) != null && !EmailCodeMapUtil.emailCodeMap.get(email).equals(code)){
			jsonModel.setCode(1);
			jsonModel.setMsg("验证码有误，请重新输入");
			return jsonModel;
		}
		try {
			userDO.setPassword(passWord);
			userService.updatePassword(userDO);
			jsonModel.setMsg("修改成功");
		}catch (Exception e){
			jsonModel.setCode(1);
			jsonModel.setMsg("验证码有误，请重新输入");
		}
		return jsonModel;
	}
	/**
	 *查找个人信息
	 */
	@RequestMapping(value="findUser", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel findUser(String userId){
		JsonModel jsonModel = new JsonModel();
		UserDO userDO = userService.findUser(userId);
		userDO.setPassword("");
		jsonModel.setCode(0);
		jsonModel.setMsg("查看成功");
		jsonModel.setData(userDO);
		return jsonModel;
	}

	/**
	 *更新个人信息
	 */
	@RequestMapping(value="updateUser", method = RequestMethod.PUT)
	@ResponseBody
	public JsonModel updateUser(UserDO userDO){
		JsonModel jsonModel = new JsonModel();
		//校验参数 todo
		userService.updateUser(userDO);
		jsonModel.setCode(0);
		jsonModel.setMsg("修改成功");
		return jsonModel;
	}
}
