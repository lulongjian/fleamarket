package com.example.mall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "sys_user")
public class UserDO {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "sno")
	private String sno;//学号

	@Column(name = "role")
	private String role;//角色

	@Column(name = "permission")
	private String permission;//权限

	@Column(name = "name")
    private String name;//用户名

	@Column(name = "password")
    private String password;//密码

	@Column(name = "phone")
	private String phone;//手机号码

	@Column(name = "email")
	private String email;//邮箱

	@Column(name = "create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime; //添加时间

	@Column(name = "update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime; //更新时间

}
