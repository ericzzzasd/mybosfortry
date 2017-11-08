package com.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.bos.domain.base.Standard;

public interface StandardService {

	void saveStandard(Standard standard);

	Page<Standard> showPaegStandard(Pageable pagea);

	List<Standard> showAllStandard();

}
