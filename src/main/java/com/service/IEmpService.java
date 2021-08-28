package com.service;

import java.util.List;

import com.po.Emp;
import com.po.PageBean;

public interface IEmpService {
	/**员工添加**/public boolean save(Emp emp);
	/**分页查询**/public List<Emp> findPageAll(Integer page, Integer rows);
	/**查询总记录数**/public Integer findMaxRows();
	/**员工删除**/public boolean delByEid(Integer eid);
	/**单个员工查询**/public Emp findByEid(Integer eid);
	/**员工修改**/public boolean update(Emp emp);
}
