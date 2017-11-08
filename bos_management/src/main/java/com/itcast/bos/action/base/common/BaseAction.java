package com.itcast.bos.action.base.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;

import com.itcast.bos.domain.base.Standard;
import com.opensymphony.xwork2.ModelDriven;

public  class BaseAction<T> implements ModelDriven<T> {

	
	protected T model;
	
	@Override
	public T getModel() {
		
		return model;
	}

	public BaseAction() {
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType=(ParameterizedType) (genericSuperclass);
		Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		try {
			model = modelClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			
		}
	}

	protected Integer page;
	protected Integer rows;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	
	public Map<String,Object> getMap(Page<T> p){
		Map<String,Object> map=new HashMap<>();
		map.put("total", p.getTotalElements());
		map.put("rows", p.getContent());
		return map;
	}


	
}
