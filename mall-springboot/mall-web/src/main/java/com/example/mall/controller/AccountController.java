package com.example.mall.controller;

import com.example.common.utils.JsonModel;
import com.example.mall.domain.AccountRecordDO;
import com.example.mall.domain.vo.AccountVO;
import com.example.mall.utils.AccountRecordJsonModel;
import com.example.mall.domain.AccountDO;
import com.example.mall.service.AccountRecordService;
import com.example.mall.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("account")
@Slf4j
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRecordService accountRecordService;
	/**
	 * 查看我的账户
	 */
	@RequestMapping(value="myAccount", method= RequestMethod.POST)
	public JsonModel myAccount(String userId){
		JsonModel jsonModel = new JsonModel();

		//查询账户余额
		AccountDO query = new AccountDO();
		query.setUserId(Integer.parseInt(userId));
		AccountDO accountDO = accountService.findAccountByUserId(query);

		//查询账户记录
		AccountRecordDO accountRecordDO = new AccountRecordDO();
		accountRecordDO.setUserId(Integer.parseInt(userId));
		List<AccountRecordDO> accountRecordDOList = accountRecordService.findAccountRecordByUserId(accountRecordDO);
		//交易类型转化
		for(AccountRecordDO accountR :accountRecordDOList){
			List<String> result = AccountRecordJsonModel.categoryToString(accountR.getCategory(),accountR.getMoney(), new LinkedList<>());
			accountR.setCategory(result.get(0));
			accountR.setMoneyStr(result.get(1));
		}

		//封装查询数据
		AccountVO accountVO = new AccountVO();
		accountVO.setAccount(accountDO);
		accountVO.setAccountRecord(accountRecordDOList);

		log.info("查询我的账户");
		jsonModel.setMsg("查询我的账户");
		jsonModel.setCode(0);
		jsonModel.setData(accountVO);
		return jsonModel;
	}

	/**
	 * 我的账户充值/提现
	 */
	@RequestMapping(value="addAccountByUserId", method= RequestMethod.POST)
	public JsonModel addAccountByUserId(String userId, String type, String money, String productUserId){
		JsonModel jsonModel = new JsonModel();
		switch (type){
			case "0":
				//查询账户余额
				AccountDO query = new AccountDO();
				query.setUserId(Integer.parseInt(userId));
				AccountDO accountDO = accountService.findAccountByUserId(query);
				if(accountDO.getBalance().compareTo(new BigDecimal(money)) == -1){
					jsonModel.setCode(1);
					jsonModel.setMsg("余额不足,请到点击右上角我的-我的账户-充值");
					break;
				}
				//支出
				accountService.payFor(userId, money, productUserId);
				break;
			case "3":
				//充值
				//判断账户余额是否支持提现
				//余额添加
				accountService.addAccountByUserId(userId, money);
				jsonModel.setCode(0);
				jsonModel.setMsg("恭喜你成功充值 "+money+" 元");
				break;
			case "4":
				//提现
				//余额减少
				//查询账户余额
				AccountDO qu = new AccountDO();
				qu.setUserId(Integer.parseInt(userId));
				AccountDO ac = accountService.findAccountByUserId(qu);
				if(ac.getBalance().compareTo(new BigDecimal(money)) == -1){
					jsonModel.setCode(1);
					jsonModel.setMsg("余额不足,请先前往个人中心充值");
					break;
				}
				accountService.redAccountByUserId(userId, money);
				jsonModel.setCode(0);
				jsonModel.setMsg("恭喜你成功提现 "+money+" 元");
				break;
			default: return jsonModel;
		}
		return jsonModel;
	}
}
