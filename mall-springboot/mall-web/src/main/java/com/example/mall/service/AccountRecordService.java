package com.example.mall.service;

import com.example.mall.domain.AccountRecordDO;
import com.example.mall.dao.AccountRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AccountRecordService {

	@Autowired
	private AccountRecordMapper accountRecordMapper;

	/**
	 *根据用户id查询交易记录
	 */
	public List<AccountRecordDO> findAccountRecordByUserId(AccountRecordDO accountRecordDO) {
		//根据时间排序
		Example example = new Example(AccountRecordDO.class);
		example.orderBy("createTime").desc();
		example.createCriteria().andEqualTo("userId",accountRecordDO.getUserId());
		return accountRecordMapper.selectByExample(example);
	}
}
