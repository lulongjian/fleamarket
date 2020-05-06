package com.example.admin.controller;

import com.example.admin.service.AdminUserService;
import com.example.common.utils.Constant;
import com.example.common.utils.JsonModel;
import com.example.admin.domain.vo.SysMsgVO;
import com.example.admin.service.AdminSysService;
import com.example.common.utils.RASEncrypt;
import com.example.mall.domain.UserDO;
import com.example.mall.domain.form.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/sys")
@Slf4j
public class adminSysController {

	@Autowired
	private AdminSysService adminSysService;

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 管理员首页获取信息
	 * @return
	 */
	@RequestMapping(value="getSystemData", method= RequestMethod.POST)
	public JsonModel getSystemData(){
		JsonModel jsonModel = new JsonModel();
		SysMsgVO sysMsgVO = adminSysService.getSystemData();
		jsonModel.setData(sysMsgVO);
		return jsonModel;
	}

	/**
	 *登陆逻辑处理
	 */
	@RequestMapping(value="login", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel login(String userName, String passWord, HttpSession session){
		JsonModel jsonModel = new JsonModel();
		//判断是否为系统管理员
		UserForm userForm = new UserForm();
		userForm.setEmail(userName);
		UserDO queryUser = adminUserService.getUserIdByUserName(userForm);
		if(queryUser != null &&!queryUser.getRole().equals("管理员")){
			jsonModel.setCode(200);
			jsonModel.setMsg("您不是管理员，暂无法登录");
			return jsonModel;
		}
		String loginName = userName;
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
			jsonModel.setMsg("密码错误!");
			jsonModel.setCode(1);
			log.info("用户: "+userName+"输入的密码错误！！");
			return jsonModel;
		}
	}
}
