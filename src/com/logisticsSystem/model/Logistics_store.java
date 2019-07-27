/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: Logistics_store.java
 * Created Date: 2019-07-27
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * 
 */
package com.logisticsSystem.model;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

public class Logistics_store {
	
	private int id;
	private int categoryID;
	private int memberID;
	private String subject;
	private String barcode;
	private String author;
	private int price;
	private int cnt;
	private String filename;
	private int filesize;
	private InputStream image;
	private File file;
	private String ip;
	private Timestamp regidate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	
	public int getMemberID() {
		return memberID;
	}
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Timestamp getRegidate() {
		return regidate;
	}
	public void setRegidate(Timestamp regidate) {
		this.regidate = regidate;
	}
	
}
