package com.itcast.bos.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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

import com.itcast.bos.action.base.common.BaseAction;
import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.service.base.CourierService;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class CourierAction extends BaseAction<Courier>{
	@Autowired
	private CourierService courierService;
	
	
	private String cs;
	
	public void setCs(String cs) {
		this.cs = cs;
	}

	/*@Action(value="showPageAllCourier",results={@Result(name="success",type="json")})
	public String showPageAllCourier(){
		Pageable pagea=new PageRequest(page-1, rows);
		Page<Courier> p=courierService.showPageAllCourier(pagea);
		Map<String, Object> map=new HashMap<>();
		map.put("total",p.getTotalElements());
		map.put("rows",p.getContent());
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}*/

	@Action(value="saveCourier",results={@Result(name="success",type="redirect",location="/pages/base/courier.html")})
	public String saveCourier(){
		
		courierService.saveCourier(model);
		return "success";
	}
	
	@Action(value="showPageSearchCourier",results={@Result(name="success",type="json")})
	public String showPageAllSearchCourier(){
	
		Specification<Courier> specification=new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<>();
				
				if(StringUtils.isNotBlank(model.getCourierNum())){
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class),model.getCourierNum());
					list.add(p1);
				}
				if(StringUtils.isNotBlank(model.getType())){
					Predicate p2 = cb.equal(root.get("type").as(String.class),model.getType());
					list.add(p2);
				}
				if(StringUtils.isNotBlank(model.getCompany())){
					Predicate p3 = cb.like(root.get("company").as(String.class),"%"+model.getCompany()+"%");
					list.add(p3);
				}
				Join<Courier, Standard> standardJoin = root.join("standard",
						JoinType.INNER);
				if(model.getStandard()!=null&& StringUtils.isNotBlank(model.getStandard().getName())){
					
					Predicate p4 = cb.like(standardJoin.get("name").as(String.class),"%"+model.getStandard().getName()+"%");
					list.add(p4);
				}
				Predicate and = cb.and(list.toArray(new Predicate[0]));
				
				return and;
			}
		}; 
		
		Pageable pagea=new PageRequest(page-1, rows);
		Page<Courier> p=courierService.showPageAllSearchCourier(specification,pagea);
		
		Map<String, Object> map = getMap(p);
		
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}
	
	
	@Action(value="delCourier",results={@Result(name="success",type="redirect",location="./pages/base/courier.html")})
	public String delCourier(){
		
		String[] split = cs.split(",");
		List<Integer> list=new ArrayList<Integer>();
		for (int i = 0; i < split.length; i++) {
			list.add(Integer.parseInt(split[i]));
		}
		courierService.delCourier(list);
		return "success";
	}
}
