package com.itcast.bos.service.take_delivery;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillService {

	String quickSaveWayBill(WayBill model);

	Page<WayBill> showAllWayBill(Pageable pageable);

	WayBill showWayBill(String wayBillNum);

	void saveWayBill(WayBill model);
	
}
