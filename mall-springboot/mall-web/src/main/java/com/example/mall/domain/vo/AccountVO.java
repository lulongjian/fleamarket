package com.example.mall.domain.vo;

import com.example.mall.domain.AccountDO;
import com.example.mall.domain.AccountRecordDO;
import lombok.Data;

import java.util.List;

/**
 * 封装Product查询结果
 */
@Data
public class AccountVO {
	private AccountDO account;//账户
	private List<AccountRecordDO> accountRecord ;//账户交易记录
}
