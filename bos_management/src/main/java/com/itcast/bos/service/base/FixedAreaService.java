package com.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void saveFixedArea(FixedArea model);

	Page<FixedArea> showPageFixedArea(Specification<FixedArea> specification, Pageable pagea);

}
