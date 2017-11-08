package com.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itcast.bos.domain.base.Area;

public interface AreaDao extends JpaRepository<Area, String>,JpaSpecificationExecutor<Area>{

}
