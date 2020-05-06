package com.example.mall.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class UserVO {
	private Integer id;
	private String sno;//学号
	private String role;//角色
	private String permission;//权限
	private String name;//用户名
	private String password;//密码
	private String phone;//手机号码
	private String email;//邮箱
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime; //添加时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime; //更新时间

	private String address;//用户常用地址
}
