package com.example.mall.service;

import com.example.common.utils.Constant;
import com.example.common.utils.JsonModel;
import com.example.common.utils.RASEncrypt;
import com.example.common.utils.redis.RedisUtils;
import com.example.mall.dao.AccountMapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.AccountDO;
import com.example.mall.domain.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@Transactional
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AccountMapper accountMapper;

	/**
	 * 用户注册
	 */
	public JsonModel addUser(UserDO userDO) {
		JsonModel jsonModel = new JsonModel();
		//参数校验
		//判断学号是否存在
		UserDO queryUserSno = new UserDO();
		queryUserSno.setSno(userDO.getSno());
		if(userMapper.selectOne(queryUserSno) != null){
			//该学号已存在
			jsonModel.setCode(200);
			jsonModel.setMsg("该学号已注册，请勿重新注册");
			return jsonModel;
		}
		//判断用户名是否存在
		UserDO queryUserName = new UserDO();
		queryUserName.setName(userDO.getName());
		if(userMapper.selectOne(queryUserName) != null){
			//该学号已存在
			jsonModel.setCode(200);
			jsonModel.setMsg("该用户名已注册，请勿重新注册");
			return jsonModel;
		}
		//判断手机号码是否重复
		UserDO queryUserPhone = new UserDO();
		queryUserPhone.setPhone(userDO.getPhone());
		if(userMapper.selectOne(queryUserPhone) != null){
			//该学号已存在
			jsonModel.setCode(200);
			jsonModel.setMsg("该手机号码已注册，请检查手机号码是否填写错误");
			return jsonModel;
		}
		//判断email是否重复
		UserDO queryUserEmail = new UserDO();
		queryUserEmail.setEmail(userDO.getEmail());
		if(userMapper.selectOne(queryUserEmail) != null){
			//该学号已存在
			jsonModel.setCode(200);
			jsonModel.setMsg("该邮箱已注册，请检查手机号码是否填写错误");
			return jsonModel;
		}
		try {
			//处理密码，加密存进数据库
			String passWord = RASEncrypt.encrypt(userDO.getPassword(), Constant.PUBLICKRY);
			userDO.setPassword(passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//权限设置
		userDO.setPermission("0");
		//时间设置
		userDO.setCreateTime(new Date());
		userDO.setUpdateTime(new Date());
		userMapper.insert(userDO);
		//添加账号
		UserDO query = new UserDO();
		query.setSno(userDO.getSno());
		UserDO newUser = userMapper.selectOne(query);
		AccountDO accountDO = new AccountDO();
		accountDO.setUserId(newUser.getId());
		accountDO.setUserName(newUser.getName());
		accountDO.setBalance(new BigDecimal(0));
		accountMapper.insert(accountDO);
		jsonModel.setCode(0);
		jsonModel.setMsg("注册成功");
		log.info("用户："+userDO.getName()+"注册成功");
		return jsonModel;
	}

	/**
	 * 根据用户id查找用户
	 */
	public UserDO findUser(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	/**
	 *更新个人信息
	 */
	public void updateUser(UserDO userDO) {
		userDO.setUpdateTime(new Date());
		userMapper.updateByPrimaryKeySelective(userDO);
	}

	/**
	 * 根据邮箱查找用户信息
	 * @param email
	 * @return
	 */
	public UserDO findUserByEmail(String email) {
		UserDO userDO = new UserDO();
		userDO.setEmail(email);
		return userMapper.selectOne(userDO);
	}

	/**
	 * 修改密码
	 * @param userDO
	 */
	public void updatePassword(UserDO userDO) {
		userMapper.updateByPrimaryKeySelective(userDO);
	}
}
