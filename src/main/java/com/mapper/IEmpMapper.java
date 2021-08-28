package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.po.Emp;

@Service("empMapper")
public interface IEmpMapper {
	/**员工添加**/public int save(Emp emp);
	/**查询员工表最大id（即为最后一次添加的员工id）**/public Integer findMaxEid();
	/**分页查询**/public List<Emp> findPageAll(@Param("page") Integer page, @Param("rows") Integer rows);
	/**查询总记录数**/public Integer findMaxRows();
	/**员工删除**/public int delByEid(Integer eid);
	/**单个员工查询**/public Emp findByEid(Integer eid);
	/**员工修改**/public int update(Emp emp);
}
