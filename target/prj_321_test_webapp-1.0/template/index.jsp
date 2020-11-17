<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/index_header.jsp"></jsp:include>
<body>
<c:url var="current_uri" value="/"></c:url>
<div class="super_container">

	<jsp:include page="fragment/index_navbar.jsp"></jsp:include>

	<!-- Home -->

	<div class="home">

		<!-- Home Slider -->

		<div class="home_slider_container">
			<div class="owl-carousel owl-theme home_slider">

				<!-- Home Slider Item -->
				<div class="owl-item">
					<div class="home_slider_background" style="background-image:url(https://storage.googleapis.com/fastdate-image/nike_shoes_1.jpg)"></div>
					<div class="home_slider_content">
						<div class="home_slider_content_inner">
							<div class="home_slider_subtitle">Nike | Jordan</div>
							<div class="home_slider_title">Union LA x Jordan Zoom</div>
						</div>
					</div>
				</div>

				<!-- Home Slider Item -->
				<div class="owl-item">
					<div class="home_slider_background" style="background-image:url(https://storage.googleapis.com/fastdate-image/nike_shoes_2.jpg)"></div>
					<div class="home_slider_content">
						<div class="home_slider_content_inner">
							<div class="home_slider_subtitle">Nike</div>
							<div class="home_slider_title">Air Force 1 High</div>
						</div>
					</div>
				</div>

				<!-- Home Slider Item -->
				<div class="owl-item">
					<div class="home_slider_background" style="background-image:url(https://storage.googleapis.com/fastdate-image/nike_shoes_3.jpg)"></div>
					<div class="home_slider_content">
						<div class="home_slider_content_inner">
							<div class="home_slider_subtitle">Nike | Jordan</div>
							<div class="home_slider_title">Jordan 6</div>
						</div>
					</div>
				</div>

			</div>

			<!-- Home Slider Nav -->

			<div class="home_slider_next d-flex flex-column align-items-center justify-content-center"><img src="${pageContext.request.contextPath}/resources/images/arrow_r.png" alt=""></div>

			<!-- Home Slider Dots -->

			<div class="home_slider_dots_container">
				<div class="container">
					<div class="row">
						<div class="col">
							<div class="home_slider_dots">
								<ul id="home_slider_custom_dots" class="home_slider_custom_dots">
									<li class="home_slider_custom_dot active">01.<div></div></li>
									<li class="home_slider_custom_dot">02.<div></div></li>
									<li class="home_slider_custom_dot">03.<div></div></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- New Arrivals -->

	<div class="arrivals">
		<div class="container">
			<div class="row">
				<div class="col">
					<div class="section_title_container text-center">
						<div class="section_subtitle">only the best</div>
						<div class="section_title">new arrivals</div>
					</div>
				</div>
			</div>
			<div class="row products_container">

				<c:forEach items="${products}" var="product">
					<!-- Product -->
					<div class="col-lg-4 col-md-6 product_col">
						<div class="product">
							<div class="product_image">
								<img src="${product.imageUrl}" alt="">
							</div>
							<div class="rating rating_4">
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
							</div>
							<div class="product_content clearfix">
								<div class="product_info">
									<div class="product_name"><a href="${pageContext.request.contextPath}?id=${product.id}">${product.name}</a></div>
									<div class="product_price"><fmt:formatNumber value = "${product.price}"
									                                             type = "number"/><small style="font-size: .8rem; margin-left: 2px;">vnd</small></div>
								</div>
								<c:if test="${product.quantity > 0}">
									<div class="product_options">
										<a class="product_fav product_option"
										   onclick="alert('Added into cart')"
										   href="${pageContext.request.contextPath}/cart/add?id=${product.id}&redirect_uri=${current_uri}">+</a>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>

			</div>
		</div>
	</div>

	<!-- Gallery -->

	<div class="gallery">
		<div class="gallery_image" style="background-image:url(https://storage.googleapis.com/fastdate-image/gallery.jpg)"></div>
		<div class="container">
			<div class="row">
				<div class="col">
					<div class="gallery_title text-center">
						<ul>
							<li><a href="#">#jungtin</a></li>
							<li><a href="#">#minkthi3n</a></li>
							<li><a href="#">#shoes</a></li>
						</ul>
					</div>
					<div class="gallery_text text-center">We are jungtin shoes shop, welcome welcome</div>
					<!-- <div class="button gallery_button"><a href="#">submit</a></div> -->
				</div>
			</div>
		</div>
		<div class="gallery_slider_container">

			<!-- Gallery Slider -->
			<div class="owl-carousel owl-theme gallery_slider">

				<!-- Gallery Item -->
				<div class="owl-item gallery_item">
					<a class="colorbox" href="https://storage.googleapis.com/fastdate-image/shoes_shop_1.jpg">
						<img src="https://storage.googleapis.com/fastdate-image/shoes_shop_1.jpg" alt="">
					</a>
				</div>

				<!-- Gallery Item -->
				<div class="owl-item gallery_item">
					<a class="colorbox" href="https://storage.googleapis.com/fastdate-image/shoes_shop_2.jpg">
						<img src="https://storage.googleapis.com/fastdate-image/shoes_shop_2.jpg" alt="">
					</a>
				</div>

				<!-- Gallery Item -->
				<div class="owl-item gallery_item">
					<a class="colorbox" href="https://storage.googleapis.com/fastdate-image/shoes_shop_3.jpg">
						<img src="https://storage.googleapis.com/fastdate-image/shoes_shop_3.jpg" alt="">
					</a>
				</div>

				<!-- Gallery Item -->
				<div class="owl-item gallery_item">
					<a class="colorbox" href="https://storage.googleapis.com/fastdate-image/shoes_shop_4.jpg">
						<img src="https://storage.googleapis.com/fastdate-image/shoes_shop_4.jpg" alt="">
					</a>
				</div>

				<!-- Gallery Item -->
				<div class="owl-item gallery_item">
					<a class="colorbox" href="https://storage.googleapis.com/fastdate-image/shoes_shop_5.jpg">
						<img src="https://storage.googleapis.com/fastdate-image/shoes_shop_5.jpg" alt="">
					</a>
				</div>

				<!-- Gallery Item -->
				<div class="owl-item gallery_item">
					<a class="colorbox" href="https://storage.googleapis.com/fastdate-image/shoes_shop_6.jpg">
						<img src="https://storage.googleapis.com/fastdate-image/shoes_shop_6.jpg" alt="">
					</a>
				</div>

			</div>
		</div>
	</div>

	<jsp:include page="fragment/index_footer.jsp"></jsp:include>
</div>

<jsp:include page="fragment/index_script.jsp"></jsp:include>
</body>
</html>