package com.po;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**员工类**/
public class Emp implements Serializable{
	private Integer eid;//员工编号
	private String ename;//员工姓名
	private String gender;//员工性别
	private String address;//员工住址
	private Date birthday;//员工生日（负责从后台与数据库交互的日期属性）
	private String photo="default.jpg";//员工照片
	private Integer depid;//员工对应的部门编号
	/**临时属性**/
	private String depname;//部门名称
	private Float emoney;//员工薪资
	private String[] wids;//福利编号数组（从前台传到后台，取到福利编号后，到数据库查询）
	private List<Welfare> wflst;//福利集合(从后台到前台)
	private MultipartFile pic;//文件上传
	private String sdate;//负责前后台交互的日期属性
	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Emp(Integer eid, String ename, String gender, String address, Date birthday, String photo, Integer depid,
			String depname, Float emoney, String[] wids, List<Welfare> wflst, MultipartFile pic, String sdate) {
		super();
		this.eid = eid;
		this.ename = ename;
		this.gender = gender;
		this.address = address;
		this.birthday = birthday;
		this.photo = photo;
		this.depid = depid;
		this.depname = depname;
		this.emoney = emoney;
		this.wids = wids;
		this.wflst = wflst;
		this.pic = pic;
		this.sdate = sdate;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getDepid() {
		return depid;
	}
	public void setDepid(Integer depid) {
		this.depid = depid;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public Float getEmoney() {
		return emoney;
	}
	public void setEmoney(Float emoney) {
		this.emoney = emoney;
	}
	public String[] getWids() {
		return wids;
	}
	public void setWids(String[] wids) {
		this.wids = wids;
	}
	public List<Welfare> getWflst() {
		return wflst;
	}
	public void setWflst(List<Welfare> wflst) {
		this.wflst = wflst;
	}
	public MultipartFile getPic() {
		return pic;
	}
	public void setPic(MultipartFile pic) {
		this.pic = pic;
	}
	public String getSdate() {
		if(birthday!=null){
			sdate = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
		}
		return sdate;
	}
	public void setSdate(String sdate) {
		try {
			if(sdate!=null){
				birthday = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.sdate = sdate;
	}
	@Override
	public String toString() {
		return "Emp [eid=" + eid + ", ename=" + ename + ", gender=" + gender + ", address=" + address + ", birthday="
				+ birthday + ", photo=" + photo + ", depid=" + depid + ", depname=" + depname + ", emoney=" + emoney
				+ ", wids=" + Arrays.toString(wids) + ", wflst=" + wflst + ", pic=" + pic + ", sdate=" + sdate
				+ ", getEid()=" + getEid() + ", getEname()=" + getEname() + ", getGender()=" + getGender()
				+ ", getAddress()=" + getAddress() + ", getBirthday()=" + getBirthday() + ", getPhoto()=" + getPhoto()
				+ ", getDepid()=" + getDepid() + ", getDepname()=" + getDepname() + ", getEmoney()=" + getEmoney()
				+ ", getWids()=" + Arrays.toString(getWids()) + ", getWflst()=" + getWflst() + ", getPic()=" + getPic()
				+ ", getSdate()=" + getSdate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
