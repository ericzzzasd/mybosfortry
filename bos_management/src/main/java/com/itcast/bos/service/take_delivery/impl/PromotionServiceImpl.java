package com.itcast.bos.service.take_delivery.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.print.attribute.standard.PageRanges;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.take_delivery.PromotionDao;
import com.itcast.bos.domain.page.PageBean;
import com.itcast.bos.domain.take_delivery.Promotion;
import com.itcast.bos.service.take_delivery.PromotionService;


@Service
@Transactional
public class PromotionServiceImpl implements PromotionService{
	
	@Autowired
	private PromotionDao promotionDao;

	@Override
	public void savePromotion(Promotion model) {
		promotionDao.save(model);
		
	}

	@Override
	public Page<Promotion> findPromotionPage(Pageable pageable) {
		Specification<Promotion> specification=new Specification<Promotion>() {
			
			@Override
			public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("status"),1);
				return predicate;
			}
		};
		Page<Promotion> page = promotionDao.findAll(specification, pageable);
		return page;
	}

	

	@Override
	public PageBean<Promotion> showPromotionPage(Integer page,Integer rows) {
		Pageable pageable=new PageRequest(page-1, rows);
		Page<Promotion> pageH = promotionDao.findAll(pageable);
		PageBean<Promotion> pageBean=new PageBean<>();
		pageBean.setRows(4);
		pageBean.setList(pageH.getContent());		
		pageBean.setTotalPage(pageH.getTotalPages());
		
		return pageBean;
	}

	@Override
	public Promotion findPromotion(Integer id) {
		
		return promotionDao.findOne(id);
	}

	@Override
	public void checkPromotion(Date date) {
		System.out.println("检测");
		promotionDao.checkPromotion(date);
		
	}

	
	
}
