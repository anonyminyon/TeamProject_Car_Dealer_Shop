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
                <!--        <link rel="stylesheet" href="assets/css/HomeList.css">-->
                <link rel="stylesheet" href="assets/css/Header.css">
                <link rel="stylesheet" href="assets/css/Footer.css">
                <link rel="stylesheet" href="assets/css/ProductList.css">

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

                    <div id="content" class="bgf5" style="padding-top: 0rem">

                        <div data-v-75885984="" class="search-filter-content nc-search-filter" data-v-0405ab77="">
                            <div class="wa-title">
                                <h2 class="wa-title-text">Bộ lọc tìm kiếm</h2>
                            </div>
                            <ul class="sfc-list" style="height: 280px;">
                                <li>
                                    <div class="sc-item-content">
                                        <span class="sc-item-title">Hãng :</span>
                                        <ul class="sc-item-option-list">
                                            <li id="brand" value="0" class="sc-item-option sc-item-option-selected"><a onclick="filter()"> All </a></li>
                                            <c:forEach var="b" items="${BrandList}">
                                                <li id="brand" value="${b.getBrandID()}" class="sc-item-option"><a onclick="filter()"> ${b.getBrandName()} </a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <div class="sc-item-content">
                                        <span class="sc-item-title">Kiểu dáng :</span>
                                        <ul class="sc-item-option-list">
                                            <li id="design" value="0" class="sc-item-option sc-item-option-selected"><a onclick="filter()"> All </a></li>
                                            <c:forEach var="d" items="${DesignList}">
                                                <li id="design" value="${d.getDesignID()}" class="sc-item-option"><a onclick="filter()"> ${d.getDesign()} </a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <div class="sc-item-content">
                                        <span class="sc-item-title">Loại năng lượng :</span>
                                        <ul class="sc-item-option-list">
                                            <li id="fuel" value="" class="sc-item-option sc-item-option-selected"><a onclick="filter()"> All </a></li>
                                            <c:forEach var="f" items="${FuelList}">
                                                <li id="fuel" value="${f}" class="sc-item-option"><a onclick="filter()"> ${f} </a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <div class="sc-item-content">
                                        <span class="sc-item-title">Hộp số :</span>
                                        <ul class="sc-item-option-list">
                                            <li id="gear" value="" class="sc-item-option sc-item-option-selected"><a onclick="filter()"> All </a></li>
                                            <c:forEach var="g" items="${GearList}">
                                                <li id="gear" value="${g}" class="sc-item-option"><a onclick="filter()"> ${g} </a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <div class="sc-item-content">
                                        <span class="sc-item-title">Ghế ngồi :</span>
                                        <ul class="sc-item-option-list">
                                            <li id="seat" value="0" class="sc-item-option sc-item-option-selected"><a onclick="filter()"> All </a></li>
                                            <c:forEach var="s" items="${SeatList}">
                                                <li id="seat" value="${s}" class="sc-item-option"><a onclick="filter()"> ${s} </a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <div class="sc-item-content">
                                        <span class="sc-item-title">Giá tiền :</span>
                                        <ul class="sc-item-option-list">
                                            <li id="price" class="sc-item-option sc-item-option-selected" data-min="0" data-max=""><a onclick="filter()"> All </a></li>
                                            <li id="price" class="sc-item-option" data-min="0" data-max="300000000"><a onclick="filter()"> Dưới 300 tr </a></li>
                                            <li id="price" class="sc-item-option" data-min="300000000" data-max="500000000"><a onclick="filter()"> 300 - 500 tr </a></li>
                                            <li id="price" class="sc-item-option" data-min="500000000" data-max="700000000"><a onclick="filter()"> 500 - 700 tr </a></li>
                                            <li id="price" class="sc-item-option" data-min="700000000" data-max="1000000000"><a onclick="filter()"> 700 tr - 1 tỷ </a></li>
                                            <li id="price" class="sc-item-option" data-min="1000000000" data-max="2000000000"><a onclick="filter()"> 1 tỷ - 2 tỷ </a></li>
                                            <li id="price" class="sc-item-option" data-min="2000000000" data-max="4000000000"><a onclick="filter()"> 2 tỷ - 4 tỷ </a></li>
                                            <li id="price" class="sc-item-option" data-min="4000000000" data-max="6000000000"><a onclick="filter()"> 4 tỷ - 6 tỷ </a></li>
                                            <li id="price" class="sc-item-option" data-min="6000000000" data-max="10000000000"><a onclick="filter()"> 6 tỷ - 10 tỷ </a></li>
                                            <li id="price" class="sc-item-option" data-min="10000000000" data-max=""><a onclick="filter()"> Trên 10 tỷ </a></li>
                                        </ul>
                                    </div>
                                </li>
                                <div>
                                    <span class="sc-item-title">Tìm kiếm :</span>
                                    <input class="col-md-8" oninput="filter()" id="search-box" type="text" placeholder="Tìm kiếm">
                                </div>
                            </ul>
                        </div>

                        <div class="conbox">
                            <div class="pro">
                                <ul>
                                    <div class="row" id="productList">
                                        <c:forEach var="c" items="${CarList}">
                                            <li>
                                                <div class="box-image">
                                                    <a href="product?carID=${c.getCarID()}">
                                                        <img class="image-car image-index" alt="${c.getCarName()}" src="./img/Xe/${c.getCarID()}/${c.getCarIMG().getCarIMG()}">
                                                        <img class="image-car image-hover" alt="${c.getCarName()}" src="./img/Xe/${c.getCarID()}/${c.getCarSubIMG().getCarSubIMG()}">
                                                    </a>
                                                </div>
                                                <div class="info">
                                                    <a href="product?carID=${c.getCarID()}">
                                                        <h2>
                                                            ${c.getCarName()}
                                                        </h2>
                                                        <div class="pri">Giá từ:
                                                            <div>
                                                                <fmt:formatNumber value="${c.getPrice()}" pattern="#,###" type="number" /><span>VNĐ</span>
                                                            </div>
                                                        </div>
                                                        <div class="tech">
                                                            <span>${c.getNumberOfSeat()}</span>
                                                            <span>${c.getDesign().getDesign()}</span>
                                                            <span>${c.getFuel()}</span>
                                                            <span>${c.getMadeIn()}</span>
                                                            <span>${c.getGear()}</span>
                                                            <span>${c.getEngineType()}</span>
                                                            <span>Dung tích: ${c.getCylinderCapacity()} cc</span>
                                                        </div>
                                                    </a>
                                                    <div class="tool">
                                                        <a class="a1" href="costestimate">Dự toán</a>
                                                        <a class="a2" href="cardeposit?carID=${c.getCarID()}">Đặt cọc xe</a>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </div>
                                </ul>
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

                        Lazy Loading
                        <script src="assets/js/lazy.js"></script>

                        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                        <script>
                            $(document).ready(function() {
                                let loading = false;
                                let canLoadMore = true;

                                $(window).scroll(function() {
                                    if (canLoadMore && !loading && $(document).height() - $(window).height() <= $(window).scrollTop()) {
                                        loading = true;
                                        loadMoreProducts();
                                    }
                                });

                                function loadMoreProducts() {
                                    var brand = document.querySelector('#brand.sc-item-option-selected').getAttribute('value');
                                    var design = document.querySelector('#design.sc-item-option-selected').getAttribute('value');
                                    var fuel = document.querySelector('#fuel.sc-item-option-selected').getAttribute('value');
                                    var gear = document.querySelector('#gear.sc-item-option-selected').getAttribute('value');
                                    var seat = document.querySelector('#seat.sc-item-option-selected').getAttribute('value');
                                    var name = document.getElementById('search-box').value;
                                    var amount = 0;

                                    var minPrice = document.querySelector('#price.sc-item-option-selected').getAttribute('data-min');
                                    var maxPrice = document.querySelector('#price.sc-item-option-selected').getAttribute('data-max');
                                    canLoadMore = false;

                                    // Perform an AJAX request to load more products
                                    var amount = document.getElementsByClassName("box-image").length;
                                    $.ajax({
                                        url: "/Auto99/productlist", // Replace with your servlet URL
                                        method: "POST",
                                        data: {
                                            brand: brand,
                                            design: design,
                                            fuel: fuel,
                                            amount: amount,
                                            minPrice: minPrice,
                                            maxPrice: maxPrice,
                                            gear: gear,
                                            seat: seat,
                                            name: name
                                        },
                                        success: function(data) {
                                            // Append the new products to the existing list
                                            var row = document.getElementById("productList");
                                            row.innerHTML += data;
                                            loading = false; // Reset the loading flag

                                            // Allow loading again after a delay (e.g., 1000 milliseconds)
                                            setTimeout(function() {
                                                canLoadMore = true;
                                            }, 1000);
                                        }
                                    });
                                }
                            });
                        </script>

                        <script>
                            document.addEventListener('DOMContentLoaded', function() {
                                const optionLists = document.querySelectorAll('.sc-item-option-list');

                                optionLists.forEach(function(list) {
                                    const options = list.querySelectorAll('.sc-item-option');

                                    options.forEach(function(option) {
                                        option.addEventListener('click', function() {
                                            // Remove 'sc-item-option-selected' class from all options
                                            options.forEach(function(o) {
                                                o.classList.remove('sc-item-option-selected');
                                            });

                                            // Add 'sc-item-item-option-selected' class to the clicked option
                                            option.classList.add('sc-item-option-selected');

                                            // Trigger the filter immediately when an option is selected
                                            filter();
                                        });
                                    });
                                });
                            });

                            let filterTimeout;

                            function filter() {
                                clearTimeout(filterTimeout); // Clear any existing timeout
                                filterTimeout = setTimeout(function() {
                                    var brand = document.querySelector('#brand.sc-item-option-selected').getAttribute('value');
                                    var design = document.querySelector('#design.sc-item-option-selected').getAttribute('value');
                                    var fuel = document.querySelector('#fuel.sc-item-option-selected').getAttribute('value');
                                    var gear = document.querySelector('#gear.sc-item-option-selected').getAttribute('value');
                                    var seat = document.querySelector('#seat.sc-item-option-selected').getAttribute('value');
                                    var name = document.getElementById('search-box').value;
                                    var amount = 0;

                                    var minPrice = document.querySelector('#price.sc-item-option-selected').getAttribute('data-min');
                                    var maxPrice = document.querySelector('#price.sc-item-option-selected').getAttribute('data-max');

                                    $.ajax({
                                        url: "/Auto99/productlist", // Replace with your servlet URL
                                        method: "POST",
                                        data: {
                                            brand: brand,
                                            design: design,
                                            fuel: fuel,
                                            amount: amount,
                                            minPrice: minPrice,
                                            maxPrice: maxPrice,
                                            gear: gear,
                                            seat: seat,
                                            name: name
                                        },
                                        success: function(data) {
                                            // Append the new products to the existing list
                                            var row = document.getElementById("productList");
                                            row.innerHTML = data;
                                        }
                                    });
                                }, 100);
                            }
                        </script>

            </body>

            </html>