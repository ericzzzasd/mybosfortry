package com.itcast.bos_fore.action;

import java.util.Random;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.itcast.bos.domain.Constant.Constant;
import com.itcast.bos_fore.action.common.BaseAction;
import com.itcast.bos_fore.utils.MailUtils;
import com.itcast.bos_fore.utils.UUidUtils;
import com.itcast.crm.domain.Customer;


@Namespace("/")
@Controller
@ParentPackage("json-default")
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer>{
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String ,String> redisTemplate;
	
	private String checkTelCode;
	
	public String getCheckTelCode() {
		return checkTelCode;
	}

	public void setCheckTelCode(String checkTelCode) {
		this.checkTelCode = checkTelCode;
	}
	
	@Action(value="sendSms")
	public void sendSms(){	
		Random r=new Random();
		String s="";
		for (int i = 0; i <4; i++) {
			int j = r.nextInt(10);
			s=s+j;
		}
		 final String cs=s;
		 ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(),s);
		jmsTemplate.send("sms",new MessageCreator() {	
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(model.getTelephone()+"_"+cs);
				return message;
			}
		});
	}
	
	@Action(value="doRegister",results={@Result(name="success",location="../../signup-success.html"),@Result(name="false",location="../../signup.html")})
	public String doRegister(){
		String s = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
		if(!checkTelCode.equals(s)){
			
			return "false";
		}
		String uuid = UUidUtils.getUUID();
		System.out.println(uuid);
		System.out.println(model.getEmail());
		redisTemplate.opsForValue().set(model.getTelephone(),uuid);
		MailUtils.sendMail("速运快递", model.getTelephone(),model.getEmail(),uuid);
		model.setType(2);
		WebClient.create("http://localhost:9002/crm_management/services/customerService/saveCustomer").type(MediaType.APPLICATION_JSON).post(model);
		
		return "success";
	}
	
	private String activecode;
	
	public String getActivecode() {
		return activecode;
	}

	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}

	@Action(value="doActive")
	public void doActive(){
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		try{
			String s = redisTemplate.opsForValue().get(model.getTelephone());
			if(s==null||!activecode.equals(s)){
				ServletActionContext.getResponse().getWriter().write("激活码失效，或者以过期");;
			}
			Customer customer = WebClient.create("http://localhost:9002/crm_management/services/customerService/findCustomerByTelephone/"+model.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
			if(customer.getTelephone()==null||customer.getType()!=2){
				ServletActionContext.getResponse().getWriter().write("请勿重复或者恶意激活");
			}else{
				 WebClient.create(Constant.CRM_MANAGEMENT+"/customerService/changeCustomer/"+model.getTelephone()).type(MediaType.APPLICATION_JSON).put(null);
				 ServletActionContext.getResponse().getWriter().write("用户激活成功，请前去登录");
			}
		}catch(Exception e){
			
		}
	}
	@Action(value="login",results={@Result(name="success",location="../../index.html"),@Result(name="f",location="../../login.html")})
	public String login(){
		System.out.println(model.getTelephone());
		Customer customer = WebClient.create(Constant.CRM_MANAGEMENT+"/custoemr/login?telephone="+model.getTelephone()+"&passowrd="+model.getPassword()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
		if(customer!=null){
			ServletActionContext.getRequest().getSession().setAttribute("login", customer);
			return "success";
		}else{
			return "f";
		}
	}
	
}
