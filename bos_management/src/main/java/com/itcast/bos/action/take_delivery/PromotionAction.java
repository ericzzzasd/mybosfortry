package com.itcast.bos.action.take_delivery;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.FileUtils;
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

import com.itcast.bos.action.common.BaseAction;
import com.itcast.bos.domain.take_delivery.Promotion;
import com.itcast.bos.service.take_delivery.PromotionService;
import com.itcast.bos.utils.UUidUtils;



@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion>{
	
	@Autowired
	private PromotionService promotionService;
	
	private File imgFile;
	
	private String imgFileFileName;
	
	private String imgFileContentType;
	
	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	@Action(value="image_upload",results={@Result(name="success",type="json")})
	public String image_load(){
	
		try{
			String realPath = ServletActionContext.getServletContext().getRealPath("upload");
			String contextPath = ServletActionContext.getRequest().getContextPath();

			String uuid = UUidUtils.getUUID();
			String fileExt = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));

			String randomFileName=uuid+fileExt;
			FileUtils.copyFile(imgFile, new File(realPath+"/"+randomFileName));
			
			Map<String,Object> map=new HashMap<>();
			map.put("error",0);
			map.put("url", contextPath+"/upload/"+randomFileName);
			ServletActionContext.getContext().getValueStack().push(map);
		}catch(Exception e){
			
		}
			return "success";
		
	}
	
	@Action(value="image_manage",results={@Result(name="success",type="json")})
	public String image_management(){
		String realPath = ServletActionContext.getServletContext().getRealPath("upload");
		String contextPath = ServletActionContext.getRequest().getContextPath();
		String currentPath = contextPath+"/upload/";
		File currentPathFile=new File(realPath);
		
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
			
		List<HashMap> fileList = new ArrayList<HashMap>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				HashMap<String, Object> hash = new HashMap<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path","");
		result.put("current_dir_path", "upload");
		result.put("current_url", currentPath);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		ServletActionContext.getContext().getValueStack().push(result);
		return "success";
	}
	
	private File titleImgFile;
	
	private String titleImgFileFileName;
	
	public File getTitleImgFile() {
		return titleImgFile;
	}

	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}

	public String getTitleImgFileFileName() {
		return titleImgFileFileName;
	}

	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}

	@Action(value="savePromotion",results={@Result(name="success",type="redirect",location="./pages/take_delivery/promotion_add.html")})
	public String savePromotion(){
		try{
			String contextPath = ServletActionContext.getRequest().getContextPath();
			String realPath = ServletActionContext.getServletContext().getRealPath("upload");
			String uuid=UUidUtils.getUUID();
			String fileEtx=titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
			String randamName=uuid+fileEtx;
			
			FileUtils.copyFile(titleImgFile,new File(realPath+"/"+randamName));
			model.setTitleImg(contextPath+"/upload/"+randamName);
			promotionService.savePromotion(model);
			
		}catch(Exception e){
		
		}
		return "success";
		
	}
	
	@Action(value="findPromotionPage",results={@Result(name="success",type="json")})
	public String findPromotionPage(){
		Pageable pageable=new PageRequest(page-1,rows);
		Page<Promotion> p=promotionService.findPromotionPage(pageable);
		System.out.println(p.getContent());
		Map<String, Object> map = getMap(p);
		ServletActionContext.getContext().getValueStack().push(map);
		return "success";
		
	}
}
