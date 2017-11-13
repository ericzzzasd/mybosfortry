package com.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.base.CourierDao;
import com.itcast.bos.dao.base.FixedAreaDao;
import com.itcast.bos.dao.base.TakeTimeDao;

import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.domain.base.TakeTime;
import com.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{
	@Autowired
	private FixedAreaDao fixedAreaDao;
	@Autowired
	private CourierDao courierDao;
	@Autowired
	private TakeTimeDao takeTimeDao;

	@Override
	public void saveFixedArea(FixedArea model) {
		fixedAreaDao.save(model);
		
	}

	@Override
	public Page<FixedArea> showPageFixedArea(Specification<FixedArea> specification, Pageable pagea) {
		Page<FixedArea> findAll = fixedAreaDao.findAll(specification, pagea);
		return findAll;
	}

	@Override
	public void FiexedAreaConnectCourierTakeTime(String id, String courierId, String takeTimeId) {
		FixedArea f = fixedAreaDao.findOne(id);
		TakeTime t = takeTimeDao.findOne(Integer.parseInt(takeTimeId));
		Courier c = courierDao.findOne(Integer.parseInt(courierId));
		
		f.getCouriers().add(c);
		c.setTakeTime(t);
	}
	
}
