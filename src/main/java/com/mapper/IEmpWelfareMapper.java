package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.EmpWelfare;
import com.po.Welfare;

@Service("empWelfareMapper")
public interface IEmpWelfareMapper {
	/**员工福利添加**/public int save(EmpWelfare empWelfare);
	/**员工福利删除**/public int delByEid(Integer eid);
	/**通过员工编号查询员工福利（直接返回福利对象集合）**/public List<Welfare> findByEid(Integer eid);
}
