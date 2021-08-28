package com.po;

import java.io.Serializable;

/**福利类**/
public class Welfare implements Serializable{
	private Integer wid;//福利编号
	private String wname;//福利名称
	public Welfare() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Welfare(Integer wid, String wname) {
		super();
		this.wid = wid;
		this.wname = wname;
	}
	public Integer getWid() {
		return wid;
	}
	public void setWid(Integer wid) {
		this.wid = wid;
	}
	public String getWname() {
		return wname;
	}
	public void setWname(String wname) {
		this.wname = wname;
	}
	@Override
	public String toString() {
		return "Welfare [wid=" + wid + ", wname=" + wname + ", getWid()=" + getWid() + ", getWname()=" + getWname()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
