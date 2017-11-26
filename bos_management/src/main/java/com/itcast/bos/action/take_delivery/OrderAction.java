package com.itcast.bos.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.bos.action.common.BaseAction;
import com.itcast.bos.domain.take_delivery.Order;
import com.itcast.bos.service.take_delivery.OrderService;

@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class OrderAction extends BaseAction<Order>{
	
	@Autowired
	private OrderService orderService;

	@Action(value="showOrder",results={@Result(name="success",type="json")})
	public String showOrder(){
		Order order=orderService.showOrder(model.getOrderNum());
		Map<String, Object> map=new HashMap<>();
		map.put("order", order);
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}
}
