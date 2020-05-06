package com.example.mall.domain.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductForm {
	private Integer id;
	private String order;//排序
	private String permission;//商品授权
	private String status;//商品状态
	private String title;//商品标题
	private String sort;//商品类别
	private String des;//商品描述
	private String imgUrl;//图片地址
	private BigDecimal price;//价格
	private BigDecimal oldPrice;//原价格
	private Integer collectNum;//收藏数
	private Date createTime;//发布日期
	private Date updateTime;//更新日期

	private Integer userId;//用户id
	private Integer productId;//商品id
}
