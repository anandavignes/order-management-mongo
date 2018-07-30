package com.ally.order.mgt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ally.order.mgt.domain.Order;
import com.ally.order.mgt.domain.OrderSummary;
import com.ally.order.mgt.exception.BadRequestException;
import com.ally.order.mgt.exception.MissingProductsException;
import com.ally.order.mgt.exception.OrderNotFoundException;
import com.ally.order.mgt.repository.OrderRepository;
import com.ally.order.mgt.util.OrderUtil;

@Service
public class OrderServiceImpl implements OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Order submitOrder(Order order) throws Exception {
		logger.info("New Order Submission starts");
		if(null != order) {
			if(null == order.getProducts() || order.getProducts().isEmpty()) {
				throw new MissingProductsException();
			}
		}
		order.setStatus(OrderUtil.WAITING_FOR_PAYMENT);
		order = orderRepository.save(order);
		order.setTotaldue(OrderUtil.calculateTotalDueAmount(order.getProducts()));
		logger.info("Order Submission was successfull and the order number is {} and the total due is {}",
				order.getOrdernumber(), order.getTotaldue());
		
		return order;
	}

	@Override
	public Order retrieveOrder(String orderId) throws Exception {
		logger.info("Retrive Order - OrderNumber {}", orderId);
		if(StringUtils.isEmpty(orderId)) {
			throw new BadRequestException();
		}
		Order order = orderRepository.findByOrdernumber(orderId);
		if(null == order) {
			throw new OrderNotFoundException();
		}
		return order;
	}

	@Override
	public List<Order> retrieveAllOrders() throws Exception {
		logger.info("Retrieve All Orders - Starts");
		List<Order> allOrders = orderRepository.findAll();
		logger.info("Total number of orders: {}", allOrders.size());
		return allOrders;
	}

	@Override
	public OrderSummary retriveOrderSummary() throws Exception {
		logger.info("Retrieve Order Summary - Starts");
		List<Order> allOrders = orderRepository.findAll();
		OrderSummary orderSummary = OrderUtil.generateOrderSummary(allOrders);
		logger.info("Retrieve Order Summary: Payment-Waiting: {} , Order-Shipped: {}",
				orderSummary.getNumberWaitingForPayment(), orderSummary.getNumberShipped());
		return orderSummary;
	}



}
