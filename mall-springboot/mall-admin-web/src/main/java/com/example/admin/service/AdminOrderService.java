package com.example.admin.service;

import com.example.common.utils.JsonModel;
import com.example.mall.dao.AccountMapper;
import com.example.mall.dao.AccountRecordMapper;
import com.example.mall.dao.OrderMapper;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.AccountDO;
import com.example.mall.domain.AccountRecordDO;
import com.example.mall.domain.OrderDO;
import com.example.mall.domain.ProductDO;
import com.example.mall.domain.form.OrderForm;
import com.example.mall.domain.form.ProductForm;
import com.example.mall.domain.vo.OrderVO;
import com.example.mall.domain.vo.ProductVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class AdminOrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private AccountRecordMapper accountRecordMapper;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 管理员获取订单信息
	 * @param page
	 * @param number
	 * @param orderForm
	 * @return
	 */
	public JsonModel getOrder(Integer page, Integer number, OrderForm orderForm) {
		PageHelper.startPage(page, number);
		Page<OrderVO> orderVOPage = (Page<OrderVO>) orderMapper.getOrder(orderForm);
		PageInfo<OrderVO> orderVOPageInfo = orderVOPage.toPageInfo();
		//返回数据
		JsonModel jsonModel = new JsonModel();
		jsonModel.setCount(orderVOPageInfo.getTotal());
		jsonModel.setData(orderVOPageInfo.getList());
		return jsonModel;
	}

	/**
	 * 取消订单
	 * @param orderId
	 */
	public void deleteOrder(String orderId) {
		//获取订单信息
		OrderDO orderDO = orderMapper.selectByPrimaryKey(orderId);
		//判断订单的状态
		//找到付款记录--并退还付款金额
		AccountRecordDO accountRecordDO = accountRecordMapper.selectByPrimaryKey(orderDO.getAccountRecordId());
		if (accountRecordDO == null) {
			//无付款记录
		}else {
			//买家资金原路发挥
			AccountDO accountDO = new AccountDO();
			accountDO.setUserId(accountRecordDO.getUserId());
			AccountDO buyerAccount = accountMapper.selectOne(accountDO);
			buyerAccount.setBalance(buyerAccount.getBalance().add(accountRecordDO.getMoney()));
			accountMapper.updateByPrimaryKeySelective(buyerAccount);
			//创建退回交易记录详情
			AccountRecordDO accountRecord = new AccountRecordDO();
			accountRecord.setUserId(orderDO.getBuyerId());
			accountRecord.setUserName(orderDO.getBuyerName());
			accountRecord.setSellerId(orderDO.getSellerId());
			accountRecord.setSellerName(orderDO.getSellerName());
			accountRecord.setMoney(accountRecordDO.getMoney());
			accountRecord.setCategory("5");
			accountRecord.setCreateTime(new Date());
			accountRecordMapper.insert(accountRecord);
		}
		//修改商品属性---将商品设置为可购买
		ProductDO productDO = productMapper.selectByPrimaryKey(orderDO.getProductId());
		productDO.setStatus("1");
		productMapper.updateByPrimaryKeySelective(productDO);
		//删除订单--物理删除---后期计划做逻辑删除，要留历史记录
		orderMapper.deleteByPrimaryKey(orderDO);
	}
}
