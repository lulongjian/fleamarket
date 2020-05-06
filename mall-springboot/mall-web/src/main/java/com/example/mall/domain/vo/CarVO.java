package com.example.mall.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CarVO {

	private Integer id;
	private Integer userId;//
	private Integer productUserId;//卖家id
	private String productUserName;//卖家姓名
	private Integer productId;//商品id


	private String status;//商品状态
	private String title;//商品标题
	private String sort;//商品类别
	private String des;//商品描述
	private String imgUrl;//图片地址
	private BigDecimal price;//价格
	private BigDecimal oldPrice;//原价格
	private Integer collectNum;//收藏数

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//发布日期

	private String phone;//用户电话
}
