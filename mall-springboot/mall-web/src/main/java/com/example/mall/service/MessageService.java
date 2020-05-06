package com.example.mall.service;

import com.example.mall.dao.MessageMapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.vo.MessageVO;
import com.example.mall.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

	@Autowired
	private MessageMapper messageMapper;

	@Autowired
	private UserMapper userMapper;

	/**
	 * 根据商品id查询所有留言
	 */
	public List<MessageVO> findMessageByProductId(int productId) {
		return messageMapper.findMessageByProductId(productId);
	}

	/**
	 * 添加留言
	 */
	public void addMessage(int productId, String text, String name) {
		//根据用户名查询用户id
		UserDO userDO = new UserDO();
		userDO.setName(name);
		UserDO userDO1 = userMapper.selectOne(userDO);
		messageMapper.addMessage(productId, text, userDO1.getId(), new Date());
	}
}
