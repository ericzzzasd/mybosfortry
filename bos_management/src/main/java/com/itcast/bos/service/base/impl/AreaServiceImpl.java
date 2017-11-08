package com.itcast.bos.service.base.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.base.AreaDao;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.service.base.AreaService;
import com.itcast.bos.utils.PinYin4jUtils;
@Service
@Transactional
public class AreaServiceImpl implements AreaService{
	
	@Autowired
	private AreaDao areaDao;

	@Override
	public void saveFileAreaForXlsFile(File file) throws FileNotFoundException, IOException {
		HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		List<Area> list=new ArrayList<>();
		for (Row row : sheet) {
			if(row.getRowNum()==0){
				continue;
			}
			if(row.getCell(0).getStringCellValue()==null&&"".equals(row.getCell(0).getStringCellValue())){
				continue;
			}
			//获得地区对应的信息通过解析xls
			Area area=new Area();
			area.setId(row.getCell(0).getStringCellValue());
			String province = row.getCell(1).getStringCellValue().substring(0,row.getCell(1).getStringCellValue().length()-1);
			area.setProvince(row.getCell(1).getStringCellValue());
			String city = row.getCell(2).getStringCellValue().substring(0,row.getCell(2).getStringCellValue().length()-1);
			area.setCity(row.getCell(2).getStringCellValue());
			String district = row.getCell(3).getStringCellValue().substring(0,row.getCell(3).getStringCellValue().length()-1);
			area.setDistrict(row.getCell(3).getStringCellValue());
			area.setPostcode(row.getCell(4).getStringCellValue());
			String citycode = PinYin4jUtils.hanziToPinyin(city,"");
			area.setCitycode(citycode);
			System.out.println(citycode);
			String s=province+city+district;
			String[] strings = PinYin4jUtils.getHeadByString(s);
			String shortcode="";
			for (String string : strings) {
				shortcode=shortcode+string;
			}
			area.setShortcode(shortcode);
			list.add(area);
		}
		
		areaDao.save(list);
	}

	@Override
	public void saveFileAreaForXlsxFile(File file) throws Exception{
		XSSFWorkbook xssfWorkbook=new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
		List<Area> list=new ArrayList<>();
		for (Row row : sheet) {
			if(row.getRowNum()==0){
				continue;
			}
			if(row.getCell(0).getStringCellValue()==null&&"".equals(row.getCell(0).getStringCellValue())){
				continue;
			}
			//获得地区对应的信息通过解析xlsx
			Area area=new Area();
			area.setId(row.getCell(0).getStringCellValue());
			String province = row.getCell(1).getStringCellValue().substring(0,row.getCell(1).getStringCellValue().length()-1);
			area.setProvince(row.getCell(1).getStringCellValue());
			String city = row.getCell(2).getStringCellValue().substring(0,row.getCell(2).getStringCellValue().length()-1);
			area.setCity(row.getCell(2).getStringCellValue());
			String district = row.getCell(3).getStringCellValue().substring(0,row.getCell(3).getStringCellValue().length()-1);
			area.setDistrict(row.getCell(3).getStringCellValue());
			area.setPostcode(row.getCell(4).getStringCellValue());
			String citycode = PinYin4jUtils.hanziToPinyin(city,"");
			area.setCitycode(citycode);
			System.out.println(citycode);
			String s=province+city+district;
			String[] strings = PinYin4jUtils.getHeadByString(s);
			String shortcode="";
			for (String string : strings) {
				shortcode=shortcode+string;
			}
			area.setShortcode(shortcode);
			list.add(area);
		}
		
		areaDao.save(list);
		
	}


	@Override
	public Page<Area> showAllPageArea(Pageable pagea, Specification<Area> specification) {
		Page<Area> p = areaDao.findAll(specification,pagea);
		return p;
	}

	
}
