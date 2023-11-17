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
        <link rel="stylesheet" href="assets/css/HomePage.css">
        <link rel="stylesheet" href="assets/css/Header.css">
        <link rel="stylesheet" href="assets/css/owl.css">
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

        <!-- Page Content -->
        <!-- Banner Starts Here -->
        <div class="banner header-text">

            <c:forEach items="${ListBlog}" var="category" varStatus="lb">
                <c:if test="${category.blogCategoryID.blogCategoryID==4}">
                    <div class="owl-banner owl-carousel">
                        <c:forEach var="subCategory" items="${AllBlogsByPI[lb.index]}">
                            <c:if test="${subCategory.parentID == category.parentID && subCategory.blogCategoryID.blogCategoryID != category.blogCategoryID.blogCategoryID}">
                                <a href="blogdetail?blogID=${subCategory.blogID}">
                                    <div class="banner-item">
                                        <img src="img/1px.png" lazy-src="img/Blog/${subCategory.blogIMG}">
                                    </div>
                                </a>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
            </c:forEach>
        </div>


        <!-- Banner Ends Here -->

        <!-- CarList -->
        <div class="products">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-heading">
                            <h1>KHÁM PHÁ CÁC DÒNG XE</h1>
                        </div>
                    </div>
                    <div class="tbox container">
                        <div class="col-md-12">
                            <div class="col-md-12 list_car_type">
                                <ul>
                                    <c:forEach var="c" items="${ListCD}" varStatus="loop">
                                        <c:if test="${loop.index == 0}">
                                            <li class="active" style="width: 20%;">
                                                <a>${c.design}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${loop.index > 0}">
                                            <li style="width: 20%;">
                                                <a>${c.design}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                    <li style="width: 20%;" id="myLi">
                                        <a href="productlist">Xem thêm </a>
                                    </li>

                                    <script>
                                        // Lấy thẻ li và thẻ a bằng cách sử dụng id
                                        var liElement = document.getElementById('myLi');
                                        var aElement = liElement.querySelector('a');

                                        // Thêm sự kiện click vào thẻ li
                                        liElement.addEventListener('click', function () {
                                            // Khi thẻ li được bấm, tự động kích hoạt sự kiện click trên thẻ a
                                            aElement.click();
                                        });
                                    </script>
                                </ul>
                            </div>
                        </div>
                    </div>



                    <div class="cbox container">
                        <c:forEach var="c" items="${ListCD}" varStatus="designStatus">
                            <div value="${c.design}" id="${c.design}">
                                <div class="row owl-theme owl-carousel text-center">
                                    <c:forEach var="a" items="${AllCarsByDesign[designStatus.index]}">
                                        <div class="product">
                                            <a href="product?carID=${a.carID}">
                                                <div class="tin">
                                                    <div class="p1"> Động cơ ${a.engineType}</div>
                                                    <div class="p2"> Dung tích ${a.cylinderCapacity} cc</div>
                                                </div>
                                                <div class="box-image">
                                                    <c:forEach var="carImages" items="${AllCarImages}">
                                                        <c:forEach var="cimg" items="${carImages}">
                                                            <c:if test="${cimg.carID.carID == a.carID}">
                                                                <img class="image-car image-index" src="img/1px.png" lazy-src="img/Xe/${a.carID}/${cimg.carIMG}" alt="${a.carName}">
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:forEach>
                                                    <c:forEach var="carSubImages" items="${AllCarSubImages}">
                                                        <c:forEach var="csimg" items="${carSubImages}">
                                                            <c:if test="${csimg.carID.carID == a.carID}">
                                                                <img class="image-car image-hover" src="img/1px.png" lazy-src="img/Xe/${a.carID}/${csimg.carSubIMG}" alt="${a.carName}">
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:forEach>
                                                </div>
                                                <div class="info">
                                                    <h2>${a.carName}</h2>
                                                    <div>
                                                        <fmt:formatNumber value="${a.price}" type="number" pattern="#,###" />
                                                        <span>VNĐ</span>
                                                    </div>
                                                </div>
                                                <div class="bin">
                                                    <div class="p1">${a.numberOfSeat} chỗ</div>
                                                    <div class="p2">${a.gear}</div>
                                                </div>
                                            </a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>
                <script>
                    let listCarType = document.querySelectorAll('.list_car_type ul li');

                    // Lấy tất cả các div có id tương ứng với c.design
                    let designElements = document.querySelectorAll('.cbox.container > div');

                    designElements.forEach(function (designElement, index) {
                        if (index === 0) {
                            // Bỏ lớp "hide" cho div đầu tiên
                            designElement.classList.remove('hide');
                        } else {
                            // Thêm lớp "hide" cho các div còn lại
                            designElement.classList.add('hide');
                        }
                    });
                    // Xử lý sự kiện khi chọn kiểu xe
                    listCarType.forEach(function (li) {
                        li.addEventListener('click', function () {
                            // Xóa lớp 'active' từ tất cả các phần tử <li> kiểu xe
                            listCarType.forEach(function (item) {
                                item.classList.remove('active');
                            });

                            // Thêm lớp 'active' vào phần tử <li> kiểu xe được nhấp
                            this.classList.add('active');

                            // Lấy kiểu xe được chọn
                            let selectedType = this.textContent.trim();

                            // Lặp qua tất cả các div có id tương ứng với c.design
                            designElements.forEach(function (designElement) {
                                let designId = designElement.getAttribute('value');

                                // Kiểm tra xem id của div có phù hợp với kiểu xe được chọn
                                if (selectedType === designId) {
                                    designElement.classList.remove('hide');
                                } else {
                                    designElement.classList.add('hide');
                                }
                            });
                        });
                    });
                </script>

            </div>
        </div>
        <!-- CarList Ends Hear -->

        <!--Application-->
        <div class="section-cta">
            <div class="cta-box">
                <div class="cta-item">
                    <a href="bloghomelist?blogID=19">

                        <span class="bg-khuyen_mai_image"></span>
                        <p class="cta-item-text">Khuyến mãi</p>
                    </a>
                </div>
                <div class="cta-item">
                    <a href="costestimate">

                        <span class=" bg-bang_gia_image"></span>
                        <p class="cta-item-text">Dự toán chi phí</p>
                    </a>
                </div>
                <div class="cta-item">
                    <a href="servicetestride">
                        <span class="bg-dang_ki_lai_thu_image"></span>
                        <p class="cta-item-text">Đăng ký lái thử</p>
                    </a>
                </div>
            </div>
        </div>
        <!--Application Ends Hear-->

        <!--Service -->
        <div class="service">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-heading">
                            <h1>DỊCH VỤ</h1>

                        </div>
                    </div>
                    <div class="tbox container">
                        <div class="col-md-12">
                            <div class="col-md-12 list_service_type">
                                <ul>
                                    <c:forEach var="bc" items="${ListBC}">
                                        <c:if test="${bc.blogCategoryID==2}">
                                            <c:set var="firstCategoryFound" value="false" />
                                            <c:forEach var="b" items="${ListBlog}">
                                                <c:if test="${b.blogCategoryID.blogCategoryID==2}">
                                                    <li style="width: 33%;" <c:if test="${!firstCategoryFound}">class="active"</c:if>>
                                                        <a>${b.title}</a>
                                                    </li>
                                                    <c:set var="firstCategoryFound" value="true" />
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="sbox container">
                        <c:forEach var="category" items="${ListBlog}" varStatus="lb">
                            <c:if test="${category.blogCategoryID.blogCategoryID==2}">
                                <c:set var="categoryCount" value="${ParentIDCountMap[category.parentID]}" />
                                <c:if test="${categoryCount > 1}">
                                    <div data-value="${category.title}" class="service-parentID">
                                        <div class="row owl-clients owl-carousel text-center">
                                            <c:forEach var="subCategory" items="${AllBlogsByPI[lb.index]}">
                                                <c:if test="${subCategory.parentID == category.parentID && subCategory.blogCategoryID.blogCategoryID != category.blogCategoryID.blogCategoryID}">
                                                    <div class="service-item">
                                                        <div class="box-image">
                                                            <a href="servicedetail?blogID=${subCategory.blogID}">
                                                                <img class="img-service" src="img/1px.png" lazy-src="img/Blog/${subCategory.blogIMG}" alt="${subCategory.title}">
                                                            </a>
                                                        </div>
                                                        <div class="down-content">
                                                            <a href="servicedetail?blogID=${subCategory.blogID}">
                                                                <h2>${subCategory.title}</h2>
                                                                <p class="description">${subCategory.description}</p>
                                                                <div class="more"><a href="servicehomelist">XEM THÊM <i class="fas fa-chevron-right"></i></a></div>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </div>

                    <script>
                        let listServiceType = document.querySelectorAll('.list_service_type ul li');
                        let blogParentIDElements = document.querySelectorAll('.sbox.container > div.service-parentID');

                        blogParentIDElements.forEach(function (parentID, index) {
                            if (index === 0) {
                                parentID.classList.remove('hide');
                            } else {
                                parentID.classList.add('hide');
                            }
                        });

                        listServiceType.forEach(function (li) {
                            li.addEventListener('click', function () {
                                listServiceType.forEach(function (item) {
                                    item.classList.remove('active');
                                });

                                this.classList.add('active');

                                let selectedType = this.textContent.trim();

                                blogParentIDElements.forEach(function (serviceElement) {
                                    let bscId = serviceElement.getAttribute('data-value');

                                    if (selectedType === bscId) {
                                        serviceElement.classList.remove('hide');
                                    } else {
                                        serviceElement.classList.add('hide');
                                    }
                                });
                            });
                        });
                    </script>


                </div>
            </div>
        </div>
        <!--     Services Ends Hear-->

        <!--     Blogs -->
        <div class="blog">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-heading">
                            <h1>TIN TỨC & KHUYẾN MÃI</h1>

                        </div>
                    </div>
                    <div class="tbox container">
                        <div class="col-md-12">
                            <div class="col-md-12 list_blog_type">
                                <ul>
                                    <c:forEach var="bc" items="${ListBC}">
                                        <c:if test="${bc.blogCategoryID==3}">
                                            <c:set var="firstCategoryFound" value="false" />
                                            <c:forEach var="b" items="${ListBlog}">
                                                <c:if test="${b.blogCategoryID.blogCategoryID==3}">
                                                    <li style="width: 33%;" <c:if test="${!firstCategoryFound}">class="active"</c:if>>
                                                        <a>${b.title}</a>
                                                    </li>
                                                    <c:set var="firstCategoryFound" value="true" />
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="bbox container">
                        <c:forEach var="category" items="${ListBlog}" varStatus="lb">
                            <c:if test="${category.blogCategoryID.blogCategoryID==3}">
                                <c:set var="categoryCount" value="${ParentIDCountMap[category.parentID]}" />
                                <c:if test="${categoryCount > 1}">
                                    <div data-value="${category.title}" class="service-parentID">
                                        <div class="container">
                                            <div class="row">
                                                <div class="col-lg-6">
                                                    <c:forEach var="subCategory" items="${AllBlogsByPI[lb.index]}" varStatus="loop">
                                                        <c:if test="${subCategory.parentID == category.parentID && subCategory.blogCategoryID.blogCategoryID != category.blogCategoryID.blogCategoryID}">
                                                            <c:if test="${loop.index == 0}">
                                                                <div class="blog-item" style="width: 100%;">
                                                                    <a href="blogdetail?blogID=${subCategory.blogID}">
                                                                        <div class="box-image">
                                                                            <img class="img-service" style="height: 400px;" src="img/1px.png" lazy-src="img/Blog/${subCategory.blogIMG}" alt="${subCategory.title}">
                                                                        </div>
                                                                        <div class="down-content">
                                                                            <h2>${subCategory.title}</h2>
                                                                            <p class="description">${subCategory.description}</p>
                                                                        </div>
                                                                    </a>
                                                                </div>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                                <div class="col-lg-6">
                                                    <c:forEach var="subCategory" items="${AllBlogsByPI[lb.index]}" varStatus="loop">
                                                        <c:if test="${subCategory.parentID == category.parentID && subCategory.blogCategoryID.blogCategoryID != category.blogCategoryID.blogCategoryID}">
                                                            <c:if test="${loop.index == 1 || loop.index == 2}">
                                                                <!--Hiển thị 2 blog đầu tiên trong hàng đầu-->
                                                                <div class="blog-item" style="width: 50%; padding: 0 2.5%;">
                                                                    <a href="blogdetail?blogID=${subCategory.blogID}">
                                                                        <div class="box-image">
                                                                            <img class="img-service" style="height: 170px" src="img/1px.png" lazy-src="img/Blog/${subCategory.blogIMG}" alt="${subCategory.title}">
                                                                        </div>
                                                                        <div class="down-content">
                                                                            <p>${subCategory.title}</p>
                                                                        </div>
                                                                    </a>
                                                                </div>
                                                            </c:if>

                                                            <c:if test="${loop.index == 3 || loop.index == 4}">
                                                                <!--Hiển thị 2 blog tiếp theo trong hàng thứ hai-->
                                                                <div class="blog-item" style="width: 50%; padding: 0 2.5%;">
                                                                    <a href="blogdetail?blogID=${subCategory.blogID}">
                                                                        <div class="box-image">
                                                                            <img class="img-service" style="height: 130px" src="img/1px.png" lazy-src="img/Blog/${subCategory.blogIMG}" alt="${subCategory.title}">
                                                                        </div>
                                                                        <div class="down-content">
                                                                            <p>${subCategory.title}</p>
                                                                        </div>
                                                                    </a>
                                                                </div>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        <div class="xemthem" style="text-align: center;">
                            <a href="bloghomelist">
                                Xem thêm
                            </a>
                        </div>

                    </div>

                    <script>
                        let listNewsType = document.querySelectorAll('.list_blog_type ul li');
                        let blogSubCategoryElement = document.querySelectorAll('.bbox.container > div.service-parentID');

                        blogSubCategoryElement.forEach(function (blogSubCategory, index) {
                            if (index === 0) {
                                blogSubCategory.classList.remove('hide');
                            } else {
                                blogSubCategory.classList.add('hide');
                            }
                        });

                        listNewsType.forEach(function (li) {
                            li.addEventListener('click', function () {
                                listNewsType.forEach(function (item) {
                                    item.classList.remove('active');
                                });

                                this.classList.add('active');

                                let selectedType = this.textContent.trim();

                                blogSubCategoryElement.forEach(function (blogElement) {
                                    let bscId = blogElement.getAttribute('data-value');

                                    if (selectedType === bscId) {
                                        blogElement.classList.remove('hide');
                                    } else {
                                        blogElement.classList.add('hide');
                                    }
                                });
                            });
                        });
                    </script>

                </div>
            </div>
        </div>
        <!-- Blogs Ends Hear-->

        <!-- Footer-->
        <div class="map">
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3723.9327372450375!2d105.75677227591818!3d21.03537718755002!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc4b6a0fe47%3A0x537c07fb83a9ed0f!2sAuto%2099!5e0!3m2!1svi!2s!4v1695022239157!5m2!1svi!2s"
                    width="100%" height="300px" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
        </div>
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