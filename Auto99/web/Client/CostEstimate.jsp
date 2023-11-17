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

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>

        <style>
            /* Remove border around input and select elements */
            input, select {
                border: none !important;
                border-color: white#ffffff !important; /* Set border color to transparent */
                width: 100%; /* Make the input/select 100% width */
                margin: 0; /* Remove margin */
                padding: 0; /* Remove padding */
            }

            /* Style for the select dropdown arrow (optional) */
            select {
                /* Hide the default dropdown arrow */
                -webkit-appearance: none;
                -moz-appearance: none;
                appearance: none;
                /* Add a custom arrow or icon if needed */
                background: url('your-arrow-icon.png') no-repeat right center;
                padding-right: 20px; /* Adjust the padding for the icon */
            }

            label {
                font:  700 25px/20px sans-serif; /* Make the text bold */
                padding: 0;
            }
            #Tieu-de {
                font:  554 36px/50px sans-serif; /* Make the text bold */
            }

            .checkbox-list {
                list-style: none;
            }

            .checkbox {
                display: flex;
                align-items: center;
                cursor: pointer;

            }

            .checkbox div{
                font-family: sans-serif;
            }

            .checkbox i {
                margin-right: 10px;
                font-size: 1.2em;
                color: #666;
            }

            .checkbox.checked i {
                color: #007bff;
            }

            p{
                font-family: inherit;
                font-size: inherit;
                line-height: inherit;
                display: flex !important;
                flex-direction: row !important;
                align-items: flex-start !important;
                justify-content: space-between !important;
            }
            
            .total{
                font-size: 2rem !important;
            }
        </style>
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

        <div class="container-fluid">
            <div class="row d-flex justify-content-around" style="padding-top: 10rem; padding-bottom: 10rem; background: url('./img/backgroundService.jpg') no-repeat top center fixed; background-size: cover;">
                <div class="box" style="background-color: white; padding: 2rem; width: 50%; margin: 0.5rem auto; font-size: 1.5rem; border-radius: 1rem;">
                    <!-- Content of the box -->
                    <h1 id="Tieu-de">DỰ TOÁN CHI PHÍ</h1>
                    <ul>
                        <form action="costestimate" method="POST" name="Lhebook" id="Lhebook" autocomplete="off">
                            <li>
                                <label for="brandID">Hãng xe (*)</label>
                                <select name="brandID" id="brandID" tabindex="3" onchange="getCar()">
                                    <option value="0">Chọn hãng xe của bạn</option> <!-- Placeholder option -->
                                    <c:forEach items="${BrandList}" var="brand">
                                        <option value="${brand.getBrandID()}">${brand.getBrandName()}</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <hr/>
                            <li>
                                <label for="designID">Kiểu dáng (*)</label>
                                <select name="designID" id="designID" tabindex="3" onchange="getCar()">
                                    <option value="0">Chọn kiểu dáng</option> <!-- Placeholder option -->
                                    <c:forEach items="${DesignList}" var="design">
                                        <option value="${design.getDesignID()}">${design.getDesign()}</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <hr/>
                            <li>
                                <div id="carList">
                                    <label for="carID">Mẫu xe (*)</label>
                                    <select name="carID" id="carID" tabindex="3" onchange="getPrice()">
                                        <option value="0">Chọn mẫu xe</option> <!-- Placeholder option -->
                                    </select>
                                </div>
                            </li>
                            <hr/>
                            <li>
                                <div id="location">
                                    <label for="location">Nơi đăng ký (*)</label>
                                    <select name="locationID" id="locationID" tabindex="3" onchange="getLocation()">
                                        <option value="0">Chọn địa điểm</option> <!-- Placeholder option -->
                                        <c:forEach items="${LocationList}" var="location">
                                            <option value="${location.getLocationID()}">${location.getLocationName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </li>
                            <hr/>
                            <li>
                                <label>Giá niêm yết (*)</label><br>
                                <input name="carPrice" readonly id="carPrice" value="" tabindex="3">
                            </li>
                            <hr/>
                            <h2>Chi phí đăng ký, đăng kiểm và bảo hiểm</h2>
                            <!--                            <p>
                                                            <label class="col-md-6">Giá tính phí trước bạ</label>
                                                            <input name="preTaxPrice" class="text-right" readonly id="preTaxPrice" value="" tabindex="3">
                                                        </p>-->
                            <div  id = "LocationFee">
                                <p>
                                    <label class="col-md-6">Lệ phí trước bạ</label><br>
                                    <input name="preRegFee" class="text-right" readonly id="preRegFee" value="" tabindex="3">
                                </p>

                                <p>
                                    <label class="col-md-6">Lệ phí đăng ký</label><br>
                                    <input name="regFee" class="text-right" readonly id="regFee" value="" tabindex="3">
                                </p>
                            </div>

                            <c:forEach var="f" items="${FeeList}">
                                <div>
                                    <p>
                                        <label class="col-md-8">${f.getFeeName()}</label><br>
                                        <fmt:formatNumber value="${f.getFee()}" pattern="#,###" var="formattedValue" />
                                        <input class="text-right" readonly value="${formattedValue} VNĐ" tabindex="3">
                                    </p>
                                </div>
                            </c:forEach>

                            <hr/>
                            <div class ="row">
                            <h2 class="text-danger col-md-6" >Tổng chi phí lăn bánh và bảo hiểm</h2>
                            <input name="Total" class="text-danger text-right col-md-6 font-weight-bold total" readonly id="Total" value="" tabindex="3">
                            </div>
                            <hr/>
                        </form>
                    </ul>
                </div>
            </div>
        </div>    



        <!-- Your content here -->
        <!-- Footer-->
        <%@include file="../Client/Footer.jsp" %>
        <!-- Footer End Hear-->


        <!-- Bootstrap core JavaScript -->
        <script src="./vendor/jquery/jquery.min.js"></script>
        <script src="./vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Additional Scripts -->
        <script src="assets/js/custom.js"></script>
        <script src="assets/js/owl.js"></script>

        <!--Lazy Loading-->
        <script src="assets/js/lazy.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


        <script>
                                        function getCar() {
                                            var filterTimeout;
                                            clearTimeout(filterTimeout); // Clear any existing timeout
                                            filterTimeout = setTimeout(function () {
                                                var brandSelect = document.querySelector('#brandID');
                                                var designSelect = document.querySelector('#designID');

                                                var brand = brandSelect.value; // Use .value to get the selected value
                                                var design = designSelect.value;

                                                $.ajax({
                                                    url: "/Auto99/costestimate", // Replace with your servlet URL
                                                    method: "POST",
                                                    data: {
                                                        action: "getCar",
                                                        brand: brand,
                                                        design: design
                                                    },
                                                    success: function (data) {
                                                        // Replace the car list content with the new data
                                                        var carList = document.querySelector('#carID');
                                                        carList.innerHTML = data;
                                                        Total();
                                                    }
                                                });
                                            }, 50);
                                        }
        </script>

        <script>
            function getPrice() {
                var filterTimeout;
                clearTimeout(filterTimeout); // Clear any existing timeout
                filterTimeout = setTimeout(function () {
                    var carSelect = document.querySelector('#carID');

                    var carID = carSelect.value;

                    $.ajax({
                        url: "/Auto99/costestimate", // Replace with your servlet URL
                        method: "POST",
                        data: {
                            action: "getPrice",
                            carID: carID
                        },
                        success: function (data) {
                            // Replace the car list content with the new data
                            var carPrice = document.getElementById("carPrice");
                            carPrice.value = data;
                            getLocation();
                            Total();
                        }
                    });
                }, 50);
            }
        </script>

        <script>
            function getLocation() {
                var filterTimeout;
                clearTimeout(filterTimeout); // Clear any existing timeout
                filterTimeout = setTimeout(function () {
                    var locationID = document.querySelector('#locationID').value;
                    var carID = document.querySelector('#carID').value;
                    $.ajax({
                        url: "/Auto99/costestimate", // Replace with your servlet URL
                        method: "POST",
                        data: {
                            action: "getLocation",
                            locationID: locationID,
                            carID: carID
                        },
                        success: function (data) {
                            // Replace the car list content with the new data
                            var location = document.getElementById("LocationFee");
                            location.innerHTML = data;
                            Total();
                        }
                    });
                }, 50);
            }
        </script>

        <script>
            function Total() {
                var filterTimeout;
                clearTimeout(filterTimeout); // Clear any existing timeout
                filterTimeout = setTimeout(function () {
                    var carSelect = document.querySelector('#carID');
                    var locationID = document.querySelector('#locationID').value;

                    var carID = carSelect.value;

                    $.ajax({
                        url: "/Auto99/costestimate", // Replace with your servlet URL
                        method: "POST",
                        data: {
                            action: "Total",
                            locationID: locationID,
                            carID: carID
                        },
                        success: function (data) {
                            // Replace the car list content with the new data
                            var Total = document.getElementById("Total");
                            Total.value = data;
                        }
                    });
                }, 50);
            }
        </script>

    </body>
</html>