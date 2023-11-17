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
        <link rel="stylesheet" href="assets/css/HomeList.css">
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

        <!-- Header Ends Hear -->
        <div class="page-heading about-heading header-text">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="text-content">
                            <input type="hidden" name="blogID" value="${blogID}" required>
                            <c:choose>
                                <c:when test="${blogID != null}">
                                    <h2>${Blog.title}</h2>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="category" items="${ListTTaKM}" varStatus="loop">
                                        <c:if test="${category.blogCategoryID.blogCategoryID==3 && loop.index==0}">
                                            <h2>${category.blogCategoryID.blogCategory}</h2>
                                        </c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

                            <div style="display:flex; justify-content: center; align-items: center; text-align: center;">
                                <h4><a href="home">Trang chủ</a></h4>
                                <h4> > </h4>
                                <c:forEach var="category" items="${ListTTaKM}" varStatus="loop">
                                    <c:if test="${loop.index==0}">
                                        <h4><a href="bloghomelist">${category.blogCategoryID.blogCategory} </a></h4>
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

        <!-- List Blog -->

        <div class="home-content" style="margin-top: 100px">
            <div class="container">
                <div class="row">
                    <c:choose>
                        <c:when test="${blogID != null}">
                            <c:forEach items="${ListBlogSP}" var="subCategory">
                                <a href="blogdetail?blogID=${subCategory.blogID}">
                                    <div class="col-xl-4 col-md-6 service-item">
                                        <div class="box-image">
                                            <img class="img-service" src="img/1px.png" lazy-src="img/Blog/${subCategory.blogIMG}" alt="${subCategory.title}">
                                        </div>
                                        <div class="down-content">
                                            <h2>${subCategory.title}</h2>
                                            <p class="description">${subCategory.description}</p>
                                            <div style="display:flex; justify-content: space-between; text-align: center; align-content: center">
                                                <div class="createdOn">
                                                    <fmt:parseDate var="parsedDate" value="${subCategory.createdOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                                                    <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy hh:mm a" /> ${formattedDate}
                                                </div>
                                                <div class="more"><a href="bloghomelist?blogID=${blogID}">${Blog.title.toUpperCase()}</a></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="category" items="${ListTTaKM}" varStatus="loop">
                                <c:if test="${loop.index==0}">
                                    <c:forEach var="subCategory" items="${AllBlogs}">
                                        <a href="blogdetail?blogID=${subCategory.blogID}">
                                            <div class="col-xl-4 col-md-6 service-item">
                                                <div class="box-image">
                                                    <img class="img-service" src="img/1px.png" lazy-src="img/Blog/${subCategory.blogIMG}" alt="${subCategory.title}">
                                                </div>
                                                <div class="down-content">
                                                    <h2>${subCategory.title}</h2>
                                                    <p class="description">${subCategory.description}</p>
                                                    <div style="display:flex; justify-content: space-between; text-align: center; align-content: center">
                                                        <div class="createdOn">
                                                            <fmt:parseDate var="parsedDate" value="${subCategory.createdOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                                                            <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy hh:mm a" /> ${formattedDate}
                                                        </div>
                                                        <div class="more"><a href="bloghomelist">${category.blogCategoryID.blogCategory.toUpperCase()}</a></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Ends List Blog -->


            <div id="mainlink">
                <c:choose>
                    <c:when test="${endPage <= 7}">
                        <c:forEach begin="1" end="${endPage}" var="i">
                            <c:set var="link" value="bloghomelist?index=${i}" />
                            <c:if test="${blogID != null}">
                                <c:set var="link" value="${link}&blogID=${blogID}" />
                            </c:if>
                            <a class="${index == i ? 'active' : ' '}" href="${link}">${i}</a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${index <= 4}">
                                <c:forEach begin="1" end="7" var="i">
                                    <c:set var="link" value="bloghomelist?index=${i}" />
                                    <c:if test="${blogID != null}">
                                        <c:set var="link" value="${link}&blogID=${blogID}" />
                                    </c:if>
                                    <a class="${index == i ? 'active' : ' '}" href="${link}">${i}</a>
                                </c:forEach>
                                <a href="bloghomelist?blogID=${blogID}&index=${endPage}">»</a>
                            </c:when>
                            <c:when test="${index >= endPage - 3}">
                                <a href="bloghomelist?blogID=${blogID}&index=1">«</a>
                                <c:forEach begin="${endPage - 6}" end="${endPage}" var="i">
                                    <c:set var="link" value="bloghomelist?index=${i}" />
                                    <c:if test="${blogID != null}">
                                        <c:set var="link" value="${link}&blogID=${blogID}" />
                                    </c:if>
                                    <a class="${index == i ? 'active' : ' '}" href="${link}">${i}</a>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <a href="bloghomelist?blogID=${blogID}&index=1">«</a>
                                <c:forEach begin="${index - 3}" end="${index + 3}" var="i">
                                    <c:set var="link" value="bloghomelist?index=${i}" />
                                    <c:if test="${blogID != null}">
                                        <c:set var="link" value="${link}&blogID=${blogID}" />
                                    </c:if>
                                    <a class="${index == i ? 'active' : ' '}" href="${link}">${i}</a>
                                </c:forEach>
                                <a href="bloghomelist?blogID=${blogID}&index=${endPage}">»</a>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
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

    </body>

</html>