package com.example.mall.controller;

import com.example.common.utils.JsonModel;
import com.example.mall.domain.vo.MessageVO;
import com.example.mall.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("message")
@Slf4j
public class MessageController {

	@Autowired
	private MessageService messageService;

	/**
	 * 根据商品id查询所有留言
	 */
	@RequestMapping(value="messageList", method= RequestMethod.POST)
	public JsonModel findMessageByProductId(String pId){
		JsonModel jsonModel = new JsonModel();
		List<MessageVO> messageVOList = messageService.findMessageByProductId(Integer.parseInt(pId));
		log.info("查询商品留言");
		jsonModel.setMsg("查询成功");
		jsonModel.setCode(0);
		jsonModel.setData(messageVOList);
		jsonModel.setCount((long) messageVOList.size());
		return jsonModel;
	}

	/**
	 * 添加留言
	 */
	@RequestMapping(value="addMessage", method= RequestMethod.POST)
	public JsonModel addMessage(String pId, String text, String name){
		JsonModel jsonModel = new JsonModel();
		messageService.addMessage(Integer.parseInt(pId), text, name);
		log.info("添加留言成功");
		jsonModel.setMsg("添加成功");
		jsonModel.setCode(0);
		return jsonModel;
	}
}
