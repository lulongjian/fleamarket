package com.example.mall.dao;

import com.example.common.thMapper.TkMapper;
import com.example.mall.domain.OrderDO;
import com.example.mall.domain.form.OrderForm;
import com.example.mall.domain.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends TkMapper<OrderDO> {

	//根据用户id查询购入订单
	List<OrderVO> findBuyOrderByForm(OrderForm orderForm);

	//根据用户id查询售出订单
	List<OrderVO> findSellerOrderByForm(OrderForm orderForm);

	//查询正在进行中的订单
	int getUserOrderCountByUserId(Integer userId);

	//添加订单信息
	void addOrder(OrderDO orderDO);

	//管理员获取订单信息
	List<OrderVO> getOrder(OrderForm orderForm);
}
