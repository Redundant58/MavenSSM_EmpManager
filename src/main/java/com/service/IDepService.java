package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.Dep;

public interface IDepService {
	/**查询所有部门**/public List<Dep> findAll();
	/**部门添加**/public boolean save(Dep dep);
	/**查询单个部门**/public Dep findByDepid(Integer depid);
	/**部门修改**/public boolean update(Dep dep);
}
