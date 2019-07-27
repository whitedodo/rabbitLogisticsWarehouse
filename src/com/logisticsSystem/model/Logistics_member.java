/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: MainFrmDB.java
 * Created Date: 2019-07-26
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * 
 */
package com.logisticsSystem.model;

import java.io.InputStream;
import java.util.Date;

public class Logistics_member {

	private int id;
	private String email;
	private String passwd;
	private String name;
	private String level;
	private String ip;
	private Date regidate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getRegidate() {
		return regidate;
	}
	public void setRegidate(Date regidate) {
		this.regidate = regidate;
	}
	
	
	
}
