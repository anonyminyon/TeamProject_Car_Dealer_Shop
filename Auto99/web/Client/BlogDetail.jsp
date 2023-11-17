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
        <link rel="stylesheet" href="assets/css/BlogDetail.css">
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
            <div class="page-heading about-heading header-text">
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <input type="hidden" name="blogID" value="${blogID}" required>
                    ${Blog.content}
                    <c:if test="${blogID!=6 && blogID!=7}">
                        <div class="createdOn_blog">
                        <fmt:parseDate var="parsedDate" value="${Blog.createdOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                        <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy'|'hh:mm:ss a" />
                        <p><i class="fa-regular fa-clock"></i>${formattedDate}</p>
                    </div>
                    <div class="more"><p><i class="fa-solid fa-grip-lines-vertical"></i> TIN TỨC KHÁC </p></div>
                                <c:forEach items="${ListAds}" var="c">
                        <div class="adlist">
                            <a href="blogdetail?blogID=${c.blogID}"><i class="fas fa-chevron-right"></i> ${c.title.toUpperCase()}</a>
                        </div>
                    </c:forEach>
                    </c:if>
                    
                </div>
                <div class="col-lg-1">
                </div>
                <div class="col-lg-3">
                    <c:forEach var="AdSubCategory" items="${ListTTaKM}" varStatus="loop">
                        <div class="blog-item">
                            <a class="dir" href="bloghomelist?blogID=${AdSubCategory.blogID}">${AdSubCategory.title}</a>
                            <c:forEach var="Ad" items="${adList}">
                                <c:if test="${Ad.parentID==AdSubCategory.parentID}">
                                    <div class="detail">
                                        <a href="blogdetail?blogID=${Ad.blogID}">
                                            <h3>${Ad.title}</h3>
                                            <div class="createdOn">
                                                <fmt:parseDate var="parsedDate" value="${Ad.createdOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                                                <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy hh:mm a" />
                                                <p>${formattedDate}
                                                </p>
                                            </div>

                                            <hr>
                                            <div class="box-image">
                                                <img class="img-service" src="img/Blog/${Ad.blogIMG}" alt="lỗi">
                                            </div>
                                        </a>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:forEach>
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