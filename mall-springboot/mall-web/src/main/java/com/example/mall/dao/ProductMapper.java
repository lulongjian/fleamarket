package com.example.mall.dao;

import com.example.common.thMapper.TkMapper;
import com.example.mall.domain.ProductDO;
import com.example.mall.domain.form.ProductForm;
import com.example.mall.domain.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper extends TkMapper<ProductDO> {

	List<ProductVO> findProduct(ProductForm productForm);//分页查询，并按条件查询

	List<ProductVO> findProductById(ProductForm productForm);//查找订单模块的商品详情

	List<ProductVO> getProduct(ProductForm productForm);//管理员页面查找商品
}
