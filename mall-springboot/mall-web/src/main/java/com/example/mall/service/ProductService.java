package com.example.mall.service;

import com.example.common.utils.JsonModel;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.vo.ProductVO;
import com.example.mall.domain.ProductDO;
import com.example.mall.domain.form.ProductForm;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 查询所有商品，分页查询，分类查询，模糊查询
	 */
	public JsonModel findProduct(int pageNum, int pageSize, ProductForm productForm) {
		//开启分页
		PageHelper.startPage(pageNum, pageSize);
		Page<ProductVO> productVOList = (Page<ProductVO>) productMapper.findProduct(productForm);
		PageInfo<ProductVO> pageInfo = productVOList.toPageInfo();
		//返回数据
		JsonModel jsonModel = new JsonModel();
		jsonModel.setData(pageInfo.getList());
		jsonModel.setCount(pageInfo.getTotal());
		return jsonModel;
	}

	/**
	 * 添加商品
	 *
	 * @param productForm
	 */
	public void addProduct(ProductForm productForm) {
		ProductDO productDO = new ProductDO();
		productDO.setImgUrl(productForm.getImgUrl());
		productDO.setTitle(productForm.getTitle());
		productDO.setOldPrice(productForm.getOldPrice());
		productDO.setPrice(productForm.getPrice());
		productDO.setDes(productForm.getDes());
		productDO.setUserId(productForm.getUserId());
		productDO.setSort(productForm.getSort());
		productDO.setPermission("0");
		productDO.setCollectNum(0);
		productDO.setStatus("1");
		productDO.setCreateTime(new Date());
		productMapper.insert(productDO);
	}

	/**
	 * 根据用户id获取个人商品
	 *
	 * @param userId
	 * @return
	 */
	public List<ProductDO> findProductListByUserId(int userId) {
		ProductDO productDO = new ProductDO();
		productDO.setUserId(userId);
		return productMapper.select(productDO);
	}

	/**
	 * 删除商品
	 *
	 * @param userId
	 * @param productId
	 */
	public void delete(String userId, String productId) {
		//删除商品 通过商品id
		productMapper.deleteByPrimaryKey(productId);
		//删除购物车关联
	}

	/**
	 * 根据商品id获取商品
	 *
	 * @param id
	 * @return
	 */
	public ProductDO findProductById(String id) {
		return productMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新商品信息
	 *
	 * @param productForm
	 */
	public void updateProduct(ProductForm productForm) {
		ProductDO productDO = new ProductDO();
		productDO.setTitle(productForm.getTitle());
		productDO.setId(productForm.getId());
		if (productForm.getImgUrl() != "") {
			productDO.setImgUrl(productForm.getImgUrl());
		}
		productDO.setSort(productForm.getSort());
		productDO.setOldPrice(productForm.getOldPrice());
		productDO.setPrice(productForm.getPrice());
		productDO.setDes(productForm.getDes());
		productDO.setUpdateTime(new Date());
		productMapper.updateByPrimaryKeySelective(productDO);
	}

	/**
	 * 根据商品id获取商品，我的订单模块
	 *
	 * @param productId
	 * @return
	 */
	public ProductVO findProductListById(String productId) {
		ProductForm productForm = new ProductForm();
		productForm.setProductId(Integer.parseInt(productId));
		List<ProductVO> productVOList = productMapper.findProductById(productForm);
		if (productVOList.size() ==0) {
			return null;
		}else {
			return productVOList.get(0);
		}
	}
}
