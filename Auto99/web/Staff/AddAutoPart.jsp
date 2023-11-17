<%-- 
    Document   : AddService
    Created on : Sep 24, 2023, 12:55:58 AM
    Author     : Hieu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta charset="UTF-8">    
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="./assets/Staff/css/AdminPage.css">

        <!----===== font awesome CSS ===== -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>

        <link rel="stylesheet" href="./assets/Staff/css/main.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
        <script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>
        <script>

            function readURL(input, thumbimage) {
                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimage").attr('src', e.target.result);
                    }
                    reader.readAsDataURL(input.files[0]);
                } else { // Sử dụng cho IE
                    $("#thumbimage").attr('src', input.value);

                }
                $("#thumbimage").show();
                $('.filename').text($("#uploadfile").val());
                $('.Choicefile').css('background', '#14142B');
                $('.Choicefile').css('cursor', 'default');
                $(".removeimg").show();
                $(".Choicefile").unbind('click');

            }
            $(document).ready(function () {
                $(".Choicefile").bind('click', function () {
                    $("#uploadfile").click();

                });
                $(".removeimg").click(function () {
                    $("#thumbimage").attr('src', '').hide();
                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                    $(".removeimg").hide();
                    $(".Choicefile").bind('click', function () {
                        $("#uploadfile").click();
                    });
                    $('.Choicefile').css('background', '#14142B');
                    $('.Choicefile').css('cursor', 'pointer');
                    $(".filename").text("");
                });
            })
        </script>

        <% 
                    String error = request.getParameter("error");
        %>

        <title>Car List</title>
    </head>
    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item"><a href="autopart">Danh sách phụ tùng</a></li>
                        <li class="breadcrumb-item"><a href="#">Thêm phụ tùng</a></li>
                    </ul>
                </div>

                <form action="partcrud" method="POST"  enctype="multipart/form-data">
                    <div class="col-md-12">
                        <div class="tile">
                            <h3 class="tile-title">Tạo mới phụ tùng</h3>
                            <div class="tile-body">
                                <div class="row">
                                    <fieldset class="form-group col-md-12">
                                        <legend>Thông tin cơ bản</legend>
                                        <div class="row">

                                            <div class="form-group col-md-12">
                                                <% 
                                                    if (error != null && error.equals("1")) {
                                                %>
                                                <p class="text-warning">Tên phụ tùng đã tồn tại </p>
                                                <% } %>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Tên phụ tùng</label>
                                                <input class="form-control" type="text" name="partName" required>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Giá tiền (VNĐ) </label>
                                                <input class="form-control" type="number" name="price" required>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Tình trạng </label>
                                                <select class="form-control" name="Status">
                                                    <option value="true">Còn hàng</option>
                                                    <option value="false">Hết hàng</option>
                                                </select>
                                            </div>

                                        </div>
                                    </fieldset>

                                    <fieldset class="form-group col-md-12">
                                        <legend>Áp dụng cho</legend>
                                        <div class="row">
                                            <div class="form-group col-md-7 row length-box">
                                                <div class="form-group col-md-6">
                                                    <label for="brandID">Hãng xe (*)</label>
                                                    <select name="brandID" id="brandID" tabindex="3" onchange="getCar()">
                                                        <option value="0">Chọn hãng xe của bạn</option> <!-- Placeholder option -->
                                                        <c:forEach items="${BrandList}" var="brand">
                                                            <option value="${brand.getBrandID()}">${brand.getBrandName()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="form-group col-md-6">
                                                    <label for="designID">Kiểu dáng (*)</label>
                                                    <select name="designID" id="designID" tabindex="3" onchange="getCar()">
                                                        <option value="0">Chọn kiểu dáng</option> <!-- Placeholder option -->
                                                        <c:forEach items="${DesignList}" var="design">
                                                            <option value="${design.getDesignID()}">${design.getDesign()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <div id="carList">
                                                        <label for="carID">Mẫu xe (*)</label>
                                                        <select name="carID" id="carID" tabindex="3" onchange="getPrice()">
                                                            <option value="0">Chọn mẫu xe</option> <!-- Placeholder option -->
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <button name="addButton" tabindex="3" onclick="addCar(event)">Thêm</button>
                                                </div>
                                            </div>
                                            <div class="form-group col-md-5 row length-box">
                                                <table class="table table-hover table-bordered" id="sampleTable">
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Tên xe</th>
                                                            <th>Chức năng</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody class="carTableList" id="carTableList">
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <fieldset class="form-group col-md-12">
                                        <div class="row">
                                            <legend>Hình ảnh</legend>
                                            <div class="form-group col-md-9">
                                                <label class="control-label">Ảnh sản phẩm</label>

                                                <% 
                                                        if (error != null && error.equals("2")) {
                                                %>
                                                <p class="text-warning">Không tìm thấy file</p>
                                                <% } else if (error != null && error.equals("4")) { %>
                                                <p class="text-warning">File không hợp lệ</p>
                                                <% } %>

                                                <div id="myfileupload">
                                                    <input type="file" id="uploadfile" name="ImageUpload" accept=".jpg,.jpeg,.png,.gif" onchange="readURL(this);">
                                                </div>
                                                <div id="thumbbox">
                                                    <img height="450" width="400" alt="Thumb image" id="thumbimage" style="display: none">
                                                    <a class="removeimg" href="javascript:"></a>
                                                </div>
                                                <div id="boxchoice">
                                                    <a href="javascript:" class="Choicefile"><i class="fas fa-cloud-upload-alt"></i> Chọn ảnh</a>
                                                    <p style="clear:both"></p>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset class="form-group col-md-12">
                                        <legend>Giới thiệu</legend>
                                        <div>
                                            <textarea class="form-control" name="description" id="default">${textareaData}</textarea>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                            <button class="btn btn-save" type="submit">Lưu lại</button>
                            <!<!-- send input action to servlet to know what action need -->
                            <input type="hidden" name="action" value="addPart"/>
                            <a class="btn btn-cancel" href="autopart">Hủy bỏ</a>
                            <c:if test="${msg != null}">
                                <span class="text-success">${msg}</span>
                            </c:if>
                        </div>
                    </div>
                </form>
            </div>
        </div>

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
                        url: "/Auto99/partcrud", // Replace with your servlet URL
                        method: "POST",
                        data: {
                            action: "GetCar",
                            brand: brand,
                            design: design
                        },
                        success: function (data) {
                            // Replace the car list content with the new data
                            var carList = document.querySelector('#carList');
                            carList.innerHTML = data;
                        }
                    });
                }, 10);
            }
        </script>

        <script>
            function addCar(event) {
                event.preventDefault(); // Prevent the default form submission

                var filterTimeout;
                clearTimeout(filterTimeout); // Clear any existing timeout
                filterTimeout = setTimeout(function () {
                    var carID = document.querySelector('#carID').value;

                    $.ajax({
                        url: "/Auto99/partcrud", // Replace with your servlet URL
                        method: "POST",
                        data: {
                            action: "AddCar",
                            carID: carID
                        },
                        success: function (data) {
                            // Replace the car list content with the new data
                            var carList = document.getElementById('carTableList');
                            carList.innerHTML = data;
                            // Move getCar() outside the $.ajax success callback
                            getCar();
                        }
                    });
                }, 10);
            }
        </script>

        <script>
            function Delete(button, event) {
                event.preventDefault(); // Prevent the default form submission

                var filterTimeout;
                clearTimeout(filterTimeout); // Clear any existing timeout
                filterTimeout = setTimeout(function () {
                    var carID = button.value;

                    $.ajax({
                        url: "/Auto99/partcrud", // Replace with your servlet URL
                        method: "POST",
                        data: {
                            action: "DeleteCar",
                            carID: carID
                        },
                        success: function (data) {
                            // Replace the car list content with the new data
                            var carList = document.querySelector('#carTableList');
                            carList.innerHTML = data;
                            // You may also want to refresh other parts of your UI here
                            getCar();
                        }
                    });
                }, 10);
            }
        </script>


        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="tinymce/tinymce.min.js"></script>
        <script src="assets/Staff/js/TinyMCE.js"></script>

    </body>
</html>
