package com.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.bos.domain.base.TakeTime;

public interface TakeTimeDao extends JpaRepository<TakeTime,Integer> {
	
}
