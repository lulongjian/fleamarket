package com.example.mall.controller;

import com.example.common.utils.JsonModel;
import com.example.mall.domain.ProductDO;
import com.example.mall.domain.form.ProductForm;
import com.example.mall.domain.vo.ProductVO;
import com.example.mall.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * 查询商品列表
	 * @param page
	 * @param number
	 * @param productForm
	 * @return
	 */
	@RequestMapping(value="productList", method= RequestMethod.POST)
	public JsonModel findAllProduct(Integer page, Integer number, ProductForm productForm){
		JsonModel jsonModel;
		log.info("查询商品列表");
		jsonModel = productService.findProduct(page, number, productForm);
		jsonModel.setCode(0);
		jsonModel.setMsg("查询成功");
		return jsonModel;
	}

	/**
	 * 添加商品
	 * @param productForm
	 * @return
	 */
	@RequestMapping(value="addProduct", method= RequestMethod.PUT)
	public JsonModel addProduct(ProductForm productForm){
		JsonModel jsonModel = new JsonModel();
		log.info("添加商品");
		productService.addProduct(productForm);
		return jsonModel;
	}

	/**
	 *根据用户id获取个人发布商品
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="productListByUser", method= RequestMethod.POST)
	public JsonModel productListByUserId(String userId){
		JsonModel jsonModel = new JsonModel();
		log.info("添加商品");
		List<ProductDO> productList = productService.findProductListByUserId(Integer.parseInt(userId));
		jsonModel.setCode(0);
		jsonModel.setMsg("查询个人发布商品成功");
		jsonModel.setData(productList);
		return jsonModel;
	}

	/**
	 *删除个人商品
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="delete", method= RequestMethod.POST)
	public JsonModel deleteProduct(String userId, String productId){
		JsonModel jsonModel = new JsonModel();
		log.info("删除商品：id = "+productId);
		productService.delete(userId, productId);
		jsonModel.setCode(0);
		jsonModel.setMsg("删除商品成功");
		return jsonModel;
	}

	/**
	 *	根据商品id获取商品
	 * @param id
	 * @return
	 */
	@RequestMapping(value="findProductById", method= RequestMethod.POST)
	public JsonModel findProductById(String id){
		JsonModel jsonModel = new JsonModel();
		jsonModel.setCode(0);
		jsonModel.setMsg("查询成功");
		ProductDO productDO = productService.findProductById(id);
		jsonModel.setData(productDO);
		return jsonModel;
	}

	/**
	 *	根据商品id获取商品，我的订单模块
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="findProductById/{productId}", method= RequestMethod.GET)
	public JsonModel findProduct(@PathVariable("productId") String productId){
		JsonModel jsonModel = new JsonModel();
		ProductVO productvo = productService.findProductListById(productId);
		if (productvo == null){
			jsonModel.setCode(1);
			return jsonModel;
		}
		jsonModel.setData(productvo);
		return jsonModel;
	}

	/**
	 *	更新商品信息
	 * @param productForm
	 * @return
	 */
	@RequestMapping(value="updateProduct", method= RequestMethod.POST)
	public JsonModel updateProduct(ProductForm productForm){
		JsonModel jsonModel = new JsonModel();
		jsonModel.setCode(0);
		jsonModel.setMsg("更新成功");
		productService.updateProduct(productForm);
		jsonModel.setData(productForm);
		return jsonModel;
	}
}
