package com.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Emp;
import com.po.EmpWelfare;
import com.po.Salary;
import com.po.Welfare;
import com.service.IEmpService;
import com.util.ServiceMapperUtil;

@Service("empService")
@Transactional
public class EmpServiceImpl implements IEmpService {
	@Resource(name="serviceMapperUtil")
	private ServiceMapperUtil smutil;
	public ServiceMapperUtil getSmutil() {
		return smutil;
	}
	public void setSmutil(ServiceMapperUtil smutil) {
		this.smutil = smutil;
	}
	
	@Override
	public boolean save(Emp emp) {
		//先进行员工添加
		int code = smutil.getEmpMapper().save(emp);
		//如果员工添加成功的话，再进行薪资保存和员工福利保存
		if(code>0){
			/*薪资保存和员工福利保存都需要员工编号eid
			 （员工编号eid通过  *员工添加成功* 之后自增生成，通过查询员工表当前最大id，即为该员工的编号eid）*/
			Integer eid = smutil.getEmpMapper().findMaxEid();
			/**************薪资保存start*************/
			Salary salary = new Salary(emp.getEmoney(),eid);
			smutil.getSalaryMapper().save(salary);
			/**************薪资保存end*************/
			/**************员工福利保存start*************/
			//获得该员工的福利编号集合wids
			String[] wids = emp.getWids();
			if(wids!=null && wids.length>0){
				//遍历福利编号集合（取出每一个福利编号，并调用方法保存到员工福利表中）
				for (int i = 0; i < wids.length; i++) {
					Integer wid = Integer.parseInt(wids[i]);
					EmpWelfare empWelfare = new EmpWelfare(eid,wid);
					smutil.getEmpWelfareMapper().save(empWelfare);
				}
			}
			/**************员工福利保存end*************/
			return true;
		}
		return false;
	}
	@Override
	public List<Emp> findPageAll(Integer page,Integer rows) {
		return smutil.getEmpMapper().findPageAll(page, rows);
	}
	@Override
	public Integer findMaxRows() {
		return smutil.getEmpMapper().findMaxRows();
	}
	@Override
	public boolean delByEid(Integer eid) {
		//删除操作：先删从表，再删主表
		//删除员工福利关系
		smutil.getEmpWelfareMapper().delByEid(eid);
		//删除薪资
		smutil.getSalaryMapper().delByEid(eid);
		//删除员工
		int code = smutil.getEmpMapper().delByEid(eid);
		if(code>0){
			return true;
		}
		return false;
	}
	@Override
	public Emp findByEid(Integer eid) {
		//获取员工的对象
		Emp findemp = smutil.getEmpMapper().findByEid(eid);
		//获取薪资对象，然后将薪资存入员工对象中的薪资属性emoney(临时属性)
		Salary salary = smutil.getSalaryMapper().findByEid(eid);
		if(salary!=null && salary.getSmoney()!=null){
			findemp.setEmoney(salary.getSmoney());
		}
		//获取福利对象
		List<Welfare> wflst = smutil.getEmpWelfareMapper().findByEid(eid);
		findemp.setWflst(wflst);
		//处理福利id
		String[] wids = new String[wflst.size()];
		for (int i = 0; i < wflst.size(); i++) {
			//从福利集合wflst中获得wid，并存入数组wids中
			wids[i] = wflst.get(i).getWid().toString();
		}
		//将福利集合对象wflst和福利id数组 分别存入员工对象中
		findemp.setWflst(wflst);
		findemp.setWids(wids);
		return findemp;
	}
	@Override
	public boolean update(Emp emp) {
		//先修改员工
		int code = smutil.getEmpMapper().update(emp);
		if(code>0){
			//再修改薪资，获取原来的薪资
			Salary oldsa = smutil.getSalaryMapper().findByEid(emp.getEid());
			if(oldsa!=null && oldsa.getSmoney()!=null){//之前有薪资的情况
				oldsa.setSmoney(emp.getEmoney());
				smutil.getSalaryMapper().update(oldsa);
			}else{//之前没有薪资，则重新保存薪资
				Salary sa = new Salary(emp.getEmoney(),emp.getEid());
				smutil.getSalaryMapper().save(sa);
			}
			//修改员工福利（先删除之前的福利，再重新添加福利）
			List<Welfare> wflst = smutil.getEmpWelfareMapper().findByEid(emp.getEid());
			if(wflst!=null && wflst.size()>0){
				//删除原有福利
				smutil.getEmpWelfareMapper().delByEid(emp.getEid());
			}
			String[] wids = emp.getWids();
			if(wids!=null && wids.length>0){
				for (int i = 0; i < wids.length; i++) {
					Integer wid = Integer.parseInt(wids[i]);
					EmpWelfare ewf = new EmpWelfare(emp.getEid(),wid);
					smutil.getEmpWelfareMapper().save(ewf);
				}
			}
			return true;
		}
		return false;
	}
}
