package com.itcast.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.crm.dao.CustomerDao;
import com.itcast.crm.domain.Customer;
import com.itcast.crm.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerDao customerDao;

	@Override
	public List<Customer> findNoConnectCustomer() {
		List<Customer> list = customerDao.findByFixedAreaIdIsNull();
		return list;
	}

	@Override
	public List<Customer> findConnectCustomer(String fixedArea_Id) {
		
		return customerDao.findByFixedAreaId(fixedArea_Id);
	}

	@Override
	public void customerConnectFixedArea(String fixedArea_Id, String customer_Ids) {
		
		customerDao.clearCustomer(fixedArea_Id);
	
		String[] split = customer_Ids.split(",");
		for (String string : split) {
			int parseInt = Integer.parseInt(string);
			customerDao.customerConnectFixedArea(fixedArea_Id, parseInt);
		} 
		
	}

	@Override
	public void registerCustomer(Customer customer) {
		customerDao.save(customer);
		
		
	}

	@Override
	public Customer findCustomerByTelephone(String telephone) {
		Customer customer = customerDao.findByTelephone(telephone);
		return customer;
	}

	@Override
	public void changeCustomer(String telephone) {
		System.out.println(telephone);
		customerDao.changeCustomerType(telephone);
		
	}

	@Override
	public Customer findCustomerByTelephoneAndPassoword(String telephone,String password) {

		Customer loginCustomer = customerDao.findByTelephoneAndPassword(telephone,password);
	
		return loginCustomer;
	}

	@Override
	public Customer findCustomerByAddress(String address) {
		Customer customer = customerDao.findByAddress(address);
		return customer;
	}

}
