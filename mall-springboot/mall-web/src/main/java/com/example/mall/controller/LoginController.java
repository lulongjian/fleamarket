package com.example.mall.controller;

import com.example.common.utils.Constant;
import com.example.common.utils.JsonModel;
import com.example.common.utils.RASEncrypt;
import com.example.mall.domain.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("sys")
@Slf4j
public class LoginController {

	/**
	 *登陆逻辑处理
	 */
	@RequestMapping(value="login", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel login(String userName, String passWord, HttpSession session){
		String loginName = userName;
		JsonModel jsonModel = new JsonModel();
		log.info("用户: "+userName+"正在登陆中.....");
		if(loginName==null){
			return jsonModel;
		}
		try {
			passWord = RASEncrypt.decrypt(passWord, Constant.PRIVATEKRY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 使用Shiro编写认证操作
		 */
		//1.获取subject
		Subject subject = SecurityUtils.getSubject();

		//2.封装用户数据
		char[] passwordChar = passWord.toCharArray();
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(loginName);
		token.setPassword(passwordChar);
		//3.执行登陆方法
		try {
			subject.login(token);
			//登陆成功
			//查询该用户的角色
			UserDO user = (UserDO)subject.getPrincipal();
			session.setAttribute("loginName", loginName);
			session.setAttribute("name", user.getName());
			session.setAttribute("userId", user.getId());

			if(user.getPassword().equals("0")){
				jsonModel.setCode(1);
				jsonModel.setMsg("用户还没有授权登陆");
				return jsonModel;
			}
	        jsonModel.setCode(0);
			jsonModel.setMsg("成功");
			log.info("用户: "+userName+"登陆成功");
			/**
			 * 返回mapp数据
			 */
			Map<String, Object> map = new HashMap<>();
			map.put("user", user);
			map.put("userName",user.getName());
			jsonModel.setData(map);
			return jsonModel;
		} catch (UnknownAccountException e) {
			//登陆失败：用户名不存在
			jsonModel.setMsg("用户名不存在!");
			jsonModel.setCode(1);
			log.info("用户: "+userName+"不存在，注册才可以使用");
			return jsonModel;
		} catch (IncorrectCredentialsException e){
			//登陆失败：密码错误
			jsonModel.setCode(1);
			jsonModel.setMsg("密码错误!");
			log.info("用户: "+userName+"输入的密码错误！！");
			return jsonModel;
		}
	}

	/**
	 *判断是否已经存在登陆账号
	 */
	@RequestMapping("login")
	public String toLogin(HttpSession session){

		//判断是否已经存在登陆账号
		if(session.getAttribute("loginName")!=null){
			return "redirect:/sys/index";
		}
		return "sys/login";
	}

	/**
	 *退出登陆逻辑处理
	 */
	@RequestMapping(value="logout", method= RequestMethod.GET)
	public String logout(){
		//1.获取subject
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		log.info("用户: "+subject.getSession().getAttribute("loginName")+"退出登陆");
		return "redirect:/sys/login";
	}

}
