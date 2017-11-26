package com.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.base.SubArea;

public interface SubAreaDao extends JpaRepository<SubArea,String>{
	
	List<SubArea> findByArea(Area sendArea);

	
}
