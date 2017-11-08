package com.itcast.bos.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.rtf.RTFEditorKit;

import org.apache.cxf.transport.http.AbstractHTTPDestination.BackChannelConduit;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.itcast.bos.action.base.common.BaseAction;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class StandardAction extends BaseAction<Standard>{
	@Autowired
	private StandardService standardService;

	@Action(value="saveStandard",results={@Result(name="success",type="redirect",location="/pages/base/standard.html")})
	public String saveStandard(){
		standardService.saveStandard(model);
		return "success";
	}
	
	@Action(value="showPageStandard",results={@Result(name="success",type="json")})
	public String showPaegStandard(){
		Pageable pagea=new  PageRequest(page-1,rows);
		Page<Standard> p=standardService.showPaegStandard(pagea);
		
		Map<String, Object> map = getMap(p);
		
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}

	@Action(value="showAllStandard",results={@Result(name="success",type="json")})
	public String showAllStandard(){
		List<Standard> list=standardService.showAllStandard();
		ServletActionContext.getContext().getValueStack().push(list);
		return "success";
	}
}
