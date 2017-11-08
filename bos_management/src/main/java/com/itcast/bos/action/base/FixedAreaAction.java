package com.itcast.bos.action.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.service.base.FixedAreaService;

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
	
}
