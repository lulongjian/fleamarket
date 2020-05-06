package com.example.mall.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "account")
public class AccountDO {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;//用户id

	@Column(name = "user_name")
	private String userName;//用户名字

	@Column(name = "balance")
	private BigDecimal balance;//账户余额
}
