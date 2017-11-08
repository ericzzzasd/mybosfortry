package com.itcast.bos.service.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itcast.bos.domain.base.Area;

public interface AreaService {

	void saveFileAreaForXlsFile(File file) throws Exception;

	void saveFileAreaForXlsxFile(File file) throws Exception;

	Page<Area> showAllPageArea(Pageable pagea, Specification<Area> specification);

}
