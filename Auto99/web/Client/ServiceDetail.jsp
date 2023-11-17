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
        <link rel="stylesheet" href="assets/css/ServiceDetail.css">
        <link rel="stylesheet" href="assets/css/Header.css">
        <link rel="stylesheet" href="assets/css/Footer.css">

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
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="text-content">
                            <input type="hidden" name="blogID" value="${blogID}" required>
                            <c:if test="${blogID != null}">
                                <h2>${Blog.title}</h2>
                            </c:if>

                            <div style="display:flex; justify-content: center; align-items: center; text-align: center;">
                                <h4><a href="home">Trang chủ</a></h4>
                                <h4> > </h4>
                                <c:forEach var="category" items="${ListDV}" varStatus="loop">
                                    <c:if test="${loop.index==0}">
                                        <h4><a href="servicehomelist">${category.blogCategoryID.blogCategory} </a></h4>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${blogID != null}">
                                    <h4> > </h4>
                                    <h4><a href="${Blog.url}">${Blog.title}</a></h4>
                                    </c:if>

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-md-12">
                    ${Blog.content}
                    <div class="createdOn_blog">
                        <fmt:parseDate var="parsedDate" value="${Blog.createdOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                        <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy'|'hh:mm:ss a" />
                        <p><i class="fa-regular fa-clock"></i>${formattedDate}</p>
                    </div>
                    <div class="form"><a href="serviceform">ĐĂNG KÍ DỊCH VỤ</a></div>
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

        <script src="tinymce/tinymce.min.js"></script>
        <script src="assets/Staff/js/TinyMCE.js"></script>

    </body>
</html>