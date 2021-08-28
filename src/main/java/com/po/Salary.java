package com.po;

import java.io.Serializable;

/**薪资类**/
public class Salary implements Serializable{
	private Integer sid;//薪资编号
	private Float smoney;//薪资
	private Integer eid;//薪资对应的员工编号
	public Salary() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Salary(Integer sid, Float smoney, Integer eid) {
		super();
		this.sid = sid;
		this.smoney = smoney;
		this.eid = eid;
	}
	/**添加专用**/
	public Salary(Float smoney, Integer eid) {
		super();
		this.smoney = smoney;
		this.eid = eid;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Float getSmoney() {
		return smoney;
	}
	public void setSmoney(Float smoney) {
		this.smoney = smoney;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	@Override
	public String toString() {
		return "Salary [sid=" + sid + ", smoney=" + smoney + ", eid=" + eid + "]";
	}
	
}
