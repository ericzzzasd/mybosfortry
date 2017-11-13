package com.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itcast.bos.domain.base.Courier;

public interface CourierDao extends JpaRepository<Courier,Integer>,JpaSpecificationExecutor<Courier>{
	
	@Query(value="update Courier set deltag =0 where id = ?",nativeQuery=false)
	@Modifying
	public void delCourier(Integer integer);


}
