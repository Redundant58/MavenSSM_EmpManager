package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.po.Welfare;
import com.util.AJAXUtil;
import com.util.ControllerServiceUtil;

@Controller
public class EmpController {
	@Resource(name="controllerServiceUtil")
	private ControllerServiceUtil csutil;
	public ControllerServiceUtil getCsutil() {
		return csutil;
	}
	public void setCsutil(ControllerServiceUtil csutil) {
		this.csutil = csutil;
	}
	
	/**查询部门表和福利表**/
	@RequestMapping("doInit_emp.do")
	public String doInit(HttpServletRequest request,HttpServletResponse response){
		//接收从后台查询的结果（集合）
		List<Dep> deplst = csutil.getDepService().findAll();
		List<Welfare> wflst = csutil.getWelfareService().findAll();
		//因为存在多个结果，所以将多个结果存进Map里，并返回给前台
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("deplst", deplst);
		map.put("wflst", wflst);
		//过滤前台不需要的属性，fastjson就不会将它转化为json字符串（birthday、pic）
		PropertyFilter propertyFilter = AJAXUtil.filterProperts("birthday","pic");
		//将查询的结果map集合转化为json字符串
		String jsonstr = JSONObject.toJSONString(map);
		//将转化后的json字符串返回到前台
		AJAXUtil.printString(response, jsonstr);
		return null;
	}
	
	/**员工添加（包括保存薪资salary和员工福利empWelfare）**/
	@RequestMapping("save_emp.do")
	public String save(HttpServletRequest request,HttpServletResponse response,Emp emp){
		//处理文件
		//获取网站根路径
		String realpath = request.getRealPath("/");
		/*************************文件上传start*************************/
		//获取上传的照片文件对象
		MultipartFile multipartFile = emp.getPic();
		//判断文件对象是否为空
		if(multipartFile!=null&&!multipartFile.isEmpty()){
			//获取上传文件的原名称
			String photo = multipartFile.getOriginalFilename();
			//改名操作
			if(photo.lastIndexOf(".")!=-1){//文件原名称存在"."
				//获得文件后缀
				String ext = photo.substring(photo.lastIndexOf("."));
				if(ext.equalsIgnoreCase(".jpg")){//如果后缀为.jpg（不区分大小写）
					//文件新名称
					photo = new Date().getTime()+ext;
					System.out.println(photo);
					//创建文件对象，指定文件路径
					File file = new File(realpath+"/uppic/"+photo);
					try {
						//上传文件内容（将请求传递的文件内容复制一份到刚才创建的文件中）
						FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
						emp.setPhoto(photo);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		/*************************文件上传end*************************/
		boolean flag = csutil.getEmpService().save(emp);
		if(flag){
			System.out.println("员工添加成功");
			//将方法的执行结果的JSON字符串返回到前台
			AJAXUtil.printString(response, "1");
		}else{
			System.out.println("员工添加失败");
			AJAXUtil.printString(response, "0");
		}
		return null;
	}
	
	/**分页查询**/
	@RequestMapping("findPageAll_emp.do")
	public String findPageAll(HttpServletRequest request,HttpServletResponse response,Integer page,Integer rows){
		//由于EasyUI分页所需要的参数是固定的复杂格式，所以后台传的是map集合
		Map<String,Object> map = new HashMap<String,Object>();
		PageBean pb = new PageBean();
		page = page==null||page<1 ? pb.getPage():page;
		rows = rows==null||rows<1 ? pb.getRows():rows;
		//限制单页记录数（25条）
		if(rows>25)rows=25;
		int maxRows = csutil.getEmpService().findMaxRows();
		List<Emp> emplst = csutil.getEmpService().findPageAll(page, rows);
		//拼接前台EasyUI分页需要的固定数据（key值不能改）
		map.put("page", page);//"page"表示当前页（page）
		map.put("rows", emplst);//"rows"表示查询出来的记录集合（emplst）
		map.put("total", maxRows);//"total"表示查询出来的总记录数（maxRows）
		//过滤前台不需要的属性，fastjson就不会将它转化为json字符串（birthday、pic）
		PropertyFilter propertyFilter = AJAXUtil.filterProperts("birthday","pic");
		//将查询的结果map集合转化为json字符串
		String jsonstr = JSONObject.toJSONString(map);
		//将转化后的json字符串返回到前台
		AJAXUtil.printString(response, jsonstr);
		return null;
	}
	
	/**删除**/
	@RequestMapping("delByEid_emp.do")
	public String delByEid(HttpServletRequest request,HttpServletResponse response,Integer eid){
		boolean flag = csutil.getEmpService().delByEid(eid);
		if(flag){
			System.out.println("员工删除成功");
			//将方法的执行结果的JSON字符串返回到前台
			AJAXUtil.printString(response, "1");
		}else{
			System.out.println("员工删除失败");
			AJAXUtil.printString(response, "0");
		}
		return null;
	}
	
	/**(修改时)查询单个**/
	@RequestMapping("findByEid_emp.do")
	public String findByEid(HttpServletRequest request,HttpServletResponse response,Integer eid){
		Emp findemp = csutil.getEmpService().findByEid(eid);
		//过滤前台不需要的属性，fastjson就不会将它转化为json字符串（birthday、pic）
		PropertyFilter propertyFilter = AJAXUtil.filterProperts("birthday","pic");
		//将查询的结果map集合转化为json字符串
		String jsonstr = JSONObject.toJSONString(findemp);
		//将转化后的json字符串返回到前台
		AJAXUtil.printString(response, jsonstr);
		return null;
	}
	
	/**(查看详情时)查询单个**/
	@RequestMapping("detailsByEid_emp.do")
	public String detailsByEid(HttpServletRequest request,HttpServletResponse response,Integer eid){
		Emp findemp = csutil.getEmpService().findByEid(eid);
		//过滤前台不需要的属性，fastjson就不会将它转化为json字符串（birthday、pic）
		PropertyFilter propertyFilter = AJAXUtil.filterProperts("birthday","pic");
		//将查询的结果map集合转化为json字符串
		String jsonstr = JSONObject.toJSONString(findemp);
		//将转化后的json字符串返回到前台
		AJAXUtil.printString(response, jsonstr);
		return null;
	}
	/**修改**/
	@RequestMapping("update_emp.do")
	public String update(HttpServletRequest request,HttpServletResponse response,Emp emp){
		//处理文件
		//获取原来的照片
		String oldphoto = csutil.getEmpService().findByEid(emp.getEid()).getPhoto();
		//获取网站根路径
		String realpath = request.getRealPath("/");
		/*************************文件上传start*************************/
		//获取上传的照片文件对象
		MultipartFile multipartFile = emp.getPic();
		//判断文件对象是否为空
		if(multipartFile!=null&&!multipartFile.isEmpty()){
			//获取上传文件的原名称
			String photo = multipartFile.getOriginalFilename();
			//改名操作
			if(photo.lastIndexOf(".")!=-1){//文件原名称存在"."
				//获得文件后缀
				String ext = photo.substring(photo.lastIndexOf("."));
				if(ext.equalsIgnoreCase(".jpg")){//如果后缀为.jpg（不区分大小写）
					//文件新名称
					photo = new Date().getTime()+ext;
					System.out.println(photo);
					//创建文件对象，指定文件路径
					File file = new File(realpath+"/uppic/"+photo);
					try {
						//上传文件内容（将请求传递的文件内容复制一份到刚才创建的文件中）
						FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
						emp.setPhoto(photo);
						File oldfile = new File(realpath+"/uppic/"+oldphoto);
						if(oldfile!=null && !oldphoto.equals("default.jpg")){
							oldfile.delete();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{//如果没有修改照片，则重新存入之前的照片
			emp.setPhoto(oldphoto);
		}
		/*************************文件上传end*************************/
		boolean flag = csutil.getEmpService().update(emp);
		if(flag){
			System.out.println("员工修改成功");
			//将方法的执行结果的JSON字符串返回到前台
			AJAXUtil.printString(response, "1");
		}else{
			System.out.println("员工修改失败");
			AJAXUtil.printString(response, "0");
		}
		return null;
	}
	
}
