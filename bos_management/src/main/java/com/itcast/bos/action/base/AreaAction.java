package com.itcast.bos.action.base;

import java.io.File;
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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itcast.bos.action.common.BaseAction;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.service.base.AreaService;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{

	@Autowired
	private AreaService areaService;
	
	private File file;
	
	private String fileContentType;
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	@Action(value="saveFileArea",results={@Result(name="success",type="redirect",location="./pages/base/area.html")})
	public String saveFileAreaForFile(){
		
		System.out.println(fileContentType);
		try {
			if(fileContentType.endsWith("excel")){
					areaService.saveFileAreaForXlsFile(file);
			}else{
				areaService.saveFileAreaForXlsxFile(file);
			}
			return "success";
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return "success";
		
		}
	
	@Action(value="showPageArea",results={@Result(name="success",type="json")})
	public String showAllPageArea(){
		Pageable pagea=new PageRequest(page-1, rows);
		Specification<Area> specification=new Specification<Area>() {
			
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<>();
				if(model.getProvince()!=null&&!"".equals(model.getProvince())){
					Predicate p1 = cb.like(root.get("province").as(String.class),"%"+model.getProvince()+"%");
					
					list.add(p1);
				}
				if(model.getCity()!=null&&!"".equals(model.getCity())){
					Predicate p2 = cb.like(root.get("city").as(String.class),"%"+model.getCity()+"%");
				
					list.add(p2);
				}
				if(model.getDistrict()!=null&&!"".equals(model.getDistrict())){
					Predicate p3 = cb.like(root.get("district").as(String.class),"%"+model.getDistrict()+"%");
				
					list.add(p3);
				}
				if(model.getCitycode()!=null&&!"".equals(model.getCitycode())){
					Predicate p4 = cb.like(root.get("citycode").as(String.class),"%"+model.getCitycode()+"%");
				
					list.add(p4);
				}
				if(model.getShortcode()!=null&&!"".equals(model.getShortcode())){
					Predicate p5 = cb.like(root.get("shortcode").as(String.class),"%"+model.getShortcode()+"%");
				
					list.add(p5);
				}
				Predicate and = cb.and(list.toArray(new Predicate[0]));
				return and;
			}
		};
		
		Page<Area> p=areaService.showAllPageArea(pagea,specification);
		
		Map<String, Object> map = getMap(p);
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
	}
	
	
}
