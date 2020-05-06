package com.example.mall.dao;

import com.example.common.thMapper.TkMapper;
import com.example.mall.domain.AccountRecordDO;
import com.example.mall.domain.form.AccountRecordForm;
import com.example.mall.domain.vo.AccountRecordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountRecordMapper extends TkMapper<AccountRecordDO> {

	//管理员获取交易记录信息
	List<AccountRecordVO> getAccountRecord(AccountRecordForm accountRecordForm);
}
