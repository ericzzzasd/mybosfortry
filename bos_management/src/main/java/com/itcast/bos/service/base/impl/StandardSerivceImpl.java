package com.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<Standard> showPaegStandard(Pageable pagea) {
		Page<Standard> p=standardDao.findAll(pagea);
		return p;
	}

	@Override
	public List<Standard> showAllStandard() {
		List<Standard> list = standardDao.findAll();
		return list;
	}

}
