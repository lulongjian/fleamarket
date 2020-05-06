package com.example.mall.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountRecordVO {

	private Integer id;
	private Integer userId;//用户id
	private String userName;//用户名字
	private String userSno;//用户学号
	private String userPhone;//用户联系电话
	private Integer sellerId;//卖家id
	private String sellerName;//卖家名
	private String sellerSno;
	private String sellerPhone;//用户联系电话
	private String category;//交易类别 0支出 1收入
	private BigDecimal money;//金额
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime; //添加时间
	private String moneyStr;
}
