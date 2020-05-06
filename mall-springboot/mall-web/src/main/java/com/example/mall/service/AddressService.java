package com.example.mall.service;

import com.example.mall.domain.AddressDO;
import com.example.mall.dao.AddressMapper;
import com.example.mall.domain.form.AddressForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

	@Autowired
	private AddressMapper addressMapper;

	/**
	 * 根据用户id查询地址列表
	 * @param userId
	 * @return
	 */
	public List<AddressDO> findAddressByUserId(Integer userId) {
		AddressDO addressDO = new AddressDO();
		addressDO.setUserId(userId);
		return addressMapper.select(addressDO);
	}

	/**
	 * 根据地址id删除地址
	 * @param addressId
	 */
	public void deleteAddressById(int addressId) {
		addressMapper.deleteByPrimaryKey(addressId);
	}

	/**
	 * 根据地址id查找地址信息
	 * @param addressId
	 * @return
	 */
	public AddressDO findAddressById(int addressId) {
		return addressMapper.selectByPrimaryKey(addressId);
	}

	/**
	 * 根据地址id更新地址信息
	 * @param form
	 */
	public void updateAddressById(AddressForm form) {
		AddressDO addressDO = new AddressDO();
		addressDO.setId(Integer.parseInt(form.getId()));
		addressDO.setUserId(Integer.parseInt(form.getUserId()));
		addressDO.setUserName(form.getUserName());
		addressDO.setUserPhone(form.getUserPhone());
		addressDO.setAddr(form.getAddr());
		addressMapper.updateByPrimaryKeySelective(addressDO);
	}

	/**
	 * 添加地址信息
	 * @param form
	 */
	public void addAddress(AddressForm form) {
		AddressDO addressDO = new AddressDO();
		addressDO.setUserId(Integer.parseInt(form.getUserId()));
		addressDO.setUserPhone(form.getUserPhone());
		addressDO.setAddr(form.getAddr());
		addressDO.setUserName(form.getUserName());
		addressMapper.insertSelective(addressDO);
	}
}
