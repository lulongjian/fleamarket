package com.example.mall.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 封装Product查询结果
 */
@Data
public class ProductVO {
	private Integer id;
	private String permission;//商品授权
	private String title;//商品标题
	private String sort;//商品类别
	private String status;//商品状态 0已售出 1未售出
	private String des;//商品描述
	private Integer userId;//用户id
	private String imgUrl;//图片地址
	private BigDecimal price;//价格
	private BigDecimal oldPrice;//原价格
	private Integer collectNum;//收藏数

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//发布日期

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;//修改

	private String sno;//用户sno
	private String name;//用户名字
	private String phone;//用户手机
}
