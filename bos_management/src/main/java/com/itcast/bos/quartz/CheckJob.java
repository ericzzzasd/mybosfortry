package com.itcast.bos.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.itcast.bos.service.take_delivery.PromotionService;

public class CheckJob implements Job{
	@Autowired
	private PromotionService promotionServce;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		
	}
	
	
	
}
