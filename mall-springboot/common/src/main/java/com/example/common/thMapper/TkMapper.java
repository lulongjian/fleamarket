package com.example.common.thMapper;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface TkMapper<T> extends Mapper<T> {

}
