package com.itcast.bos.service.take_delivery;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.itcast.bos.domain.base.SubArea;
import com.itcast.bos.domain.take_delivery.Order;

@Produces("*/*")
public interface OrderService {
	
	
	@POST
	@Path("/order")
	@Consumes({"application/xml","application/json"})
	public void saveOrder(Order order);

	public Order showOrder(String orderNum);
	
	
	
}
