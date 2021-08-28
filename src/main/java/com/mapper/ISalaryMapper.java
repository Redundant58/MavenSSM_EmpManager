package com.mapper;

import org.springframework.stereotype.Service;

import com.po.Salary;

@Service("salaryMapper")
public interface ISalaryMapper {
	/**薪资添加**/public int save(Salary salary);
	/**薪资删除**/public int delByEid(Integer eid);
	/**通过员工编号查询薪资**/public Salary findByEid(Integer eid);
	/**薪资修改**/public int update(Salary salary);
}
