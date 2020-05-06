package com.example.common.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {

	private Integer id;
	@JsonProperty("parentId")
	private Integer pid;
	private String title;
	private String icon;
	private String href;
	private Boolean spread;

	private List<TreeNode> children = new ArrayList<TreeNode>();

	/**
	 *首页左边导航树的构造器
	 */
	public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.icon = icon;
		this.href = href;
		this.spread = spread;
	}
	/**
	 * 菜单管理左菜单dtree的数据格式
	 */
	public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.spread = spread;
	}
}
