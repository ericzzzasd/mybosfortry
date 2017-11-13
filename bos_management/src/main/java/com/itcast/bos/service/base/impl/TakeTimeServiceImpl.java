package com.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.base.TakeTimeDao;
import com.itcast.bos.domain.base.TakeTime;
import com.itcast.bos.service.base.TakeTimeService;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService{
	@Autowired
	private TakeTimeDao takeTimeDao;

	@Override
	public List<TakeTime> findAllTakeTime() {
		List<TakeTime> findAll = takeTimeDao.findAll();
		return findAll;
	}

}
