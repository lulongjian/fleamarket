package com.example.mall.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVO {
	private Integer id;
	private Integer productId;//商品id
	private String productTitle;//商品标题
	private String imgUrl;//商品图片地址
	private String productStatus;//商品状态
	private BigDecimal productPrice;//商品价格
	private String orderNo;//订单编号
	private Integer buyerId;//买家id
	private String buyerName;//买家名
	private String buyerPhone;//买家注册电话
	private String buyerUserName;//买家地址名
	private String buyerUserPhone;//买家电话
	private Integer sellerId;//卖家id
	private String sellerName;//卖家名
	private String sellerPhone;//卖家电话
	private String status;//订单状态
	private BigDecimal addressId;//地址id
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//生成日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;//更新日期
}
