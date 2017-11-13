package com.itcast.bos.action.base;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.bos.domain.base.TakeTime;
import com.itcast.bos.service.base.TakeTimeService;

@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class TakeTimeAction {
	@Autowired
	private TakeTimeService takeTimeService;
	
	@Action(value="findAllTakeTime",results={@Result(name="success",type="json")})
	public String findAllTakeTime(){
		List<TakeTime> list=takeTimeService.findAllTakeTime();
		ServletActionContext.getContext().getValueStack().push(list);
		return "success";
	}
		
}
