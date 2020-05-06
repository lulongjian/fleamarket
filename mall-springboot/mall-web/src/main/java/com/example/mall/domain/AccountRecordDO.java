package com.example.mall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "account_record")
public class AccountRecordDO {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;//用户id

	@Column(name = "user_name")
	private String userName;//用户名字

	@Column(name = "seller_id")
	private Integer sellerId;//卖家id

	@Column(name = "seller_name")
	private String sellerName;//卖家名

	@Column(name = "category")
	private String category;//交易类别 0支出 1收入

	@Column(name = "money")
	private BigDecimal money;//金额

	@Column(name = "create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime; //添加时间

	//封装moneyStr
	@Column(name = "money_str")
	private String moneyStr;
}
