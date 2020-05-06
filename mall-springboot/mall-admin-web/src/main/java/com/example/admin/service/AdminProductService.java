package com.example.admin.service;

import com.example.common.utils.Constant;
import com.example.common.utils.JsonModel;
import com.example.common.utils.RASEncrypt;
import com.example.mall.dao.*;
import com.example.mall.domain.ProductDO;
import com.example.mall.domain.UserDO;
import com.example.mall.domain.form.ProductForm;
import com.example.mall.domain.form.UserForm;
import com.example.mall.domain.vo.ProductVO;
import com.example.mall.domain.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
@Transactional
public class AdminProductService {

	@Autowired
	private ProductMapper productMapper;
	/**
	 * 管理员获取商品信息
	 *
	 * @param page
	 * @param number
	 * @param productForm
	 * @return
	 */
	public JsonModel getProduct(Integer page, Integer number, ProductForm productForm) {
		//开启分页
		PageHelper.startPage(page, number);
		Page<ProductVO> productVOPage = (Page<ProductVO>) productMapper.getProduct(productForm);
		PageInfo<ProductVO> productVOPageInfo = productVOPage.toPageInfo();
		//返回数据
		JsonModel jsonModel = new JsonModel();
		jsonModel.setCount(productVOPageInfo.getTotal());
		jsonModel.setData(productVOPageInfo.getList());
		return jsonModel;
	}

	/**
	 * 更新商品状态--上架
	 * @param id
	 */
	public void upProduct(String id) {
		ProductDO productDO = new ProductDO();
		productDO.setId(Integer.parseInt(id));
		productDO.setPermission("1");
		productMapper.updateByPrimaryKeySelective(productDO);
	}

	/**
	 * 更新商品状态--下架
	 * @param id
	 */
	public void deProduct(String id) {
		ProductDO productDO = new ProductDO();
		productDO.setId(Integer.parseInt(id));
		productDO.setPermission("0");
		productMapper.updateByPrimaryKeySelective(productDO);
	}
}
