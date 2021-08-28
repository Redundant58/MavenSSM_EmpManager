package com.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Dep;
import com.service.IDepService;
import com.util.ServiceMapperUtil;

@Service("depService")
@Transactional
public class DepServiceImpl implements IDepService {
	@Resource(name="serviceMapperUtil")
	private ServiceMapperUtil smutil;
	public ServiceMapperUtil getSmutil() {
		return smutil;
	}
	public void setSmutil(ServiceMapperUtil smutil) {
		this.smutil = smutil;
	}

	@Override
	public List<Dep> findAll() {
		return smutil.getDepMapper().findAll();
	}
	@Override
	public boolean save(Dep dep) {
		int code = smutil.getDepMapper().save(dep);
		if(code>0){
			return true;
		}
		return false;
	}
	@Override
	public Dep findByDepid(Integer depid) {
		return smutil.getDepMapper().findByDepid(depid);
	}
	@Override
	public boolean update(Dep dep) {
		int code = smutil.getDepMapper().update(dep);
		if(code>0){
			return true;
		}
		return false;
	}

}
