package com.example.admin.service;

import com.example.admin.domain.vo.SysMsgVO;
import com.example.mall.dao.ProductMapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.ProductDO;
import com.example.mall.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class AdminSysService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 管理员首页获取信息
	 *
	 * @return
	 */
	public SysMsgVO getSystemData() {
		//已授权用户数
		Example authUserExample = new Example(UserDO.class);
		authUserExample.createCriteria().andEqualTo("permission", "1");
		int authUserNum = userMapper.selectCountByExample(authUserExample);
		//未授权用户数
		Example notAuthExample = new Example(UserDO.class);
		notAuthExample.createCriteria().andEqualTo("permission", "0");
		int notAuthUserNum = userMapper.selectCountByExample(notAuthExample);
		//已授权商品数
		Example authProductExample = new Example(ProductDO.class);
		authProductExample.createCriteria().andEqualTo("permission", "1");
		int authProductNum = productMapper.selectCountByExample(authProductExample);
		//未授权商品数
		Example notAuthProductExample = new Example(ProductDO.class);
		notAuthProductExample.createCriteria().andEqualTo("permission", "0");
		int notAuthProductNum = productMapper.selectCountByExample(notAuthProductExample);

		return new SysMsgVO(authUserNum, notAuthUserNum, authProductNum, notAuthProductNum);
	}
}
