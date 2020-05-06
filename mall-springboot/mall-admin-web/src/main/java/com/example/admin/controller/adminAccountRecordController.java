package com.example.admin.controller;

import com.example.mall.domain.form.AccountRecordForm;
import com.example.admin.service.AdminAccountRecordService;
import com.example.common.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/account")
public class adminAccountRecordController {

	@Autowired
	private AdminAccountRecordService adminAccountRecordService;

	/**
	 * 管理员获取交易记录信息
	 *
	 * @return
	 */
	@RequestMapping(value = "getAccountRecord", method = RequestMethod.POST)
	public JsonModel getAccountRecord(Integer page, Integer number, AccountRecordForm accountRecordForm) {
		JsonModel jsonModel = new JsonModel();
		try {
			jsonModel = adminAccountRecordService.getAccountRecord(page, number, accountRecordForm);
			jsonModel.setMsg("加载成功");
		}catch (Exception e){
			jsonModel.setMsg("网络错误！请刷新重试");
		}
		return jsonModel;
	}
}
