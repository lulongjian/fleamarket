package com.example.mall.service;

import com.example.mall.dao.AccountMapper;
import com.example.mall.dao.AccountRecordMapper;
import com.example.mall.dao.OrderMapper;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.AccountDO;
import com.example.mall.domain.AccountRecordDO;
import com.example.mall.domain.OrderDO;
import com.example.mall.domain.ProductDO;
import com.example.mall.domain.form.OrderForm;
import com.example.mall.domain.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountRecordMapper accountRecordMapper;

	/**
	 * 根据用户id查询购入订单
	 *
	 * @param orderForm
	 * @return
	 */
	public List<OrderVO> findBuyOrderByForm(OrderForm orderForm) {
		List<OrderVO> orderVOList = orderMapper.findBuyOrderByForm(orderForm);
		//封装状态
		if (orderVOList.size() != 0) {
			//封装状态 0 未付款；1 已付款；2 已发货；3 已完成
			for (int i = 0; i < orderVOList.size(); i++) {
				if(orderVOList.get(i).getStatus().equals("0")){
					orderVOList.get(i).setStatus("未付款");
				}
				if(orderVOList.get(i).getStatus().equals("1")){
					orderVOList.get(i).setStatus("已付款");
				}
				if(orderVOList.get(i).getStatus().equals("2")){
					orderVOList.get(i).setStatus("已发货");
				}
				if(orderVOList.get(i).getStatus().equals("3")){
					orderVOList.get(i).setStatus("已完成");
				}
			}
		}
		return orderVOList;
	}

	/**
	 * 根据用户id查询售出订单
	 *
	 * @param orderForm
	 * @return
	 */
	public List<OrderVO> findSellerOrderByForm(OrderForm orderForm) {
		List<OrderVO> orderVOList = orderMapper.findSellerOrderByForm(orderForm);
		//封装状态
		if (orderVOList.size() != 0) {
			//封装状态 0 未付款；1 已付款；2 已发货；3 已完成
			for (int i = 0; i < orderVOList.size(); i++) {
				if(orderVOList.get(i).getStatus().equals("0")){
					orderVOList.get(i).setStatus("未付款");
				}
				if(orderVOList.get(i).getStatus().equals("1")){
					orderVOList.get(i).setStatus("已付款");
				}
				if(orderVOList.get(i).getStatus().equals("2")){
					orderVOList.get(i).setStatus("已发货");
				}
				if(orderVOList.get(i).getStatus().equals("3")){
					orderVOList.get(i).setStatus("已完成");
				}
			}
		}
		return orderVOList;
	}
	//生成订单
	public void addOrder(OrderDO orderDO) {
		//生成订单编号
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"+ orderDO.getProductId()+"ss"+orderDO.getBuyerId());
		orderDO.setOrderNo(sdf.format(date));
		orderDO.setStatus("1");
		orderDO.setCreateTime(date);
		orderDO.setUpdateTime(date);
		orderMapper.addOrder(orderDO);
		//改变商品状态
		ProductDO productDO = new ProductDO();
		productDO.setId(orderDO.getProductId());
		productDO.setStatus("0");
		productMapper.updateByPrimaryKeySelective(productDO);
	}

	/**
	 * 确认收货
	 * @param orderDO
	 */
	public void enterOrder(OrderDO orderDO) {
		//更改订单状态
		OrderDO newOrder = new OrderDO();
		newOrder.setStatus("3");
		newOrder.setId(orderDO.getId());
		orderMapper.updateByPrimaryKeySelective(newOrder);
		//找到卖家
		OrderDO myOrder = orderMapper.selectByPrimaryKey(orderDO.getId());
		//付款给卖家
		AccountRecordDO accountRecordDO = new AccountRecordDO();
		accountRecordDO.setUserId(myOrder.getSellerId());
		accountRecordDO.setUserName(myOrder.getSellerName());
		accountRecordDO.setSellerId(myOrder.getBuyerId());
		accountRecordDO.setSellerName(myOrder.getBuyerName());
		accountRecordDO.setMoney(myOrder.getProductPrice());
		accountRecordDO.setCategory("1");
		accountRecordDO.setCreateTime(new Date());
		accountRecordMapper.insert(accountRecordDO);
		//卖家账户+付款金额
		AccountDO queryAccount = new AccountDO();
		queryAccount.setUserId(myOrder.getSellerId());
		AccountDO accountDO = accountMapper.selectOne(queryAccount);
		accountDO.setBalance(accountDO.getBalance().add(myOrder.getProductPrice()));
		accountMapper.updateByPrimaryKeySelective(accountDO);
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

	/**
	 * 发货
	 * @param orderDO
	 */
	public void setOutOrder(OrderDO orderDO) {
		//修改订单状态--已发货
		OrderDO newOrder = new OrderDO();
		newOrder.setId(orderDO.getId());
		newOrder.setStatus("2");
		orderMapper.updateByPrimaryKeySelective(newOrder);
	}
}
