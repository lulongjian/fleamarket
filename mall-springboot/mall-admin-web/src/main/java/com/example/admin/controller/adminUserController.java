package com.example.admin.controller;

import com.example.admin.service.AdminUserService;
import com.example.common.utils.JsonModel;
import com.example.mall.domain.UserDO;
import com.example.mall.domain.form.UserForm;
import com.example.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
@RequestMapping("/admin/user")
public class adminUserController {

	@Autowired
	private AdminUserService adminUserService;

	@Autowired
	private UserService userService;

	/**
	 * 管理员获取用户信息
	 *
	 * @return
	 */
	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	public JsonModel getUser(Integer page, Integer number, UserForm userForm) {
		JsonModel jsonModel = new JsonModel();
		try {
			jsonModel = adminUserService.getProdut(page, number, userForm);
			jsonModel.setMsg("加载成功");
		}catch (Exception e){
			jsonModel.setMsg("网络错误！请刷新重试");
		}
		return jsonModel;
	}

	/**
	 * 冻结用户
	 *
	 * @return
	 */
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
	public JsonModel deleteUser(@PathVariable Integer id) {
		JsonModel jsonModel = new JsonModel();
		adminUserService.deleteUserById(id);
		jsonModel.setMsg("该会员已被冻结");
		return jsonModel;
	}
	/**
	 * 解冻用户
	 *
	 * @return
	 */
	@RequestMapping(value = "/resetUser/{id}", method = RequestMethod.GET)
	public JsonModel resetUser(@PathVariable Integer id) {
		JsonModel jsonModel = new JsonModel();
		adminUserService.updateUserPermissionById(id);
		jsonModel.setMsg("该会员已解冻");
		return jsonModel;
	}

	/**
	 * 管理员新增用户
	 *
	 * @return
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public JsonModel addUser(UserForm userForm) {
		//调用注册的方法
		UserDO userDO = new UserDO();
		userDO.setSno(userForm.getSno());
		userDO.setName(userForm.getName());
		userDO.setRole(userForm.getRole());
		userDO.setEmail(userForm.getEmail());
		userDO.setPhone(userForm.getPhone());
		userDO.setPassword("123456");
		JsonModel jsonModel = userService.addUser(userDO);
		if (jsonModel.getCode() != 0) {
			//添加错误
			return jsonModel;
		}
		//调用授权的放大
		//查找新增用户id
		UserDO newUser = adminUserService.getUserIdByUserName(userForm);
		adminUserService.updateUserPermissionById(newUser.getId());
		jsonModel.setMsg("会员" + userForm.getName() + "添加成功");
		return jsonModel;
	}

	/**
	 * 修改用户
	 *
	 * @return
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public JsonModel updateUser(UserForm userForm) {
		JsonModel jsonModel = adminUserService.updateUserByUserForm(userForm);
		return jsonModel;
	}

	/**
	 * 重置密码
	 *
	 * @return
	 */
	@RequestMapping(value = "/passwordReset/{id}", method = RequestMethod.GET)
	public JsonModel passwordReset(@PathVariable Integer id) {
		JsonModel jsonModel = new JsonModel();
		adminUserService.updateUserPasswordById(id);
		jsonModel.setMsg("的密码已重置成功");
		return jsonModel;
	}

	/**
	 * 授权登陆系统
	 *
	 * @return
	 */
	@RequestMapping(value = "/getPermission/{id}", method = RequestMethod.GET)
	public JsonModel getPermission(@PathVariable Integer id) {
		JsonModel jsonModel = new JsonModel();
		adminUserService.updateUserPermissionById(id);
		jsonModel.setMsg("授权成功");
		return jsonModel;
	}

	/**
	 * 冻结用户通过ids
	 *
	 * @return
	 */
	@RequestMapping(value = "/deleteUser/{ids}", method = RequestMethod.DELETE)
	public JsonModel deleteUserByIds(@PathVariable String ids) {
		String[] idArrays = ids.split(",");
		JsonModel jsonModel = new JsonModel();
		for (int i = 0 ; i< idArrays.length; i++){
			adminUserService.deleteUserById(Integer.parseInt(idArrays[i]));
		}
		return jsonModel;
	}

	/**
	 * 授权用户通过ids
	 *
	 * @return
	 */
	@RequestMapping(value = "/authUser/{ids}", method = RequestMethod.GET)
	public JsonModel AuthUser(@PathVariable String ids) {
		String[] idArrays = ids.split(",");
		JsonModel jsonModel = new JsonModel();
		for (int i = 0 ; i< idArrays.length; i++){
			adminUserService.updateUserPermissionById(Integer.parseInt(idArrays[i]));
		}
		return jsonModel;
	}
}
