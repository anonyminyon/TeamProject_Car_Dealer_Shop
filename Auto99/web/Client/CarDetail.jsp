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

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Additional CSS Files -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <!--        <link rel="stylesheet" href="assets/css/HomeList.css">-->
        <link rel="stylesheet" href="assets/css/Header.css">
        <link rel="stylesheet" href="assets/css/Footer.css">
        <link rel="stylesheet" href="assets/css/ProductDetail.css">

    </head>

    <body>

        <!-- *** Preloader Start *** -->
        <div id="preloader">
            <div class="jumper">
                <div></div>
                <div></div>
                <div></div>
            </div>
        </div>
        <!-- *** Preloader End *** -->

        <!-- Header -->
        <%@include file="../Client/Header.jsp" %>

        <!-- Header Ends Hear -->
        <div class="page-heading about-heading header-text">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="text-content">
                            <h2>Sản phẩm</h2>
                            <div style="display:flex; justify-content: center; align-items: center; text-align: center;">
                                <h4><a href="home">Trang chủ </a></h4>
                                <h4> > </h4>
                                <c:if test="${blogID == null}">
                                    <h4><a href="productlist"> Sản phẩm</a></h4>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="content">
            <div class="conbox">
                <div class="ptop row justify-content-center">
                    <div class="pleft col-md-5">
                        <div class="thumb"><img alt="${c.getCarName()}" src="./img/Xe/${c.getCarID()}/${c.getCarSubIMG().getCarSubIMG()}"></div>
                    </div>
                    <div class="pright col-md-5">
                        <h1>${c.getCarName()}</h1>
                        <div class="tool"><a class="a1" href="costestimate" target="_blank">Dự toán</a>
                            <span></span><a class="a2" href="cardeposit?carID=${c.getCarID()}">Đặt cọc</a></div>
                        <div class="tech">
                            <ul>
                                <li>
                                    <p>Số chỗ</p>
                                    <div>${c.getNumberOfSeat()} chỗ</div>
                                </li>
                                <li>
                                    <p>Kiểu dáng</p>
                                    <div>${c.getDesign().getDesign()}</div>
                                </li>
                                <li>
                                    <p>Nhiên liệu</p>
                                    <div>${c.getFuel()}</div>
                                </li>
                            </ul>
                        </div>
                        <div class="pri">
                            <ul>
                                <li>
                                    <p>Xuất xứ</p>
                                    <div>${c.getMadeIn()}</div>
                                </li>
                                <li>
                                    <p>Giá từ</p>
                                    <div><fmt:formatNumber value="${c.getPrice()}" pattern="#,###" type="number"/>&nbsp;<span>VNĐ</span></div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="content" class="col-md-12">
            <div class="conbox justify-content-center">
                <div class="pkt">
                    <h1>Thông số kỹ thuật ${c.getCarName()}</h1>
                    <div class="note">Lưu ý: Công ty Ô tô Toyota Việt Nam được quyền thay đổi bất kỳ đặc tính nào mà không báo trước. Một số đặc tính kỹ thuật có thể khác so với thực tế.</div>
                    <ul>
                        <li id="m">
                            <div class="head">
                                <h2>Thông tin chung</h2>
                                <i class="fas fa-chevron-down"></i>
                            </div>
                            <div class="ibox" style="padding:25px 0;">
                                <div class="sgroup">
                                    <div class="sibox">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td>Số chỗ</td>
                                                    <td>${c.getNumberOfSeat()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Kiểu dáng</td>
                                                    <td>${c.getDesign().getDesign()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Xuất xứ</td>
                                                    <td>${c.getMadeIn()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Nhiên liệu</td>
                                                    <td>${c.getFuel()}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li id="m66">
                            <div class="head">
                                <h2>Động cơ</h2>
                                <i class="fas fa-chevron-down"></i>
                            </div>
                            <div class="ibox">
                                <div class="sgroup" id="s75">
                                    <div class="shead">
                                        <h3>Động cơ thường</h3>
                                    </div>
                                    <div class="sibox">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                <tr>
                                                    <td>Dung tích bình nhiên liệu (L)</td>
                                                    <td>${eac.getFuelTankCapacity()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Loại động cơ</td>
                                                    <td>${eac.getEngineType()}</td>
                                                </tr>
                                                <tr>
                                                <tr>
                                                    <td>Số xy lanh</td>
                                                    <td>${eac.getNumberOfCylinder()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Dung tích xy lanh (cc)</td>
                                                    <td>${eac.getCylinderCapacity()}</td>
                                                </tr>
                                                <tr>
                                                <tr>
                                                    <td>Hệ thống van biến thiên</td>
                                                    <td>${eac.getVariableValveSystem()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Hệ thống nhiên liệu</td>
                                                    <td>${eac.getFuelSystem()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Loại nhiên liệu</td>
                                                    <td>${c.getFuel()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Công suất tối đa ((KW) HP/vòng/phút)</td>
                                                    <td>${eac.getMaximumCapacity()}</td>
                                                </tr>
                                                <tr>
                                                    <td>Mô men xoắn tối đa (Nm/vòng/phút)</td>
                                                    <td>${eac.getMaximumTorque()}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="sgroup" id="s79">
                                    <div class="shead">
                                        <h3>Hộp số</h3>
                                    </div>
                                    <div class="sibox">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td>Hộp số</td>
                                                    <td>${eac.getGear()}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div id="content">
            <div class="conbox">
                <div class="description">
                    ${c.getDescription()}
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

        Lazy Loading
        <script src="assets/js/lazy.js"></script>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            // Get all elements with class "head"
            var heads = document.querySelectorAll(".head");

            // Loop through each "head" element and add a click event listener
            heads.forEach(function (head) {
                head.addEventListener("click", function () {
                    // Toggle the class "active" to expand/collapse the content
                    head.classList.toggle("active");
                    var ibox = head.parentElement.querySelector(".ibox");
                    ibox.style.display = (ibox.style.display === "block") ? "none" : "block";
                });
            });
        </script>
    </body>
</html>