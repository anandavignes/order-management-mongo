package com.ally.order.mgt.util;

import java.text.DecimalFormat;
import java.util.List;

import com.ally.order.mgt.domain.Order;
import com.ally.order.mgt.domain.OrderSummary;
import com.ally.order.mgt.domain.Product;

public class OrderUtil {

	public static final String WAITING_FOR_PAYMENT = "WAITING_FOR_PAYMENT";
	public static final String PAYMENT_SUCCESSFULL = "PAYMENT_SUCCESSFULL";
	
	public static String calculateTotalDueAmount(List<Product> products) {
		double totalDueAmount = 0.00;
		if(null != products && !products.isEmpty()) {
			totalDueAmount = products.stream()
					.mapToDouble(p -> (Double.valueOf(p.getPrice()) * Integer.valueOf(p.getQuantity()))).sum();
		}
		return String.valueOf(new DecimalFormat("0.00").format(totalDueAmount));
	}

	public static OrderSummary generateOrderSummary(List<Order> allOrders) {
		int waitingForPmt = 0;
		int shippedCount = 0;
		if(null != allOrders && !allOrders.isEmpty()) {
			for(Order order : allOrders) {
				if(WAITING_FOR_PAYMENT.equalsIgnoreCase(order.getStatus())) {
					waitingForPmt++;
				}else if(PAYMENT_SUCCESSFULL.equalsIgnoreCase(order.getStatus())) {
					shippedCount++;
				}
			}
		}
		OrderSummary orderSummary = new OrderSummary(String.valueOf(waitingForPmt), String.valueOf(shippedCount));
		return orderSummary;
	}

}
