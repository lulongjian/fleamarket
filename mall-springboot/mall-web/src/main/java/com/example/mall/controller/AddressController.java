package com.example.mall.controller;

import com.example.common.utils.JsonModel;
import com.example.mall.domain.AddressDO;
import com.example.mall.domain.form.AddressForm;
import com.example.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	/**
	 * 根据用户id查询地址列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel getList(String userId) {
		JsonModel jsonModel = new JsonModel();
		List<AddressDO> addressDOList = addressService.findAddressByUserId(Integer.parseInt(userId));
		jsonModel.setCode(0);
		jsonModel.setData(addressDOList);
		return jsonModel;
	}

	/**
	 * 根据地址id删除地址
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value = "/deleteByAddressId", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel deleteByAddressId(String addressId) {
		JsonModel jsonModel = new JsonModel();
		addressService.deleteAddressById(Integer.parseInt(addressId));
		jsonModel.setCode(0);
		jsonModel.setMsg("删除成功");
		return jsonModel;
	}

	/**
	 * 根据地址id查找地址信息
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value = "/findAddressById", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel findAddressById(String addressId) {
		JsonModel jsonModel = new JsonModel();
		AddressDO addressDO = addressService.findAddressById(Integer.parseInt(addressId));
		jsonModel.setCode(0);
		jsonModel.setData(addressDO);
		return jsonModel;
	}

	/**
	 * 根据地址id添加/更新地址信息
	 * @return
	 */
	@RequestMapping(value = "/addOrUpdateAddressById", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel addOrUpdateAddressById(AddressForm form) {
		JsonModel jsonModel = new JsonModel();
		if(form.getId().equals("undefined")){
			//为空则添加
			jsonModel.setCode(0);
			addressService.addAddress(form);
			jsonModel.setMsg("添加成功");
			return jsonModel;
		}
		addressService.updateAddressById(form);
		jsonModel.setCode(0);
		jsonModel.setMsg("更新成功");
		return jsonModel;
	}
}
