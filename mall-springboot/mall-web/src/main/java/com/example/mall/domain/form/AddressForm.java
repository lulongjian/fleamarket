package com.example.mall.domain.form;

import lombok.Data;

@Data
public class AddressForm {
	private String id;
	private String userId;//用户id
	private String userName;//用户名字
	private String userPhone;//用户电话
	private String addr;//地址
}
