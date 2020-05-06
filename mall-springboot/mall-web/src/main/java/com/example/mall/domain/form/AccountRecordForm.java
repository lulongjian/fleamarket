package com.example.mall.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountRecordForm {

	private Integer id;
	private Integer userId;//用户id
	private String userName;//用户名字
	private Integer sellerId;//卖家id
	private String sellerName;//卖家名
	private String category;//交易类别 0支出 1收入
	private BigDecimal money;//金额
	private Date createTime; //添加时间
	private String moneyStr;

	private String category1;
	private String category2;

}
