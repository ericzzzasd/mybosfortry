package com.itcast.bos.action.take_delivery;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itcast.bos.action.common.BaseAction;
import com.itcast.bos.domain.take_delivery.WayBill;
import com.itcast.bos.service.take_delivery.WayBillService;


@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class WayBillAction extends BaseAction<WayBill>{
	
	
	
	@Autowired
	private WayBillService wayBillService;

	@Action(value="quickSaveWayBill",results={@Result(name="success",type="json")})
	public String quickSaveWayBill(){
		String result=wayBillService.quickSaveWayBill(model);
	
		Map<String,Object> map=new HashMap<>();
		
		map.put("result",result);
		return "success";
	}
	
	
	@Action(value="showAllWayBill",results={@Result(name="success",type="json")})
	public String showAllWayBill(){
		Pageable pageable=new PageRequest(page-1, rows);
		Page<WayBill> p=wayBillService.showAllWayBill(pageable);
		Map<String, Object> map = getMap(p);
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}
	
	@Action(value="showWayBill",results={@Result(name="success",type="json")})
	public String showWayBill(){
		WayBill wayBill=wayBillService.showWayBill(model.getWayBillNum());
		String jsonString = JSONObject.toJSONString(wayBill);
		Object parse = JSON.parse(jsonString);
		Map<String, Object> map=new HashMap<>();
		map.put("waybill", parse);
	
		
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}
	
	@Action(value="saveWayBill",results={@Result(name="success",type="redirect",location="/pages/take_delivery/waybill.html")})
	public String saveWayBill(){
		
		if(model.getOrder()==null||model.getOrder().getId()==null){
			model.setOrder(null);
		}
		wayBillService.saveWayBill(model);
		
		return "success";
	}
}
