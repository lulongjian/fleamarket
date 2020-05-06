package com.example.mall.controller;

import com.example.common.utils.JsonModel;
import com.example.mall.domain.form.CarForm;
import com.example.mall.domain.CarDO;
import com.example.mall.domain.vo.CarVO;
import com.example.mall.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("car")
@Slf4j
public class CarController {

	@Autowired
	private CarService carService;

	/**
	 * 添加购物车
	 */
	@RequestMapping(value = "addCar", method = RequestMethod.POST)
	public JsonModel addCar(CarForm carForm) {
		JsonModel jsonModel = new JsonModel();
		//判断商品是否已存在购物车，存在则返回添加失败，该商品已存在
		List<CarDO> carList = carService.selectCarByUserId(carForm);
		if (carList.size() != 0) {
			//已存在，返回错误代码
			jsonModel.setMsg("该商品已存在，添加失败！");
			jsonModel.setCode(200);
			return jsonModel;
		}
		//添加购物车
		Boolean flag = carService.addCar(carForm);
		if (!flag) {
			jsonModel.setCode(200);
			jsonModel.setMsg("不能添加自己发布商品进入购物车");
			return jsonModel;
		}
		jsonModel.setMsg("添加购物车成功");
		return jsonModel;
	}

	/**
	 * 查询购物车列表
	 */
	@RequestMapping(value = "carList", method = RequestMethod.POST)
	public JsonModel findCarList(CarForm carForm) {
		JsonModel jsonModel = new JsonModel();
		List<CarVO> carList = carService.findCarList(carForm);
		jsonModel.setData(carList);
		jsonModel.setCode(0);
		jsonModel.setMsg("查询成功");
		jsonModel.setCount((long) carList.size());
		return jsonModel;
	}

	/**
	 * 查询购物车列表
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public JsonModel delete(String id) {
		JsonModel jsonModel = new JsonModel();
		carService.delete(id);
		jsonModel.setCode(0);
		jsonModel.setMsg("查询成功");
		return jsonModel;
	}
}
