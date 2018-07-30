package com.ally.order.mgt.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ally.order.mgt.domain.Order;
import com.ally.order.mgt.domain.OrderSummary;
import com.ally.order.mgt.domain.Product;
import com.ally.order.mgt.util.OrderUtil;

public class OrderUtilTest {

	List<Product> products = null;
	List<Order> orders = null;
	
	@Before
	public void setUp() throws Exception {
		products = new ArrayList<Product>();
		Product prod1 = new Product("abcd","10.2","2");
		Product prod2 = new Product("abcd","15.3","3");
		products.add(prod1);
		products.add(prod2);
		
		orders = new ArrayList<Order>();
		orders.add(new Order(OrderUtil.WAITING_FOR_PAYMENT));
		orders.add(new Order(OrderUtil.WAITING_FOR_PAYMENT));
		orders.add(new Order(OrderUtil.PAYMENT_SUCCESSFULL));
		
	}

	@After
	public void tearDown() throws Exception {
		products = null;
	}

	@Test
	public void testCalculateTotalDueAmountWhenProductIsNull() {
		assertEquals("0.00", OrderUtil.calculateTotalDueAmount(null));
	}

	@Test
	public void testCalculateTotalDueAmountWhenProductIsEmpty() {
		assertEquals("0.00", OrderUtil.calculateTotalDueAmount(new ArrayList<Product>()));
	}

	@Test
	public void testCalculateTotalDueAmountSuccessScenario() {
		assertEquals("66.30", OrderUtil.calculateTotalDueAmount(products));
	}

	@Test
	public void testgenerateOrderSummaryWhenOrderIsNull() {
		OrderSummary orderSummary = OrderUtil.generateOrderSummary(null);
		assertEquals("0", orderSummary.getNumberWaitingForPayment());
		assertEquals("0", orderSummary.getNumberShipped());
	}

	@Test
	public void testgenerateOrderSummaryWhenOrderIsEmpty() {
		OrderSummary orderSummary = OrderUtil.generateOrderSummary(new ArrayList<Order>());
		assertEquals("0", orderSummary.getNumberWaitingForPayment());
		assertEquals("0", orderSummary.getNumberShipped());
	}

	@Test
	public void testgenerateOrderSummarySuccessScenario() {
		OrderSummary orderSummary = OrderUtil.generateOrderSummary(orders);
		assertEquals("2", orderSummary.getNumberWaitingForPayment());
		assertEquals("1", orderSummary.getNumberShipped());
	}

}
