package com.example.mall.service;

import com.example.mall.dao.AccountMapper;
import com.example.mall.dao.AccountRecordMapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.AccountDO;
import com.example.mall.domain.AccountRecordDO;
import com.example.mall.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountService {

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountRecordMapper accountRecordMapper;

	@Autowired
	private UserMapper userMapper;

	/**
	 *根据用户id查询用户余额
	 */
	public AccountDO findAccountByUserId(AccountDO accountDO) {
		return accountMapper.selectOne(accountDO);
	}

	/**
	 *	充值
	 */
	public void addAccountByUserId(String userId, String money) {
		//查找用户账户id
		AccountDO query = new AccountDO();
		query.setUserId(Integer.parseInt(userId));
		AccountDO accountDO = accountMapper.selectOne(query);
		accountDO.setBalance(accountDO.getBalance().add(new BigDecimal(money)));
		//更新余额
		accountMapper.updateByPrimaryKeySelective(accountDO);
		//添加充值记录
		AccountRecordDO accountRecordDO = new AccountRecordDO();
		accountRecordDO.setUserId(accountDO.getUserId());
		accountRecordDO.setUserName(accountDO.getUserName());
		accountRecordDO.setSellerName("系统");
		accountRecordDO.setCategory("3");
		accountRecordDO.setMoney(new BigDecimal(money));
		accountRecordDO.setCreateTime(new Date());
		accountRecordMapper.insert(accountRecordDO);
	}

	/**
	 *	提现
	 */
	public void redAccountByUserId(String userId, String money) {
		//查找用户账户id
		AccountDO query = new AccountDO();
		query.setUserId(Integer.parseInt(userId));
		AccountDO accountDO = accountMapper.selectOne(query);
		accountDO.setBalance(accountDO.getBalance().subtract(new BigDecimal(money)));
		//更新余额
		accountMapper.updateByPrimaryKeySelective(accountDO);
		//添加提现记录
		AccountRecordDO accountRecordDO = new AccountRecordDO();
		accountRecordDO.setCategory("4");
		accountRecordDO.setMoney(new BigDecimal(money));
		accountRecordDO.setCreateTime(new Date());
		accountRecordDO.setUserId(accountDO.getUserId());
		accountRecordDO.setUserName(accountDO.getUserName());
		accountRecordDO.setSellerName("系统");
		accountRecordMapper.insert(accountRecordDO);
	}

	/**
	 * 支出---购买商品，咱缓存至第三方
	 * @param userId
	 * @param money
	 */
	public void payFor(String userId, String money, String productUserId) {
		//查找用户账户id
		AccountDO query = new AccountDO();
		query.setUserId(Integer.parseInt(userId));
		AccountDO accountDO = accountMapper.selectOne(query);
		accountDO.setBalance(accountDO.getBalance().subtract(new BigDecimal(money)));
		//更新余额
		accountMapper.updateByPrimaryKeySelective(accountDO);
		//添加提现记录
		AccountRecordDO accountRecordDO = new AccountRecordDO();
		accountRecordDO.setCategory("0");
		accountRecordDO.setMoney(new BigDecimal(money));
		accountRecordDO.setUserId(accountDO.getUserId());
		accountRecordDO.setUserName(accountDO.getUserName());
		accountRecordDO.setCreateTime(new Date());
		//获取卖家
		UserDO seller = userMapper.selectByPrimaryKey(productUserId);
		accountRecordDO.setSellerId(seller.getId());
		accountRecordDO.setSellerName(seller.getName());
		accountRecordMapper.insert(accountRecordDO);
	}
}
