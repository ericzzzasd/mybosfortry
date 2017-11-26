package com.itcast.bos.service.take_delivery.impl;




import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.take_delivery.WayBillDao;
import com.itcast.bos.domain.take_delivery.WayBill;
import com.itcast.bos.service.take_delivery.WayBillService;



@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

	private static final Logger Logger1=Logger.getLogger(WayBillServiceImpl.class);
	
	@Autowired
	private WayBillDao wayBillDao;
	
	@Override
	public String quickSaveWayBill(WayBill model) {
		
		try{
			System.out.println(model.getWayBillNum());
			wayBillDao.save(model);
			Logger1.info("运单生成成功"+model.getWayBillNum());
			return "success";
		}catch(Exception e){
			Logger1.error("运单生成失败"+model.getWayBillNum(), e);
			e.printStackTrace();
			return "false";
			
		}
		
	
		
	}

	@Override
	public Page<WayBill> showAllWayBill(Pageable pageable) {
		Page<WayBill> findAll = wayBillDao.findAll(pageable);
		return findAll;
	}

	
	
	
	@Override
	public WayBill showWayBill(String wayBillNum) {
		 WayBill wayBill=wayBillDao.findByWayBillNum(wayBillNum);
		
		return wayBill;
	}

	@Override
	public void saveWayBill(WayBill model) {
		try{
			WayBill wayBill = wayBillDao.findByWayBillNum(model.getWayBillNum());
			if(wayBill!=null&&wayBill.getId()!=null){
				Integer id = wayBill.getId();
				BeanUtils.copyProperties(wayBill, model);
				wayBill.setId(id);
				wayBillDao.save(model);
			}else{
				wayBillDao.save(model);
			}	
		}catch(Exception e){
			
		}
	}

}
