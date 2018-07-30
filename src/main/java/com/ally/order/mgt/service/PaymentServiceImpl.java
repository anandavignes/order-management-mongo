package com.ally.order.mgt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ally.order.mgt.domain.Order;
import com.ally.order.mgt.domain.Payment;
import com.ally.order.mgt.exception.DuplicatePaymentException;
import com.ally.order.mgt.exception.OrderNotFoundException;
import com.ally.order.mgt.exception.OverPaymentException;
import com.ally.order.mgt.exception.UnderPaymentException;
import com.ally.order.mgt.repository.OrderRepository;
import com.ally.order.mgt.util.OrderUtil;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public boolean submitPayment(Payment payment) throws Exception {
		logger.info("Payment Submission starts for order number - {} and payment of {}", payment.getOrdernumber(),
				payment.getPayment());
		Order order = orderRepository.findByOrdernumber(payment.getOrdernumber());
		if(null == order) {
			throw new OrderNotFoundException();
		}
		double totalDueAmount = Double.valueOf(OrderUtil.calculateTotalDueAmount(order.getProducts()));
		if(OrderUtil.PAYMENT_SUCCESSFULL.equalsIgnoreCase(order.getStatus())) {
			throw new DuplicatePaymentException();
		}
		double paymentAmount = Double.valueOf(payment.getPayment());
		if(paymentAmount < totalDueAmount) {
			logger.error("Underpayment scenario, total due amount is: {}",totalDueAmount);
			throw new UnderPaymentException();
		} else if(paymentAmount > totalDueAmount) {
			logger.error("Overpayment scenario, total due amount is: {}",totalDueAmount);
			throw new OverPaymentException();
		}else {
			order.setStatus(OrderUtil.PAYMENT_SUCCESSFULL);
			order = orderRepository.save(order);
			logger.info("Payment was successfull and the order number {} is SHIPPED", payment.getOrdernumber());
			return true;
		}
	}

}
