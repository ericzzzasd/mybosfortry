package com.itcast.bos.dao.take_delivery;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itcast.bos.domain.take_delivery.Promotion;

public interface PromotionDao extends JpaRepository<Promotion,Integer>,JpaSpecificationExecutor<Promotion>{
	
	
	@Query(value="update Promotion set status =2 where ? > endDate")
	@Modifying
	public void checkPromotion(Date date);
}
