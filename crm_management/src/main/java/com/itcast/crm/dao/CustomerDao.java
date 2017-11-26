package com.itcast.crm.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itcast.crm.domain.Customer;

public interface CustomerDao extends JpaRepository<Customer,String> {
	
	
	public List<Customer> findByFixedAreaIdIsNull();
	
	public List<Customer> findByFixedAreaId(String fixedArea_Id);
	
	@Query(value="update Customer set fixedAreaId=? where id=?")
	@Modifying
	public void customerConnectFixedArea(String fixedArea_Id,Integer parseInt);
	
	@Query(value="update Customer set fixedAreaId=null where fixedAreaId=?")
	@Modifying
	public void clearCustomer(String fixedArea_Id);
<<<<<<< HEAD
	
	public Customer findByTelephone(String telephone);
	
	@Query(value="update Customer set type =1 where telephone=?")
	@Modifying
	public void changeCustomerType(String telephone);
	
	public Customer findByTelephoneAndPassword(String telephone,String password);
	
	public Customer findByAddress(String address);
	
=======
>>>>>>> 6645a5796d582c6708852a194116047f00e92785
}
