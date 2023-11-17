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
                    <div class="home-content-history">
                        <h3> Lịch sử giao dịch</h3>
                        <c:forEach items="${CarOrderList}" var="c">
                                <div class="carorder">
                                    <div class="history">
                                        <c:forEach items="${CarIMG}" var="cimg">
                                            <c:if test="${c.carID.carID == cimg.carID.carID}">
                                                <img src="./img/Xe/${c.carID.carID}/${cimg.carIMG}">
                                            </c:if>
                                        </c:forEach>
                                        <div class="info">

                                            <div style="display: flex">
                                                <p class="carname">${c.carID.carName}</p>
                                                <p style="margin-left: 20px;" class="badge <c:choose>
                                                       <c:when test = "${c.status == 0}">
                                                           bg-warning
                                                       </c:when>
                                                       <c:when test = "${c.status == 1}">
                                                           bg-success
                                                       </c:when>
                                                       <c:when test = "${c.status == 2}">
                                                           bg-secondary
                                                       </c:when>
                                                   </c:choose>
                                                   "><c:choose>
                                                        <c:when test = "${c.status == 0}">
                                                            Chờ xét duyệt
                                                        </c:when>
                                                        <c:when test = "${c.status == 1}">
                                                            Đã đặt cọc
                                                        </c:when>
                                                        <c:when test = "${c.status == 2}">
                                                            Bị hủy
                                                        </c:when>
                                                    </c:choose></p>

                                            </div>

                                            <p class="carorderCode">Mã đơn hàng: ${c.carorderCode}</p>
                                        </div>
                                    </div>
                                    <div>
                                        <i style="font-size: 30px; margin-top: -10px;"class="fa-solid fa-angle-right"></i>
                                    </div>
                                </div>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>


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