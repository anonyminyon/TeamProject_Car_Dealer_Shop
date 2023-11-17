<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">

        <title>Trang thông tin chính thức của Auto99</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="assets/css/Header.css">
        <link rel="stylesheet" href="assets/css/Footer.css">
        <link rel="stylesheet" href="assets/css/Profile.css">

        <!--Flatpickr-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var currentUrl = window.location.href;
                var lastSegment = currentUrl.split('/').pop();

                var menuLinks = document.querySelectorAll("a");

                menuLinks.forEach(function (link) {
                    if (link.getAttribute("href") === lastSegment) {
                        link.classList.add("active");
                    }
                });
            });
        </script>
    </head>

    <body>

        <!-- ***** Preloader Start ***** -->
        <div id="preloader">
            <div class="jumper">
                <div></div>
                <div></div>
                <div></div>
            </div>
        </div>
        <!-- ***** Preloader End ***** -->

        <!-- Header -->
        <%@include file="../Client/Header.jsp" %>

        <div class="page-heading about-heading header-text">
            <div class="page-heading about-heading header-text">
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-md-1"></div>
                <%@include file="../Client/CustomerProfileNav.jsp"%>
                <div class="col-md-7">
                    <div class="home-content">
                        <div class="profile">
                            <p>Thông tin cá nhân</p>
                            <div class="changeinfo" id="show-emp" data-toggle="modal" data-target="#ModalUP${client.getClientID()}"> Chỉnh sửa thông tin</div>
                        </div>
                        <div class="info">
                            <c:choose>
                                <c:when test="${client.getNoID().length() == 8}">
                                    <label class="half-width">Tên Công Ty</label>
                                    <p>${client.getClientName()}</p>
                                </c:when>
                                <c:otherwise>
                                    <label class="half-width">Họ và tên</label>
                                    <p>${client.getClientName()}</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="info">
                            <label class="half-width">Email</label>
                            <p>${client.getEmail()}</p>
                        </div>
                        <div class="info">
                            <label class="half-width">Số điện thoại</label>
                            <p>${client.getPhoneNumber()}</p>
                        </div>
                        <c:if test="${client.getNoID().length() != 8}">
                            <div class="info">
                                <label class="half-width">Giới tính</label>
                                <p>${client.isGender() ? "Nam" : "Nữ"}</p>
                            </div>
                        </c:if>
                        <c:if test="${client.getNoID().length() != 8}">
                            <div class="info">
                                <label class="half-width">Ngày sinh</label>
                                <fmt:parseDate var="parsedDate" value="${client.getDateOfBrith()}" pattern="yyyy-MM-dd" />
                                <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy" />
                                <p>${formattedDate}</p>
                            </div>
                        </c:if>
                        <div class="info">
                            <c:choose>
                                <c:when test="${client.getNoID().length() == 8}">
                                    <label class="half-width">Mã số doanh nghiệp</label>
                                    <p>${client.getNoID()}</p>
                                </c:when>
                                <c:otherwise>
                                    <label class="half-width">Số CCCD</label>
                                    <p>${client.getNoID()}</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <hr>
                        <div class="changepass">
                            <label class="half-width">Mật khẩu</label>
                            <a href="changepassword"> Đổi mật khẩu</a>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <form action="profile" method="POST">
            <div class="modal fade" id="ModalUP${client.getClientID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                 data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">

                        <div class="modal-body">
                            <div class="row">
                                <div class="form-group  col-md-12">
                                    <span class="thong-tin-thanh-toan">
                                        <h5>Chỉnh sửa thông tin cá nhân</h5>
                                    </span>
                                </div>
                            </div>
                            <div class="row">

                                <div class="form-group col-md-6">
                                    <label class="control-label">Họ và tên</label>
                                    <input class="form-control" name="name" type="text" required value="${client.getClientName()}">
                                </div>
                                <div class="form-group col-md-6 ">
                                    <label for="exampleSelect1" class="control-label">Số điện thoại</label>
                                    <input class="form-control" type="text" required value="${client.getPhoneNumber()}" readonly style="cursor: not-allowed">
                                </div>
                                <c:if test="${client.getNoID().length() != 8}">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Ngày sinh</label>
                                        <fmt:parseDate var="parsedDate" value="${client.getDateOfBrith()}" pattern="yyyy-MM-dd" />
                                        <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy" />
                                        <input class="form-control flatpickr" type="text" name="dob" value="${formattedDate}" required>
                                    </div>
                                </c:if>
                                <c:if test="${client.getNoID().length() != 8}">
                                    <div class="form-group col-md-6 ">
                                        <label class="control-label">Giới tính</label>
                                        <select class="form-control" name="gender">
                                            <option value="${client.isGender()}">${client.isGender() ? "Nam" : "Nữ"}</option>
                                            <option value="${!client.isGender()}">${!client.isGender() ? "Nam" : "Nữ"}</option>
                                        </select>
                                    </div>
                                </c:if>
                                <div class="form-group col-md-6">
                                    <c:choose>
                                        <c:when test="${client.getNoID().length() == 8}">
                                            <label class="control-label">Mã số doanh nghiệp</label>
                                            <input class="form-control" type="text" required value="${client.getNoID()}" readonly style="cursor: not-allowed">
                                        </c:when>
                                        <c:otherwise>
                                            <label class="control-label">Số CCCD</label>
                                            <input class="form-control" type="text" required value="${client.getNoID()}" readonly style="cursor: not-allowed">
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <script>
                                    const flatpickrInputs = document.querySelectorAll(".flatpickr");


                                    flatpickrInputs.forEach((input) => {
                                        flatpickr(input, {
                                            enableTime: false,
                                            dateFormat: "d-m-Y"
                                        });
                                    });
                                </script>
                                <div class="form-group col-md-12">
                                    <label for="exampleSelect1" class="control-label">Email</label>
                                    <input class="form-control" type="text" required value="${client.getEmail()}" readonly style="cursor: not-allowed">
                                </div>  
                            </div>
                            <BR>
                            <button class="btn btn-save" type="submit" style="color:white; background-color: red">Lưu lại</button>
                            <a class="btn btn-cancel" data-dismiss="modal" style="color:white; background-color: grey">Hủy bỏ</a>
                            <BR>
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <!-- Footer-->
        <%@include file="../Client/Footer.jsp" %>
        <!-- Footer End Hear-->


        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Additional Scripts -->
        <script src="assets/js/custom.js"></script>
        <script src="assets/js/owl.js"></script>

        <!--Lazy Loading-->
        <script src="assets/js/lazy.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    </body>

</html>