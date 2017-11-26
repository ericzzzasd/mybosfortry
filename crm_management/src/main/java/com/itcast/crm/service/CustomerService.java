package com.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
<<<<<<< HEAD
import javax.ws.rs.POST;
=======
>>>>>>> 6645a5796d582c6708852a194116047f00e92785
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.itcast.crm.domain.Customer;

@Produces("*/*")
public interface CustomerService {
	
	@GET
	@Path("/noConnectCustomer")
	@Produces({"application/xml","application/json"})
	public List<Customer> findNoConnectCustomer();
	
	@GET
	@Path("/connectCustomer/{fixedArea_Id}")
	@Consumes({"application/xml","application/json"})
	@Produces({"application/xml","application/json"})
	public List<Customer> findConnectCustomer(@PathParam("fixedArea_Id") String fixedArea_Id);
	
	
	@PUT
	@Path("/connectFixedArea")
	@Consumes({"application/xml","application/json"})
	public void customerConnectFixedArea(@QueryParam("fixedArea_Id") String  fixedArea_Id,
			@QueryParam("customer_Ids")	String customer_Ids);
<<<<<<< HEAD
	
	@POST
	@Path("/saveCustomer")
	@Consumes({"application/xml","application/json"})
	public void registerCustomer(Customer customer);
	
	@GET
	@Path("/findCustomerByTelephone/{telephone}")
	@Produces({"application/xml","application/json"})
	public Customer findCustomerByTelephone(@PathParam(value = "telephone") String telephone);
	
	@PUT
	@Path("/changeCustomer/{telephone}")
	@Consumes({"application/xml","application/json"})
	public void changeCustomer(@PathParam(value = "telephone") String telephone);
	
	@GET
	@Path("/custoemr/login")
	@Produces({"application/xml","application/json"})
	public Customer findCustomerByTelephoneAndPassoword(@QueryParam("telephone")String telephone,@QueryParam("passowrd")String passowrd);
	
	@GET
	@Path("/customer/address")
	@Produces({"application/xml","application/json"})
	@Consumes({"application/xml","application/json"})
	public Customer findCustomerByAddress(@QueryParam("address")String address);
=======
>>>>>>> 6645a5796d582c6708852a194116047f00e92785
}
