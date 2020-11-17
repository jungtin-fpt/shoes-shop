<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Header -->

<header class="header">
  <div class="header_inner d-flex flex-row align-items-center justify-content-start">
    <div class="logo"><a href="${pageContext.request.contextPath}/">jung.tin</a></div>
    <nav class="main_nav">
      <ul>
        <li><a href="${pageContext.request.contextPath}/">home</a></li>
        <li><a href="${pageContext.request.contextPath}/products">dashboard</a></li>
      </ul>
    </nav>
    <div class="header_content ml-auto">
      <div class="search header_search">
        <form action="#">
          <input type="search" class="search_input" required="required" disabled>
          <button type="submit" id="search_button" class="search_button"><img src="${pageContext.request.contextPath}/resources/images/magnifying-glass.svg" alt=""></button>
        </form>
      </div>
      <div class="shopping">
        <!-- Cart -->
        <a href="${pageContext.request.contextPath}/cart">
          <div class="cart">
            <img src="${pageContext.request.contextPath}/resources/images/shopping-bag.svg" alt="">

            <c:if test="${sessionScope.get('cart') != null}">
              <c:set var="quantity" value="0"></c:set>
              <c:forEach var="item" items="${sessionScope.get('cart').items}">
                <c:set var="quantity" value="${quantity + item.value}"></c:set>
              </c:forEach>

              <div class="cart_num_container">
                <div class="cart_num_inner">
                    <div class="cart_num">${quantity}</div>
                </div>
              </div>
            </c:if> <%--bagde number--%>

          </div>
        </a>
      </div>
    </div>

    <div class="burger_container d-flex flex-column align-items-center justify-content-around menu_mm"><div></div><div></div><div></div></div>
  </div>
</header>

<!-- Menu -->

<div class="menu d-flex flex-column align-items-end justify-content-start text-right menu_mm trans_400">
  <div class="menu_close_container"><div class="menu_close"><div></div><div></div></div></div>
  <div class="logo menu_mm"><a href="#">jung.tin</a></div>
  <div class="search">
    <form action="#">
      <input type="search"
             value="disabled_by_me"
             class="search_input menu_mm"
             required="required"
             disabled>
      <button type="submit" id="search_button_menu" class="search_button menu_mm" disabled><img class="menu_mm" src="${pageContext.request.contextPath}/resources/images/magnifying-glass.svg" alt=""></button>
    </form>
  </div>
  <nav class="menu_nav">
    <ul class="menu_mm">
      <li class="menu_mm"><a href="${pageContext.request.contextPath}/">home</a></li>
      <li class="menu_mm"><a href="${pageContext.request.contextPath}/products">dashboard</a></li>
    </ul>
  </nav>
</div>