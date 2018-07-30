package com.ally.order.mgt.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ally.order.mgt.domain.Order;
import com.ally.order.mgt.domain.Product;
import com.ally.order.mgt.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

	Order order =  null;
	List<Product> products = null;
	String existingOrderId = null;
	@Autowired
	private OrderRepository repository;

	@Before
	public void setUp() throws Exception {
		products = new ArrayList<Product>();
		Product prod1 = new Product("abcd","10.2","2");
		Product prod2 = new Product("abcd","15.3","3");
		
		products.add(prod1);
		products.add(prod2);
		order = new Order(products);
		Product prodXYZ = new Product("xyz","11.3","1");
		List<Product> productsXYZ = new ArrayList<Product>();
		productsXYZ.add(prodXYZ);
		existingOrderId = repository.save(new Order(productsXYZ)).getOrdernumber();
	}

	@After
	public void tearDown() throws Exception {
		products = null;
		order =  null;
		existingOrderId = null;
	}

	@Test
	public void testInsertOrderWhenThereNoProducts() throws Exception {
		Order inserted = repository.save(new Order());
		assertNotNull(inserted.getOrdernumber());
	}
	
	@Test
	public void testInsertOrderSuccessScenario() throws Exception {
		Order inserted = repository.save(order);
		assertNotNull(inserted.getOrdernumber());
	}

	@Test
	public void testgetOrderByIdWhenWithNonExistentId() throws Exception {
		assertNull(repository.findByOrdernumber(String.valueOf(new SecureRandom().nextInt(100)+1)));
	}
	
	@Test
	public void testgetOrderByIdWhenWithValidOrderId() throws Exception {
		Order retrived = repository.findByOrdernumber(existingOrderId);
		assertNotNull(retrived);
		assertNull(retrived.getStatus());
	}

}
