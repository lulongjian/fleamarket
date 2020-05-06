package com.example.mall.dao;

import com.example.common.thMapper.TkMapper;
import com.example.mall.domain.AccountDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends TkMapper<AccountDO> {

}
