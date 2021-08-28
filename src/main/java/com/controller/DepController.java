package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.util.AJAXUtil;
import com.util.ControllerServiceUtil;

@Controller
public class DepController {
	@Resource(name="controllerServiceUtil")
	private ControllerServiceUtil csutil;
	public ControllerServiceUtil getCsutil() {
		return csutil;
	}
	public void setCsutil(ControllerServiceUtil csutil) {
		this.csutil = csutil;
	}
	
	/**部门添加**/
	@RequestMapping("save_dep.do")
	public String save(HttpServletRequest request,HttpServletResponse response,Dep dep){
		boolean flag = csutil.getDepService().save(dep);
		if(flag){
			System.out.println("部门添加成功");
			//将方法的执行结果的JSON字符串返回到前台
			AJAXUtil.printString(response, "1");
		}else{
			System.out.println("部门添加失败");
			AJAXUtil.printString(response, "0");
		}
		return null;
	}
	
	/**部门查询所有**/
	@RequestMapping("findAll_dep.do")
	public String findAll(HttpServletRequest request,HttpServletResponse response){
		List<Dep> deplst = csutil.getDepService().findAll();
		String jsonstr = JSONObject.toJSONString(deplst);
		//将转化后的json字符串返回到前台
		AJAXUtil.printString(response, jsonstr);
		return null;
	}
	
	/**部门查询单个**/
	@RequestMapping("findByDepid_dep.do")
	public String findByDepid(HttpServletRequest request,HttpServletResponse response,Integer depid){
		Dep dep = csutil.getDepService().findByDepid(depid);
		String depjson = JSONObject.toJSONString(dep);
		AJAXUtil.printString(response, depjson);
		return null;
	}
	
	/**部门修改**/
	@RequestMapping("update_dep.do")
	public String update(HttpServletRequest request,HttpServletResponse response,Dep dep){
		boolean flag = csutil.getDepService().update(dep);
		if(flag){
			System.out.println("部门修改成功");
			//将方法的执行结果的JSON字符串返回到前台
			AJAXUtil.printString(response, "1");
		}else{
			System.out.println("部门修改失败");
			AJAXUtil.printString(response, "0");
		}
		return null;
	}
}
