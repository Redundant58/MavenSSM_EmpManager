package com.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Welfare;
import com.service.IWelfareService;
import com.util.ServiceMapperUtil;

@Service("welfareService")
@Transactional
public class WelfareServiceImpl implements IWelfareService {
	@Resource(name="serviceMapperUtil")
	private ServiceMapperUtil smutil;
	public ServiceMapperUtil getSmutil() {
		return smutil;
	}
	public void setSmutil(ServiceMapperUtil smutil) {
		this.smutil = smutil;
	}
	
	@Override
	public List<Welfare> findAll() {
		return smutil.getWelfareMapper().findAll();
	}

}
