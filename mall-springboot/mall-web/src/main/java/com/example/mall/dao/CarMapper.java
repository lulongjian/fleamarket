package com.example.mall.dao;

import com.example.common.thMapper.TkMapper;
import com.example.mall.domain.CarDO;
import com.example.mall.domain.form.CarForm;
import com.example.mall.domain.vo.CarVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper extends TkMapper<CarDO> {

	List<CarVO> findCarList(CarForm carForm);//查询商品列表
}
