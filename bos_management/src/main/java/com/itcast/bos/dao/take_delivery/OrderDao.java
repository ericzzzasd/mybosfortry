package com.itcast.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.bos.domain.take_delivery.Order;

public interface OrderDao extends JpaRepository<Order,Integer>{

	Order findByOrderNum(String orderNum);

}
