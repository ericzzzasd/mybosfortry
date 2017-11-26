package com.itcast.bos.service.take_delivery;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.bos.domain.page.PageBean;
import com.itcast.bos.domain.take_delivery.Promotion;

@Produces("*/*")
public interface PromotionService {

	void savePromotion(Promotion model);

	Page<Promotion> findPromotionPage(Pageable pageable);
	
	public void checkPromotion(Date date);
	
	@GET
	@Path("/promotion")
	@Produces({"application/xml","application/json"})
	public PageBean<Promotion> showPromotionPage(@QueryParam("page")Integer page,@QueryParam("rows")Integer rows);
	
	
	@GET
	@Path("/promotion/{id}")
	@Produces({"application/xml","application/json"})
	public Promotion findPromotion(@PathParam("id") Integer id);
}
