package com.example.mall.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "address")
public class AddressDO {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;//用户id

	@Column(name = "user_name")
	private String userName;//用户名字

	@Column(name = "user_phone")
	private String userPhone;//用户电话

	@Column(name = "addr")
	private String addr;//地址
}
