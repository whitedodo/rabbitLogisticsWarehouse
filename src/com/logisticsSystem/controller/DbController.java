/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: DbController.java
 * Created Date: 2019-07-25
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * 
 */

package com.logisticsSystem.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class DbController {
	
	private MariaConnector mariaConnector = null;
	private DefaultTableModel model = null;
	private Connection conn = null;
	private ResultSet rs = null;
	
	public DbController(){
		this.mariaConnector = new MariaConnector();
	}
	
	public final Connection getConn(){
		return this.mariaConnector.getConn();
	}
	
	public void setConn(Connection conn){
		this.conn = conn;
	}
	
	public DefaultTableModel getModel(){
		return this.model;
	}
	
	public void setModel(DefaultTableModel model){
		this.model = model;
	}
	
	public ResultSet getResultSet(){
		return this.rs;
	}
	
	public void setResultSet(ResultSet rs){
		this.rs = rs;
	}
	
	public void select(PreparedStatement pstmt){
		
		try {
			setResultSet(pstmt.executeQuery());
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} // end of try to catch finally
		
	}
	
	
	public void update(PreparedStatement pstmt){
		try {
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
