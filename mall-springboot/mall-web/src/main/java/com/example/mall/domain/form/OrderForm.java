package com.example.mall.domain.form;

import lombok.Data;

@Data
public class OrderForm {

	private Integer id;

	private Integer userId;//用户id

	private Integer productId;//商品id

	private String name;//名字

	private String status;//状态
}
