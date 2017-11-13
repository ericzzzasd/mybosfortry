package com.itcast.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.crm.dao.CustomerDao;
import com.itcast.crm.domain.Customer;

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
		System.out.println(fixedArea_Id);
		
		customerDao.clearCustomer(fixedArea_Id);
		System.out.println(customer_Ids);
		String[] split = customer_Ids.split(",");
		for (String string : split) {
			int parseInt = Integer.parseInt(string);
			customerDao.customerConnectFixedArea(fixedArea_Id, parseInt);
		} 
		
	}

}
