package com.itcast.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillDao extends JpaRepository<WayBill,Integer>{

	WayBill findByWayBillNum(String wayBillNum);

}
