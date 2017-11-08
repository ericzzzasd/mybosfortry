package com.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itcast.bos.domain.base.Courier;

public interface CourierService {

	Page<Courier> showPageAllCourier(Pageable pagea);

	void saveCourier(Courier courier);

	Page<Courier> showPageAllSearchCourier(Specification<Courier> specification, Pageable pagea);

	void delCourier(List<Integer> list);

}
