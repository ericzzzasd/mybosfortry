package com.itcast.bos.service.base.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


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

	@Override
	public List<Courier> findNoConnectCourier() {
	
		Specification<Courier> specification=new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.isEmpty(root.get("fixedAreas").as(Set.class));
				return predicate;
			}
		};
		
		List<Courier> findAll = courierDao.findAll(specification);
		return findAll;
	}

}
