package com.example.common.utils;

public class FieldNumber {
	//生成编号，不足补0
	public static String number(Integer num){
		String str = String.format("%4d", num+1).replace(" ", "0");
		return str;
	}
}
