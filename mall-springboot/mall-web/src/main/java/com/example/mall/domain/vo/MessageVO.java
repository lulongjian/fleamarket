package com.example.mall.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 封装Product查询结果
 */
@Data
public class MessageVO {
	private Integer id;

	private Integer userId;//用户id

	private Integer productId;//商品id

	private String text;//留言信息

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime; //添加时间

	private String name;//用户名字
}
