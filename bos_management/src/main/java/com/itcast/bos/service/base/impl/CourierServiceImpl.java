package com.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.base.CourierDao;
import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.service.base.CourierService;
@Service
@Transactional
public class CourierServiceImpl implements CourierService{
	
	@Autowired
	private CourierDao courierDao;
	
	@Override
	public Page<Courier> showPageAllCourier(Pageable pagea) {
		Page<Courier> p = courierDao.findAll(pagea);
		return p;
	}

	@Override
	public void saveCourier(Courier courier) {
		courierDao.save(courier);
		
	}

	@Override
	public Page<Courier> showPageAllSearchCourier(Specification<Courier> specification, Pageable pagea) {
		Page<Courier> findAll = courierDao.findAll(specification, pagea);
		return findAll;
	}

	@Override
	public void delCourier(List<Integer> list) {
		for (Integer integer : list) {
			courierDao.delCourier(integer);
		}
		
		
	}

}
