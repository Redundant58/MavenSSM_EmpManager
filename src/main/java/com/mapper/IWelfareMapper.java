package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.Welfare;

@Service("welfareMapper")
public interface IWelfareMapper {
	/**查询所有福利**/public List<Welfare> findAll();
}
