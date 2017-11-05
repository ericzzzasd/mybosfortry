package com.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.base.StandardDao;
import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.service.base.StandardService;
@Service
@Transactional
public class StandardSerivceImpl implements StandardService{
	@Autowired
	private StandardDao standardDao;
	
	@Override
	public void saveStandard(Standard standard) {
		standardDao.save(standard);
		
	}

}
