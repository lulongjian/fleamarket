package com.example.mall.dao;

import com.example.mall.domain.form.UserForm;
import com.example.mall.domain.vo.UserVO;
import com.example.common.thMapper.TkMapper;
import com.example.mall.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends TkMapper<UserDO> {

	List<UserVO> getUser(UserForm userForm);
}
