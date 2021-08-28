package com.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.service.IDepService;
import com.service.IEmpService;
import com.service.IWelfareService;

@Service("controllerServiceUtil")
public class ControllerServiceUtil {
	@Resource(name="depService")
	private IDepService depService;
	@Resource(name="welfareService")
	private IWelfareService welfareService;
	@Resource(name="empService")
	private IEmpService empService;
	
	public IDepService getDepService() {
		return depService;
	}
	public void setDepService(IDepService depService) {
		this.depService = depService;
	}
	public IWelfareService getWelfareService() {
		return welfareService;
	}
	public void setWelfareService(IWelfareService welfareService) {
		this.welfareService = welfareService;
	}
	public IEmpService getEmpService() {
		return empService;
	}
	public void setEmpService(IEmpService empService) {
		this.empService = empService;
	}
	
}
