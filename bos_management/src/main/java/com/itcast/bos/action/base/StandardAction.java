package com.itcast.bos.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Actions
public class StandardAction extends ActionSupport implements ModelDriven<Standard>{
	@Autowired
	private StandardService standardService;
	
	private Standard standard=new Standard();	
	
	@Override
	public Standard getModel() {
		return standard;
	}
	
	@Action(value="saveStandard",results={@Result(name="success",type="redirect",location="/pages/base/standard.html")})
	public String saveStandard(){
		
		
		standardService.saveStandard(standard);
		return "success";
	}
	
}
