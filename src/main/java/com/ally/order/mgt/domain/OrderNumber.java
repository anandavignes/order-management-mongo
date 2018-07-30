package com.ally.order.mgt.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class OrderNumber implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String ordernumber;
	
	public OrderNumber() {}
	
	public OrderNumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

}
