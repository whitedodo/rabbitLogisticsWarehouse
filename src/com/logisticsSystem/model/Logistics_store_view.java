/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: Logistics_store_view.java
 * Created Date: 2019-07-27
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * 
 */
package com.logisticsSystem.model;

public class Logistics_store_view {

	private Logistics_store logistics_store = null;
	private Logistics_category logistics_category = null;
	
	public Logistics_store getLogistics_store() {
		return logistics_store;
	}
	public void setLogistics_store(Logistics_store logistics_store) {
		this.logistics_store = logistics_store;
	}
	public Logistics_category getLogistics_category() {
		return logistics_category;
	}
	public void setLogistics_category(Logistics_category logistics_category) {
		this.logistics_category = logistics_category;
	}
	
}
