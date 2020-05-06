package com.example.mall.service;

import com.example.mall.dao.CarMapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.CarDO;
import com.example.mall.domain.UserDO;
import com.example.mall.domain.form.CarForm;
import com.example.mall.domain.vo.CarVO;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.ProductDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

	@Autowired
	private CarMapper carMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private UserMapper userMapper;
	/**
	 * 根据用户id和商品id查询商品时候存在购物车中
	 * @param carForm
	 * @return
	 */
	public List<CarDO> selectCarByUserId(CarForm carForm) {
		CarDO carDO = new CarDO();
		carDO.setUserId(carForm.getUserId());
		carDO.setProductId(carForm.getProductId());
		return carMapper.select(carDO);
	}

	/**
	 * 添加购物车
	 * @param carForm
	 */
	public Boolean addCar(CarForm carForm) {
		CarDO carDO = new CarDO();
		carDO.setUserId(carForm.getUserId());
		carDO.setProductId(carForm.getProductId());
		//更新商品得收藏数
		ProductDO productDO = productMapper.selectByPrimaryKey(carForm.getProductId());
		//判断是否是自己的商品
		if(carForm.getUserId().equals(productDO.getUserId())){
			return Boolean.FALSE;
		}
		ProductDO newProduct = new ProductDO();
		newProduct.setId(productDO.getId());
		newProduct.setCollectNum(productDO.getCollectNum()+1);
		productMapper.updateByPrimaryKeySelective(newProduct);
		carMapper.insert(carDO);
		return Boolean.TRUE;
	}

	/**
	 * 查询商品列表
	 * @param carForm
	 * @return
	 */
	public List<CarVO> findCarList(CarForm carForm) {
		List<CarVO> carVOList = carMapper.findCarList(carForm);
		//封装购物车中商品的用户名
		if(carVOList.size() > 0){
			for (int i= 0; i<carVOList.size(); i++){
				UserDO userDO = userMapper.selectByPrimaryKey(carVOList.get(i).getProductUserId());
				if(userDO != null){
					carVOList.get(i).setProductUserName(userDO.getName());
				}
			}
		}

		return carVOList;
	}

	/**
	 *删除购物车商品
	 */
	public void delete(String id) {
		CarDO carDO = carMapper.selectByPrimaryKey(id);
		carMapper.deleteByPrimaryKey(id);
		//更新商品收藏数
		ProductDO productDO = productMapper.selectByPrimaryKey(carDO.getProductId());
		ProductDO newProduct = new ProductDO();
		newProduct.setId(productDO.getId());
		newProduct.setCollectNum(productDO.getCollectNum()-1);
		productMapper.updateByPrimaryKeySelective(newProduct);
	}
}
