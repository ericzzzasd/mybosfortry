package com.itcast.bos.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itcast.bos.action.common.BaseAction;
import com.itcast.bos.domain.Constant.Constant;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.service.base.FixedAreaService;
import com.itcast.crm.domain.Customer;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea>{
	@Autowired
	private  FixedAreaService fixedAreaService;
	
	@Action(value="saveFixedArea",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String saveFixedArea(){
		fixedAreaService.saveFixedArea(model);
		return "success";
	}	
	
	@Action(value="showPageFixedArea",results={@Result(name="success",type="json")})
	public String showPageFixedArea(){
		Specification<FixedArea> specification=new Specification<FixedArea>() {		
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<>();
				if(model.getId()!=null&&!"".equals(model.getId())){
					Predicate p1 = cb.like(root.get("id").as(String.class),"%"+model.getId()+"%");
					list.add(p1);
				}
				if(model.getCompany()!=null&&!"".equals(model.getCompany())){
					Predicate p2 = cb.like(root.get("company").as(String.class),"%"+model.getCompany()+"%");
					list.add(p2);
				}
				if(model.getFixedAreaName()!=null&&!"".equals(model.getFixedAreaName())){
					Predicate p3 = cb.like(root.get("fixedAreaName").as(String.class),"%"+model.getFixedAreaName()+"%");
					list.add(p3);
				}
				Predicate and = cb.and(list.toArray(new Predicate[0]));
				return and;
			}
		};
		Pageable pagea=new PageRequest(page-1, rows);
		Page<FixedArea> p=fixedAreaService.showPageFixedArea(specification,pagea);
		Map<String, Object> map = getMap(p);
		System.out.println(p.getContent());
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}
	
	
	@Action(value="findNoConnectCustomer",results={@Result(type="json",name="success")})
	public String findNoConnectCustomer(){
		Collection<? extends Customer> collection = WebClient.create(Constant.CRM_MANAGEMENT+"/noConnectCustomer").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		ServletActionContext.getContext().getValueStack().push(collection);
		return "success";
		
	}
	
	private String fixedArea_Id;
	
	
	public String getFixedArea_Id() {
		return fixedArea_Id;
	}

	public void setFixedArea_Id(String fixedArea_Id) {
		this.fixedArea_Id = fixedArea_Id;
	}
	@Action(value="findConnectCustomer",results={@Result(type="json",name="success")})
	public String findConnectCustomer(){
		System.out.println(fixedArea_Id);
		Collection<? extends Customer> collection = WebClient.create(Constant.CRM_MANAGEMENT+"/connectCustomer/"+fixedArea_Id)
			.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.getCollection(Customer.class);
		System.out.println(collection);
		ServletActionContext.getContext().getValueStack().push(collection);
		return "success";
	}
	
	private String[] customerIds;

	public String[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	@Action(value="connectFixedArea",results={@Result(type="redirect",location="/pages/base/fixed_area.html",name="success")})
	public String customerConnectFixedArea(){
		String cs="";
		for (int i = 0; i < customerIds.length; i++) {
			 if(i!=customerIds.length-1){
				 cs=cs+customerIds[i]+",";
			 }else if(i==0){
				 cs=customerIds[i]+",";
			 }else{
				 cs=cs+customerIds[i];
			 }
		}
		
		String  s=Constant.CRM_MANAGEMENT+"/connectFixedArea?fixedArea_Id="+model.getId()+"&customer_Ids="+cs;
		System.out.println(s);
		WebClient.create(s).put(null);
		
		return "success";
			
	}
	
	private String takeTimeId;
	private String courierId; 
	
	public String getTakeTimeId() {
		return takeTimeId;
	}

	public void setTakeTimeId(String takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	public String getCourierId() {
		return courierId;
	}

	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}

	
	@Action(value="fiexedAreaConnectCourierTakeTime",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
	public String  FiexedAreaConnectCourierTakeTime(){
		
		fixedAreaService.FiexedAreaConnectCourierTakeTime(model.getId(),courierId,takeTimeId);
		return "success";
	}
	
}
