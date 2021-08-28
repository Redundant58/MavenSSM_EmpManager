package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.Dep;

@Service("depMapper")
public interface IDepMapper {
	/**查询所有部门**/public List<Dep> findAll();
	/**部门添加**/public int save(Dep dep);
	/**查询单个部门**/public Dep findByDepid(Integer depid);
	/**部门修改**/public int update(Dep dep);
}
