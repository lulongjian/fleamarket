package com.example.admin.service;

import com.example.common.utils.Constant;
import com.example.common.utils.RASEncrypt;
import com.example.mall.dao.AccountMapper;
import com.example.mall.dao.OrderMapper;
import com.example.mall.domain.form.UserForm;
import com.example.mall.domain.vo.UserVO;
import com.example.common.utils.JsonModel;
import com.example.mall.dao.AddressMapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.UserDO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
@Transactional
public class AdminUserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private OrderMapper orderMapper;

	/**
	 * 管理员获取用户信息
	 *
	 * @param page
	 * @param number
	 * @param userForm
	 * @return
	 */
	public JsonModel getProdut(Integer page, Integer number, UserForm userForm) {
		//开启分页
		PageHelper.startPage(page, number);
		Page<UserVO> userVOPage = (Page<UserVO>) userMapper.getUser(userForm);
		PageInfo<UserVO> userDOPageInfo = userVOPage.toPageInfo();
		//返回数据
		JsonModel jsonModel = new JsonModel();
		jsonModel.setCount(userDOPageInfo.getTotal());
		jsonModel.setData(userDOPageInfo.getList());
		return jsonModel;
	}

	/**
	 * 冻结用户，根据用户id
	 * @param id
	 */
	public void deleteUserById(Integer id) {
		UserDO userDO = new UserDO();
		userDO.setId(id);
		userDO.setPermission("2");
		userMapper.updateByPrimaryKeySelective(userDO);
	}

	/**
	 * 修改用户信息
	 * @param userForm
	 */
	public JsonModel updateUserByUserForm(UserForm userForm) {
		JsonModel jsonModel = new JsonModel();
		//判断学号是否存在相同的
		Example userSnoQuery = new Example(UserDO.class);
		userSnoQuery.createCriteria().andEqualTo("sno",userForm.getSno());
		if(userMapper.selectOneByExample(userSnoQuery) != null &&userMapper.selectOneByExample(userSnoQuery).getId() != userForm.getId()){
			jsonModel.setCode(1);
			jsonModel.setMsg("该学号已存在，不可以修改");
			return jsonModel;
		}
		//判断用户名
		Example userNameQuery = new Example(UserDO.class);
		userNameQuery.createCriteria().andEqualTo("name",userForm.getName());
		if(userMapper.selectOneByExample(userNameQuery) != null && userMapper.selectOneByExample(userNameQuery).getId() != userForm.getId()){
			jsonModel.setCode(1);
			jsonModel.setMsg("该用户名已存在，不可以修改");
			return jsonModel;
		}
		//判断邮箱
		Example userEmailQuery = new Example(UserDO.class);
		userEmailQuery.createCriteria().andEqualTo("email",userForm.getEmail());
		if(userMapper.selectOneByExample(userEmailQuery) != null && userMapper.selectOneByExample(userEmailQuery).getId() != userForm.getId()){
			jsonModel.setCode(1);
			jsonModel.setMsg("该邮箱已存在，不可以修改");
			return jsonModel;
		}
		//判断手机号
		Example userPhoneQuery = new Example(UserDO.class);
		userPhoneQuery.createCriteria().andEqualTo("phone",userForm.getPhone());
		if(userMapper.selectOneByExample(userPhoneQuery) != null && userMapper.selectOneByExample(userPhoneQuery).getId() != userForm.getId()){
			jsonModel.setCode(1);
			jsonModel.setMsg("该号码已存在，不可以修改");
			return jsonModel;
		}
		//更新用户信息
		//绑定用户信息
		UserDO userDO = new UserDO();
		userDO.setId(userForm.getId());
		userDO.setSno(userForm.getSno());
		userDO.setRole(userForm.getRole());
		userDO.setName(userForm.getName());
		userDO.setPhone(userForm.getPhone());
		userDO.setEmail(userForm.getEmail());
		userDO.setUpdateTime(new Date());
		userMapper.updateByPrimaryKeySelective(userDO);
		jsonModel.setMsg("更新成功");
		return jsonModel;
	}

	/**
	 * 重置密码
	 * @param id
	 */
	public void updateUserPasswordById(Integer id) {
		UserDO userDO = new UserDO();
		userDO.setId(id);
		try {
			//处理密码，加密存进数据库,默认密码123456
			String passWord = RASEncrypt.encrypt("123456", Constant.PUBLICKRY);
			userDO.setPassword(passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userMapper.updateByPrimaryKeySelective(userDO);
	}

	/**
	 * 授权登陆系统
	 * @param id
	 */
	public void updateUserPermissionById(Integer id) {
		UserDO userDO = new UserDO();
		userDO.setId(id);
		userDO.setPermission("1");
		userDO.setUpdateTime(new Date());
		userMapper.updateByPrimaryKeySelective(userDO);
	}

	/**
	 * 根据查询条件查询user
	 * @param userForm
	 * @return
	 */
	public UserDO getUserIdByUserName(UserForm userForm) {
		Example example = new Example(UserDO.class);
		example.createCriteria().andEqualTo("sno", userForm.getSno())
				.andEqualTo("email", userForm.getEmail());
		return userMapper.selectOneByExample(example);
	}
}
