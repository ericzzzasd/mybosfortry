<link rel="stylesheet" type="text/css" href="css/promotion_detail.css">
<div class="container promotions" >
	<div class="col-md-2 prolist">
		<h5 class="title"><a href="#/promotion"><strong>返回促销列表</strong></a></h5>
		<img src="${promotion.titleImg}" class="img-responsive">
	</div>
	<div class="col-md-10 procontent">
		<h5 class="title"></h5>
		<div class="intro">
			<h5 class="title">${promotion.title}</h5>
			<p> 活动时间</p>
			
			<p>  开始时间:${promotion.startDate?string("yyyy-MM-dd")} - 结束时间:${promotion.endDate?string("yyyy-MM-dd")}     </p>
		</div>
		<div class="partline clearfix"></div>
		<div class="promotionbox">
			${promotion.description}
		</div>
	</div>
</div>
