package com.itcast.bos_fore.action;



import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.bos.domain.Constant.Constant;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.take_delivery.Order;
import com.itcast.bos_fore.action.common.BaseAction;
import com.itcast.bos_fore.utils.UUidUtils;
import com.itcast.crm.domain.Customer;

@ParentPackage("json-default")
@Controller
@Scope("prototype")
@Namespace("/")
public class OrderAction extends BaseAction<Order>{
	
	private String sendAreaInfo;
	
	private String recAreaInfo;
	
	public String getSendAreaInfo() {
		return sendAreaInfo;
	}

	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}

	public String getRecAreaInfo() {
		return recAreaInfo;
	}

	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}

	@Action(value="saveOrder",results={@Result(name="success",location="../../index.html")})
	public String saveOrder(){
		
		Area sendArea=getAreaByInfo(recAreaInfo);
		Area recArea=getAreaByInfo(sendAreaInfo);
		model.setSendArea(sendArea);
		model.setRecArea(recArea);
		model.setOrderType("1");
		model.setStatus("1");
		Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("login");
		model.setCustomer_id(customer.getId());
		String uuid = UUidUtils.getUUID();
		model.setOrderNum(uuid);
		model.setOrderTime(new Date());
		WebClient.create(Constant.ORDER_MANAGEMENT+"/order").type(MediaType.APPLICATION_JSON).post(model);
		return "success";
	}
	
	public Area getAreaByInfo(String info){
		Area area=new Area();
		String[] split = info.split("/");
		area.setProvince(split[0]);
		area.setCity(split[1]);
		area.setDistrict(split[2]);
		return area;
	}
}
