package com.example.mall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "user_order")
public class OrderDO {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "product_id")
	private Integer productId;//商品id

	@Column(name = "product_title")
	private String productTitle;//商品标题

	@Column(name = "product_price")
	private BigDecimal productPrice;//商品价格

	@Column(name = "order_no")
	private String orderNo;//订单编号

	@Column(name = "buyer_id")
	private Integer buyerId;//买家id

	@Column(name = "buyer_name")
	private String buyerName;//买家名

	@Column(name = "seller_id")
	private Integer sellerId;//卖家id

	@Column(name = "seller_name")
	private String sellerName;//卖家名

	@Column(name = "status")
	private String status;//订单状态

	@Column(name = "account_record_id")
	private String accountRecordId;//订单付款记录id

	@Column(name = "address_id")
	private BigDecimal addressId;//地址id

	@Column(name = "create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//生成日期

	@Column(name = "update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;//更新日期
}
