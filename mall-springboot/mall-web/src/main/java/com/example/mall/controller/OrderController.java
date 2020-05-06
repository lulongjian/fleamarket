package com.example.mall.controller;

import com.example.common.utils.JsonModel;
import com.example.mall.domain.OrderDO;
import com.example.mall.domain.vo.OrderVO;
import com.example.mall.service.OrderService;
import com.example.mall.domain.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 根据用户id查询购入订单
	 *
	 * @param orderForm
	 * @return
	 */
	@RequestMapping(value = "/buyList", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel buyList(OrderForm orderForm) {
		JsonModel jsonModel = new JsonModel();
		List<OrderVO> orderVOList = orderService.findBuyOrderByForm(orderForm);
		jsonModel.setData(orderVOList);
		return jsonModel;
	}

	/**
	 * 根据用户id查询售出订单
	 *
	 * @param orderForm
	 * @return
	 */
	@RequestMapping(value = "/sellerList", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel sellerList(OrderForm orderForm) {
		JsonModel jsonModel = new JsonModel();
		List<OrderVO> orderVOList = orderService.findSellerOrderByForm(orderForm);
		jsonModel.setData(orderVOList);
		return jsonModel;
	}

	/**
	 * 生成订单
	 *
	 * @param orderDO
	 * @return
	 */
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel addOrder(OrderDO orderDO) {
		JsonModel jsonModel = new JsonModel();
		try {
			orderService.addOrder(orderDO);
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg("网络出错，请刷新页面重新购买");
		}
		return jsonModel;
	}

	/**
	 * 确认收货
	 *
	 * @param orderDO
	 * @return
	 */
	@RequestMapping(value = "/enterOrder", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel enterOrder(OrderDO orderDO) {
		JsonModel jsonModel = new JsonModel();
		try {
			orderService.enterOrder(orderDO);
			jsonModel.setMsg("确认成功");
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg("网络出错，请刷新页面重新尝试操作");
		}
		return jsonModel;
	}

	/**
	 * 确认发货
	 *
	 * @param orderDO
	 * @return
	 */
	@RequestMapping(value = "/setOutOrder", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel setOutOrder(OrderDO orderDO) {
		JsonModel jsonModel = new JsonModel();
		try {
			orderService.setOutOrder(orderDO);
			jsonModel.setMsg("确认成功");
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg("网络出错，请刷新页面重新尝试操作");
		}

		return jsonModel;
	}


	/**
	 * 取消订单
	 *
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
	@ResponseBody
	public JsonModel deleteOrder(String orderId, String userId) {
		JsonModel jsonModel = new JsonModel();
		try {
			orderService.deleteOrder(orderId);
			jsonModel.setMsg("确认成功");
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg("网络出错，请刷新页面重新尝试操作");
		}
		return jsonModel;
	}
}
