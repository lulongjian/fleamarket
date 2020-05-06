package com.example.mall.utils;

import java.math.BigDecimal;
import java.util.List;

public class AccountRecordJsonModel {
	/**
	 * 交易类型转化
	 */
	public static List<String> categoryToString(String category, BigDecimal money, List<String> resultStr){
		String result;
		String moneyStr = "";
		switch (category){
			case "0":
				result = "支出";
				moneyStr = "-"+String.valueOf(money);
				break;
			case "1":
				result = "收入";
				moneyStr = "+"+String.valueOf(money);
				break;
			case "3":
				result = "充值";
				moneyStr = "+"+String.valueOf(money);
				break;
			case "4":
				result = "提现";
				moneyStr = "-"+String.valueOf(money);
				break;
			case "5":
				result = "退回";
				moneyStr = "+"+String.valueOf(money);
				break;
			default: result = "数据出错";
		}
		resultStr.add(result);
		resultStr.add(moneyStr);
		return resultStr;
	}
}
