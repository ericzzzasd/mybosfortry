package com.test;

import org.apache.log4j.net.SocketNode;
import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.parent.Son;

import oracle.net.aso.s;

public class Test1{
	
	@Test
	public void test() throws SchedulerException{
	
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail build = JobBuilder.newJob(HelloJob.class).withIdentity("job1","group1").build();
		
		SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)).build();
		
		scheduler.scheduleJob(build, trigger);
		
		scheduler.start();
		
		
	}
	
	public static void main(String[] args) {
		Scheduler scheduler = null;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JobDetail build = JobBuilder.newJob(HelloJob.class).withIdentity("job1","group1").build();
		
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * ? * *")).build();
		
		
		try {
			scheduler.scheduleJob(build, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}