package com.itcast.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.xml.resolver.apps.resolver;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.base.AreaDao;
import com.itcast.bos.dao.base.FixedAreaDao;
import com.itcast.bos.dao.base.SubAreaDao;
import com.itcast.bos.dao.take_delivery.OrderDao;
import com.itcast.bos.dao.take_delivery.WorkBillDao;
import com.itcast.bos.domain.Constant.Constant;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.domain.base.SubArea;
import com.itcast.bos.domain.take_delivery.Order;
import com.itcast.bos.domain.take_delivery.WorkBill;
import com.itcast.bos.service.base.FixedAreaService;
import com.itcast.bos.service.take_delivery.OrderService;
import com.itcast.crm.domain.Customer;
@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	@Autowired
	private FixedAreaDao fixedAreaDao;
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private WorkBillDao workBillDao;
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private SubAreaDao subAreaDao;

	@Autowired
	private OrderDao orderDao;
	
	
	@Override
	public void saveOrder(Order order) {
		Area sendArea = order.getSendArea();
		Area recArea = order.getRecArea();
		Area  existSendArea=areaDao.findByProvinceAndCityAndDistrict(sendArea.getProvince(),sendArea.getCity(),sendArea.getDistrict());
		Area  existRecArea=areaDao.findByProvinceAndCityAndDistrict(recArea.getProvince(),recArea.getCity(),recArea.getDistrict());
		order.setSendArea(existSendArea);
		order.setRecArea(existRecArea);
		
		Customer customer = WebClient.create(Constant.CRM_MANAGEMENT+"/customer/address?address="+order.getSendAddress()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
		if(customer==null){
			List<SubArea> list=subAreaDao.findByArea(existSendArea);
			if(list!=null){
			for (SubArea subArea : list) {
				if(order.getSendAddress().contains(subArea.getKeyWords())||order.getSendAddress().contains(subArea.getAssistKeyWords())){
					FixedArea fixedArea = subArea.getFixedArea();
					 Set<Courier> couriers = fixedArea.getCouriers();
					 if(couriers.size()!=0) {
						Courier next = couriers.iterator().next();	
						order.setCourier(next);
						orderDao.save(order);
						sendAndSaveWorkBill(order);
						System.out.println("关键字录入");
						return ;
					}
				}
			}
		}
		}else{
			String fixedAreaId = customer.getFixedAreaId();
			FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
			 Set<Courier> couriers = fixedArea.getCouriers();
			 if(couriers.size()!=0) {
				Courier next = couriers.iterator().next();
				
				order.setCourier(next);
				orderDao.save(order);
				sendAndSaveWorkBill(order);
				System.out.println("地址选择录入");
				return ;
			}
		}
			 
			
		System.out.println("后台录入");
		order.setOrderType("2");
		orderDao.save(order);
		
	}


	
	
	public void sendAndSaveWorkBill(Order order){
		Courier courier = order.getCourier();
		WorkBill workBill=new WorkBill();
		workBill.setCourier(courier);
		workBill.setOrder(order);
		Random r=new Random();
		String s="";
		for (int i = 0; i <4; i++) {
			int j = r.nextInt(10);
			s=s+j;
		}
		s=s+"_"+order.getCourier().getTelephone();
		final String cs=s;
		workBill.setSmsNumber(s);
		workBill.setType("新");
		workBill.setBuildtime(new Date());
		workBill.setRemark(order.getRemark());
		workBill.setAttachbilltimes(0);
		workBill.setPickstate("1");
		workBillDao.save(workBill);
		jmsTemplate.send("informcourier",new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message=session.createTextMessage();
				message.setText(cs);
				return message;
			}
		});
	}




	@Override
	public Order showOrder(String orderNum) {
		Order order=orderDao.findByOrderNum(orderNum);
		return order;
	}
}
