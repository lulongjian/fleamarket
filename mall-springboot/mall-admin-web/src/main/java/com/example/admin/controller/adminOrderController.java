package com.example.admin.controller;

import com.example.admin.service.AdminOrderService;
import com.example.admin.service.AdminProductService;
import com.example.common.utils.JsonModel;
import com.example.mall.domain.form.OrderForm;
import com.example.mall.domain.form.ProductForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
public class adminOrderController {

	@Autowired
	private AdminOrderService adminOrderService;

	/**
	 * 管理员获取订单信息
	 *
	 * @return
	 */
	@RequestMapping(value = "getOrder", method = RequestMethod.POST)
	public JsonModel getOrder(Integer page, Integer number, OrderForm orderForm) {
		JsonModel jsonModel = new JsonModel();
		try {
			jsonModel = adminOrderService.getOrder(page, number, orderForm);
			jsonModel.setMsg("加载成功");
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg("网络错误！！请刷新重试");
		}
		return jsonModel;
	}


	/**
	 * 取消订单
	 *
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/delete/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public JsonModel deleteOrder(@PathVariable("orderId") String orderId) {
		JsonModel jsonModel = new JsonModel();
		try {
			adminOrderService.deleteOrder(orderId);
			jsonModel.setMsg("确认成功");
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg("网络出错，请刷新页面重新尝试操作");
		}
		return jsonModel;
	}

	/**
	 * 批量取消订单
	 *
	 * @param orderIds
	 * @return
	 */
	@RequestMapping(value = "/deleteList/{orderIds}", method = RequestMethod.GET)
	@ResponseBody
	public JsonModel deleteList(@PathVariable("orderIds") String orderIds) {
		JsonModel jsonModel = new JsonModel();
		try {
			if (StringUtils.isBlank(orderIds)) {
				jsonModel.setCode(1);
				jsonModel.setMsg("没有选择订单！！");
				return jsonModel;
			}
			String[] orderIdArrys = orderIds.split(",");
			for (int i = 0; i < orderIdArrys.length; i++) {
				adminOrderService.deleteOrder(orderIdArrys[i]);
			}
			jsonModel.setMsg("确认成功");
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg("网络出错，请刷新页面重新尝试操作");
		}
		return jsonModel;
	}
}
