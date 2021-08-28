package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.Dep;
import com.po.Welfare;

public interface IWelfareService {
	/**查询所有福利**/public List<Welfare> findAll();
}
