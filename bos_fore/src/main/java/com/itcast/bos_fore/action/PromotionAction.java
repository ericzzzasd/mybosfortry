package com.itcast.bos_fore.action;
import java.io.File;

import java.io.FileOutputStream;

import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.bos.domain.Constant.Constant;
import com.itcast.bos.domain.page.PageBean;
import com.itcast.bos.domain.take_delivery.Promotion;
import com.itcast.bos_fore.action.common.BaseAction;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Controller
public class PromotionAction extends BaseAction<Promotion> {
	
	
	
	@Action(value="showPagePromotion",results={@Result(name="success",type="json")})
	public String showPagePromotion(){
		PageBean<Promotion> pageBean = WebClient.create("http://localhost:8080/bos_management/services/promotionService/promotion?page="+page+"&rows="+rows).accept(MediaType.APPLICATION_JSON).get(PageBean.class);
		ServletActionContext.getContext().getValueStack().push(pageBean);		
		
		return "success";
	} 
	
	@Action(value="showPromotion_Detail")
	public void showPromotion_Detail(){
		try{
			ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
			String realPath = ServletActionContext.getServletContext().getRealPath("freemaker");
			File file=new File(realPath+"/"+model.getId()+".html");
			if(file.exists()){
				
				FileUtils.copyFile(file, ServletActionContext.getResponse().getOutputStream());
			}else{
		
				Promotion promotion = WebClient.create(Constant.PROMOTION_MANAGEMENT+"/promotion/"+model.getId()).accept(MediaType.APPLICATION_JSON).get(Promotion.class);
				Configuration configuration=new Configuration(Configuration.VERSION_2_3_22);
				configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/template"));
				Template template = configuration.getTemplate("promotion_defail.ftl");
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("promotion",promotion);
				
				template.process(map,new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
				
				
				ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
				FileUtils.copyFile(file, ServletActionContext.getResponse().getOutputStream());
			}
		}catch(Exception e){
			
		}
	}
	
}
