<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Wish shop project">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <%--    <link href="${pageContext.request.contextPath}/resources/plugins/font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet" type="text/css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/product_responsive.css">
</head>
<body>
<c:url var="current_uri" value="/?id=${product.id}"></c:url>
<div class="super_container">

    <jsp:include page="../fragment/index_navbar.jsp"></jsp:include>

    <!-- Home -->

    <div class="home">
        <div class="home_background parallax-window" data-parallax="scroll" data-image-src="${pageContext.request.contextPath}/resources/images/product.jpg" data-speed="0.8"></div>
        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="home_container">
                        <div class="home_content">
                            <div class="home_title">Shoes</div>
                            <div class="breadcrumbs">
                                <ul>
                                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                                    <li>Product</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Product -->

    <div class="product">
        <div class="container">
            <div class="row product_row">

                <!-- Product Image -->
                <div class="col-lg-5 offset-lg-1">
                    <div class="product_image">
                        <div class="product_image_large"><img src="${product.imageUrl}" alt=""></div>
<%--                        <div class="product_image_thumbnails d-flex flex-row align-items-start justify-content-start">--%>
<%--                            <div class="product_image_thumbnail" style="background-image:url(${pageContext.request.contextPath}/resources/images/product_image_1.jpg)" data-image="images/product_image_1.jpg"></div>--%>
<%--                        </div>--%>
                    </div>
                </div>

                <!-- Product Content -->
                <div class="col-lg-5">
                    <div class="product_content">
                        <div class="product_name">${product.name}</div>
                        <div class="product_price"><fmt:formatNumber value = "${product.price}"
                                                                     type = "number"/><small style="font-size: .8rem; margin-left: 2px;">vnd</small></div>
                        <div class="rating rating_4" data-rating="4">
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                        </div>
                        <!-- In Stock -->
                        <c:set value="${product.quantity > 0}" var="isInStock"></c:set>
                        <div class="in_stock_container">
                            <c:choose>
                                <c:when test="${isInStock}">
                                    <div class="in_stock in_stock_true"></div>
                                    <span>in stock</span>
                                </c:when>
                                <c:otherwise>
                                    <div class="in_stock"></div>
                                    <span>out of stock</span>
                                </c:otherwise>
                            </c:choose>
                            |<span>Stock : ${product.quantity}</span>
                        </div>
                        <div class="product_text">
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quis quam ipsum. Pellentesque consequat tellus non tortor tempus, id egestas elit iaculis. Proin eu dui porta, pretium metus vitae, pharetra odio. Sed ac mi commodo, pellentesque erat eget, accumsan justo. Etiam sed placerat felis. Proin non rutrum ligula.</p>
                        </div>
                        <!-- Product Quantity -->
<%--                        <div class="product_quantity_container">--%>
<%--                            <span>Quantity</span>--%>
<%--                            <div class="product_quantity clearfix">--%>
<%--                                <input id="quantity_input" type="text" pattern="[0-9]*" value="1">--%>
<%--                                <div class="quantity_buttons">--%>
<%--                                    <div id="quantity_inc_button" class="quantity_inc quantity_control"><i class="fa fa-caret-up" aria-hidden="true"></i></div>--%>
<%--                                    <div id="quantity_dec_button" class="quantity_dec quantity_control"><i class="fa fa-caret-down" aria-hidden="true"></i></div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <!-- Product Size -->
                        <div class="product_size_container">
                            <div class="button cart_button">
                                <c:choose>
                                    <c:when test="${isInStock}">
                                        <a href="${pageContext.request.contextPath}/cart/add?id=${product.id}&redirect_uri=${current_uri}"
                                            onclick="alert('Added to stock')">
                                            add to cart</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a onclick="alert('Out of stock')">
                                            add to cart</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Reviews -->

            <div class="row">
                <div class="col off">
                    <div class="reviews">
                        <div class="reviews_title">reviews</div>
                        <div class="reviews_container">
                            <ul>
                                <!-- Review -->
                                <li class=" review clearfix">
                                    <div class="review_image"><img src="${pageContext.request.contextPath}/resources/images/review_1.jpg" alt=""></div>
                                    <div class="review_content">
                                        <div class="review_name"><a href="#">Maria Smith</a></div>
                                        <div class="review_date">Nov 29, 2017</div>
                                        <div class="rating rating_4 review_rating" data-rating="4">
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                        </div>
                                        <div class="review_text">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quis quam ipsum. Pellentesque consequat tellus non tortor tempus, id egestas elit iaculis. Proin eu dui porta, pretium metus vitae, pharetra odio. Sed ac mi commodo, pellentesque erat eget, accumsan justo. Etiam sed placerat felis. Proin non rutrum ligula. </p>
                                        </div>
                                    </div>
                                </li>
                                <!-- Review -->
                                <li class=" review clearfix">
                                    <div class="review_image"><img src="${pageContext.request.contextPath}/resources/images/review_2.jpg" alt=""></div>
                                    <div class="review_content">
                                        <div class="review_name"><a href="#">Maria Smith</a></div>
                                        <div class="review_date">Nov 29, 2017</div>
                                        <div class="rating rating_4 review_rating" data-rating="4">
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                        </div>
                                        <div class="review_text">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quis quam ipsum. Pellentesque consequat tellus non tortor tempus, id egestas elit iaculis. Proin eu dui porta, pretium metus vitae, pharetra odio. Sed ac mi commodo, pellentesque erat eget, accumsan justo. Etiam sed placerat felis. Proin non rutrum ligula. </p>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <jsp:include page="../fragment/index_footer.jsp"></jsp:include>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<%--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js" integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script><script src="${pageContext.request.contextPath}/resources/plugins/parallax-js-master/parallax.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/parallax/3.1.0/parallax.min.js" integrity="sha512-/6TZODGjYL7M8qb7P6SflJB/nTGE79ed1RfJk3dfm/Ib6JwCT4+tOfrrseEHhxkIhwG8jCl+io6eaiWLS/UX1w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="${pageContext.request.contextPath}/resources/js/product_custom.js"></script>
</body>
</html>