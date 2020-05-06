package com.example.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMsgVO {
	private Integer authUserNum;//已认证用户数
	private Integer notAuthUserNum;//未认证用户数
	private Integer authProductNum;//已授权商品数
	private Integer notAuthProductNum;//未授权商品数
}
