<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<jsp:include page="../fragment/dashboard_header.jsp"></jsp:include>
<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <jsp:include page="../fragment/dashboard_sidebar.jsp"></jsp:include>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <jsp:include page="../fragment/dashboard_topbar.jsp"></jsp:include>

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Product Form</h1>
                <c:if test="${error != null}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        ${error}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <form action="${pageContext.request.contextPath}/products/process" method="post">
                    <input type="hidden" name="id" value="${product.id}">
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="name">Product name</label>
                            <input type="text" class="form-control" id="name"
                                   name="name"
                                   value="${product.name}"
                                   aria-describedby="nameHelp" required>
                            <small id="nameHelp" class="form-text text-muted">Must be unique name</small>
                            <!-- thêm class  is-invalid / is-valid vào class ở form-control -->
                            <!-- <div class="invalid-feedback">
                              Looks good!
                            </div> -->
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="image_url">Image URL</label>
                            <input type="text" class="form-control" id="image_url"
                                   name="image_url"
                                   value="${product.imageUrl}"
                                   required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="price">Price</label>
                            <input type="text" class="form-control" id="price"
                                   name="price" aria-describedby="priceHelp"
                                   value="${product.price}"
                                   required>
                            <small id="priceHelp" class="form-text text-muted">Only using dot '.' for fraction</small>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="quantity">Quantity</label>
                            <input type="number" class="form-control" id="quantity"
                                   name="quantity" value="${product.quantity}" min="1" aria-describedby="quantityHelp"
                                   required>
                            <small id="quantityHelp" class="form-text text-muted">Minimum value is 1</small>
                        </div>
                    </div>
                    <button class="btn btn-primary" type="submit">Submit</button>
                </form>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- End of Main Content -->

        <jsp:include page="../fragment/dashboard_footer.jsp"></jsp:include>

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<%--
    Thằng này không chuyển về Fragment được --> lỗi font
--%>
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../fragment/dashboard_script.jsp"></jsp:include>

</body>
</html>