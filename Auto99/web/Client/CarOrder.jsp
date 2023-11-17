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
        <link rel="stylesheet" href="assets/css/CarDeposit.css">

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

        <div class="home-content">
            <div class="tab-left">
                <div class="box-image">
                    <img src="./img/Xe/${Car.getCarID()}/${Car.getCarSubIMG().getCarSubIMG()}">
                </div>
            </div>
            <div class="tab-right-container">
                <div class="tab-right">
                    <h1>Đặt cọc ${Car.getCarName()}</h1>
                    <ul>
                        <li>
                            <label for="enterInfoButton">Nhập thông tin</label>
                            <button class="hide" id="enterInfoButton">Nhập thông tin</button>
                        </li>
                        <li>
                            <label>Đặt cọc</label>
                        </li>
                    </ul>

                    <div class="content" id="content1">
                        <form id="depositForm">
                            <input type="hidden" name="carID" value="${carID}" id="carID" required>
                            <p>Hãy nhập thông tin của quý khách.</p>
                            <h4>Thông tin chủ xe</h4>
                            <div class="group-select-customer" style="display: flex;">
                                <p>Chủ sở hữu xe là</p>
                                <div class="group-input">
                                    <input name="customerType" value="personal" type="radio" id="personalRadio" checked="">
                                    <label for="personalRadio">Cá Nhân</label>
                                    <input name="customerType" value="corporate" type="radio" id="corporateRadio">
                                    <label for="corporateRadio">Doanh Nghiệp</label>
                                </div>
                            </div>
                            <div class="group-corporate">
                                <input class="name" type="text" name="companyName" id="companyName"  placeholder="Tên doanh nghiệp" pattern="^[^!@#$%^&*()_+\-.,=~]*$" required data-missing-error="Vui lòng nhập tên Doanh Nghiệp." data-parse-error="Trường không được chứa ký tự đặc biệt" data-input-invalid="Trường không được chứa ký tự đặc biệt" value="">
                                <i class="icon fa-solid fa-users"></i>
                                <div class="invalid-feedback"></div>
                            </div>

                            <div class="group-personal">
                                <input class="name" type="text" name="customerName" id="customerName"  placeholder="Họ và tên" required pattern="^[^!@#$%^&*()_+\-.,=~0-9]*$" data-missing-error="Vui lòng nhập họ và tên" data-parse-error="Trường không được chứa ký tự đặc biệt" data-input-invalid="Trường không được chứa ký tự đặc biệt" value="">
                                <i class="icon fa-solid fa-user"></i>
                                <div class="invalid-feedback"></div>

                            </div>

                            <div class="group">
                                <input class="name" type="text" name="phoneNumber" id="phoneNumber"  placeholder="Số điện thoại" required maxlength="12" minlength="9" pattern="^((\+?84)|0)((3([2-9]))|(5([25689]))|(7([0|6-9]))|(8([1-9]))|(9([0-9])))([0-9]{7})$" placeholder="Số điện thoại" data-missing-error="Vui lòng nhập số điện thoại." data-parse-error="Số điện thoại của Quý khách chưa đúng. Vui lòng kiểm tra lại." data-exist="Số điện thoại của quý khách đã được đăng kí"  value="">
                                <i class="icon fa-solid fa-phone"></i>
                                <div class="invalid-feedback"></div>
                            </div>

                            <div class="group-personal">
                                <input class="name" type="text" name="noID" id="noID" placeholder="Số CMT/CCCD"required maxlength="12" minlength="8" pattern="^\d{9}(\d{3})?$" placeholder="Số CMT/CCCD" data-missing-error="Vui lòng nhập số thẻ / hộ chiếu nhận dạng." data-parse-error="Số CMND/CCCD của Quý khách chưa đúng. Vui lòng kiểm tra lại." value="" data-input-invalid="Số CMND/CCCD của Quý khách chưa đúng. Vui lòng kiểm tra lại." data-exist="Số CMND/CCCD của quý khách đã được đăng kí" value="">
                                <i class="icon fa-regular fa-address-card"></i>
                                <div class="invalid-feedback"></div>

                            </div>
                            <div class="group-corporate">
                                <input class="name" type="text" name="companyID" id="companyID" maxlength="8"  placeholder="Số đăng kí kinh doanh"required  pattern="^0[0-9]{7}$" placeholder="Số đăng ký kinh doanh" data-missing-error="Vui lòng nhập MST/MSDN." data-parse-error="Số đăng ký kinh doanh của Quý khách chưa đúng. Vui lòng kiểm tra lại." value="" data-input-invalid="Số đăng ký kinh doanh của quý khách chưa đúng. Vui lòng kiểm tra lại." data-exist="Số đăng ký kinh doanh của quý khách đã được đăng kí" value="">
                                <i class="icon fa-solid fa-sack-dollar"></i>
                                <div class="invalid-feedback"></div>
                            </div>
                            <div class="group">
                                <input class="name" name="email" id="email" type="email" placeholder="Email"required maxlength="50" placeholder="Email" pattern="^([A-Za-z0-9_\-\.])+\@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,20}))$" data-email-logged="" data-missing-error="Vui lòng nhập email." data-parse-error="Email của Quý khách chưa đúng. Vui lòng kiểm tra lại." data-exist="Quý khách đã có tài khoản Auto99 với email này. Thông tin đơn hàng sẽ được hiển thị trong tài khoản của Quý khách." value="">
                                <i class="icon fa-solid fa-envelope"></i>
                                <div class="invalid-feedback"></div>
                            </div>
                            <div class="price">
                                <div class="info">
                                    <p>Giá công bố</p>
                                    <p>
                                        <input value="${Car.getPrice()}" hidden="" id="price">
                                        <fmt:formatNumber value="${Car.getPrice()}" type="number" pattern="#,###"/>
                                        <span>VNĐ</span>
                                    </p>
                                </div>
                            </div>

                            <div class="voucher row">
                                <div class="col-lg-10">
                                    <input class="vc" name="voucher" id="voucher" type="text" required maxlength="10" placeholder="Voucher" value="">
                                    <i class="icon fa-solid fa-ticket"></i>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="col-lg-2">
                                    <button type="button" id="checkvoucher" onclick="checkVoucher()" class="vouchersearch">
                                        <i class="fa-solid fa-magnifying-glass"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="price" id="finalPrice">
                                <div class="info">
                                    <p>Giá phải trả</p>
                                    <p>
                                        <input hidden value="${Car.getPrice()}" id="finalPriceValue">
                                        <fmt:formatNumber value="${Car.getPrice()}" type="number" pattern="#,###"/>
                                        <span>VNĐ</span>
                                    </p>
                                </div>
                            </div>

                            <script>
                                function checkVoucher() {
                                    var price = document.getElementById("price").value;
                                    var voucher = document.getElementById("voucher").value;

                                    // Gửi yêu cầu AJAX
                                    $.ajax({
                                        type: "POST",
                                        url: "processdeposit",
                                        data: {
                                            action: "checkVoucher",
                                            voucher: voucher,
                                            price: price
                                        },
                                        success: function (data) {
                                            var price = document.getElementById("finalPrice");
                                            price.innerHTML = data;
                                        }
                                    });
                                }
                                ;
                            </script>

                            <div class="error">Bạn phải điền đầy đủ thông tin và đúng định dạng</div>
                            <input type="button"  id="depositButton" value="BƯỚC TIẾP THEO"required> 
                        </form>
                    </div>

                    <div class="content hide" id="content2">
                    </div>
                </div>
            </div>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    // Lấy tất cả các phần tử input và invalid-feedback
                    var inputElements = document.querySelectorAll(".group-corporate .name");
                    var invalidFeedbacks = document.querySelectorAll(".group-corporate .invalid-feedback");

                    // Lặp qua tất cả các phần tử và gắn sự kiện input cho mỗi trường
                    inputElements.forEach(function (inputElement, index) {
                        var invalidFeedback = invalidFeedbacks[index];
                        var pattern = inputElement.getAttribute("pattern");

                        // Lắng nghe sự kiện input
                        inputElement.addEventListener("input", function () {
                            var value = inputElement.value;
                            invalidFeedback.style.display = "block";

                            // Kiểm tra điều kiện và hiển thị thông báo lỗi tương ứng
                            if (value === "") {
                                invalidFeedback.textContent = inputElement.getAttribute("data-missing-error");
                            } else if (!new RegExp(pattern).test(value)) {
                                invalidFeedback.textContent = inputElement.getAttribute("data-parse-error");
                            } else {
                                // Nếu không có lỗi, xóa thông báo lỗi
                                invalidFeedback.textContent = "";
                                invalidFeedback.style.display = "none";
                                
//                                 $.ajax({
//                                    type: "POST",
//                                    url: "processdeposit",
//                                    data: {
//                                        action: "checkNoIDExist",
//                                        noID: value
//                                    },
//                                    success: function (data) {
//                                        if (data.exists) {
//                                            invalidFeedback.textContent = inputElement.getAttribute("data-exist");
//                                            invalidFeedback.style.display = "block";
//                                        }
//                                    },
//                                    error: function (error) {
//                                        console.error("Lỗi khi kiểm tra email: " + error);
//                                    }
//                                });
                            }
                        });
                    });
                });

                document.addEventListener("DOMContentLoaded", function () {
                    // Lấy tất cả các phần tử input và invalid-feedback
                    var inputElements = document.querySelectorAll(".group-personal .name");
                    var invalidFeedbacks = document.querySelectorAll(".group-personal .invalid-feedback");

                    // Lặp qua tất cả các phần tử và gắn sự kiện input cho mỗi trường
                    inputElements.forEach(function (inputElement, index) {
                        var invalidFeedback = invalidFeedbacks[index];
                        var pattern = inputElement.getAttribute("pattern");

                        // Lắng nghe sự kiện input
                        inputElement.addEventListener("input", function () {
                            var value = inputElement.value;
                            invalidFeedback.style.display = "block";

                            // Kiểm tra điều kiện và hiển thị thông báo lỗi tương ứng
                            if (value === "") {
                                invalidFeedback.textContent = inputElement.getAttribute("data-missing-error");
                            } else if (!new RegExp(pattern).test(value)) {
                                invalidFeedback.textContent = inputElement.getAttribute("data-parse-error");
                            } else {
                                // Nếu không có lỗi, xóa thông báo lỗi
                                invalidFeedback.textContent = "";
                                invalidFeedback.style.display = "none";

//                                $.ajax({
//                                    type: "POST",
//                                    url: "processdeposit",
//                                    data: {
//                                        action: "checkNoIDExist",
//                                        noID: value
//                                    },
//                                    success: function (data) {
//                                        if (data.exists) {
//                                            invalidFeedback.textContent = inputElement.getAttribute("data-exist");
//                                            invalidFeedback.style.display = "block";
//                                        }
//                                    },
//                                    error: function (error) {
//                                        console.error("Lỗi khi kiểm tra email: " + error);
//                                    }
//                                });
                            }
                        });
                    });
                });

                document.addEventListener("DOMContentLoaded", function () {
                    // Lấy tất cả các phần tử input và invalid-feedback
                    var inputElements = document.querySelectorAll(".group .name");
                    var invalidFeedbacks = document.querySelectorAll(".group .invalid-feedback");

                    // Lặp qua tất cả các phần tử và gắn sự kiện input cho mỗi trường
                    inputElements.forEach(function (inputElement, index) {
                        var invalidFeedback = invalidFeedbacks[index];
                        var pattern = inputElement.getAttribute("pattern");

                        // Lắng nghe sự kiện input
                        inputElement.addEventListener("input", function () {
                            var value = inputElement.value;
                            invalidFeedback.style.display = "block";

                            // Kiểm tra điều kiện và hiển thị thông báo lỗi tương ứng
                            if (value === "") {
                                invalidFeedback.textContent = inputElement.getAttribute("data-missing-error");
                            } else if (!new RegExp(pattern).test(value)) {
                                invalidFeedback.textContent = inputElement.getAttribute("data-parse-error");
                            } else {
                                // Nếu không có lỗi, xóa thông báo lỗi
                                invalidFeedback.textContent = "";
                                invalidFeedback.style.display = "none";
                                
//                                $.ajax({
//                                    type: "POST",
//                                    url: "processdeposit",
//                                    data: {
//                                        action: "checkPhoneExist",
//                                        phoneNumber: value
//                                    },
//                                    success: function (data) {
//                                        if (data.exists) {
//                                            invalidFeedback.textContent = inputElement.getAttribute("data-exist");
//                                            invalidFeedback.style.display = "block";
//                                        }
//                                    },
//                                    error: function (error) {
//                                        console.error("Lỗi khi kiểm tra số điện thoại: " + error);
//                                    }
//                                });
                            }
                        });
                    });
                });


                var liLElement = document.querySelector(".tab-right li:last-child");
                var liFElement = document.querySelector(".tab-right li:first-child");
                liFElement.addEventListener("click", function () {
                    // Xóa lớp "active" khỏi phần tử cuối cùng
                    liLElement.classList.remove("active");
                    // Thêm lớp "active" vào phần tử đầu tiên
                    liFElement.classList.add("active");
                });

                // Lấy các phần tử cần điều chỉnh hiển thị
                var personalFields = document.querySelectorAll('.group-personal');
                var corporateFields = document.querySelectorAll('.group-corporate');

                // Lấy các phần tử label
                var personalLabel = document.querySelector('label[for="personalRadio"]');
                var corporateLabel = document.querySelector('label[for="corporateRadio"]');

                var isFormComplete = false;

                // Hàm kiểm tra thông tin đã điền đầy đủ hay chưa
                function checkFormCompletion() {
                    var fieldsToCheck = document.querySelectorAll('.name[required]');
                    isFormComplete = true;
                    fieldsToCheck.forEach(function (field) {
                        if (field.value.trim() === "") {
                            isFormComplete = false;
                            return;
                        }
                    });
                }



                // Sự kiện khi label "Cá Nhân" được chọn
                corporateFields.forEach(function (field) {
                    field.style.display = "none";
                    field.querySelector('input').removeAttribute('required');
                });

                personalLabel.addEventListener("click", function () {
                    // Hiển thị trường cá nhân và ẩn trường doanh nghiệp
                    personalFields.forEach(function (field) {
                        field.style.display = "block";
                        field.querySelector('input').setAttribute('required', 'required');
                    });
                    corporateFields.forEach(function (field) {
                        field.style.display = "none";
                        field.querySelector('input').removeAttribute('required');
                    });

                    // Khi chuyển sang trường cá nhân, kiểm tra thông tin đã điền đầy đủ
                    checkFormCompletion();
                });

                // Sự kiện khi label "Doanh Nghiệp" được chọn
                corporateLabel.addEventListener("click", function () {
                    // Hiển thị trường doanh nghiệp và ẩn trường cá nhân
                    corporateFields.forEach(function (field) {
                        field.style.display = "block";
                        field.querySelector('input').setAttribute('required', 'required');
                    });
                    personalFields.forEach(function (field) {
                        field.style.display = "none";
                        field.querySelector('input').removeAttribute('required');
                    });

                    // Khi chuyển sang trường doanh nghiệp, kiểm tra thông tin đã điền đầy đủ
                    checkFormCompletion();
                });

                const enterInfoButton = document.getElementById("enterInfoButton");
                const depositButton = document.getElementById("depositButton");
                const content1 = document.getElementById("content1");
                const content2 = document.getElementById("content2");

                // Mô phỏng sự kiện nhấp vào nút "Nhập thông tin" khi trang được tải
                window.addEventListener("load", function () {
                    enterInfoButton.click();
                });

                // Xử lý khi label được bấm
                document.querySelectorAll('label').forEach(label => {
                    label.addEventListener("click", function () {
                        const forId = this.getAttribute('for');
                        const button = document.getElementById(forId);
                        if (button) {
                            button.click();
                        }
                    });
                });

                // Xử lý khi nút "Nhập thông tin" được bấm
                enterInfoButton.addEventListener("click", function () {
                    content1.style.display = "block";
                    content2.style.display = "none";
                });



                depositButton.addEventListener("click", function (e) {
                    // Đặt biến hasErrors về false trước khi kiểm tra lại
                    var hasErrors = false;

                    // Lấy loại khách hàng được chọn
                    var selectedCustomerType = document.querySelector('input[name="customerType"]:checked').value;
                    var customerNameFieldId;
                    var noIDFieldId;
                    // Lấy tất cả các phần tử invalid-feedback liên quan đến loại khách hàng đã chọn
                    var invalidFeedbacks = document.querySelectorAll(".group-" + selectedCustomerType + " .invalid-feedback");
                    var invalidFeedback = document.querySelectorAll(".group .invalid-feedback");
                    // Kiểm tra lỗi dựa trên thuộc tính display của các phần tử invalid-feedback
                    invalidFeedbacks.forEach(function (feedback) {
                        if (feedback.textContent.trim() !== "" && feedback.style.display === "block") {
                            hasErrors = true;
                        }
                    });
                    invalidFeedback.forEach(function (feedback) {
                        if (feedback.textContent.trim() !== "" && feedback.style.display === "block") {
                            hasErrors = true;
                        }
                    });

                    // Kiểm tra hoàn thiện biểu mẫu
                    checkFormCompletion();

                    if (!isFormComplete || hasErrors) {
                        e.preventDefault(); // Ngăn việc submit nếu có lỗi hoặc biểu mẫu chưa hoàn thiện
                        var errorElement = document.querySelector(".error");
                        errorElement.style.display = "block";
                    } else {
                        content1.style.display = "none";
                        content2.style.display = "block";
                        liFElement.classList.remove("active");
                        liLElement.classList.add("active");
                        if (selectedCustomerType === "personal") {
                            customerNameFieldId = "customerName";
                            noIDFieldId = "noID";
                        } else if (selectedCustomerType === "corporate") {
                            customerNameFieldId = "companyName";
                            noIDFieldId = "companyID";
                        }

// Lấy giá trị tương ứng với loại khách hàng
                        var errorElement = document.querySelector(".error");
                        errorElement.style.display = "none";
                        var customerName = document.getElementById(customerNameFieldId).value;
                        var phoneNumber = document.getElementById("phoneNumber").value;
                        var noID = document.getElementById(noIDFieldId).value;
                        var email = document.getElementById("email").value;
                        var finalPriceValue = document.getElementById("finalPriceValue").value;
                        var carID = document.getElementById("carID").value;
                        $.ajax({
                            url: "/Auto99/processdeposit", // Replace with your servlet URL
                            method: "POST",
                            data: {
                                customerName: customerName,
                                phoneNumber: phoneNumber,
                                selectedCustomerType: selectedCustomerType,
                                noID: noID,
                                email: email,
                                action: "changeForm",
                                finalPriceValue: finalPriceValue,
                                carID: carID
                            },
                            success: function (data) {
                                // Replace the car list content with the new data

                                content2.innerHTML = data;

                            }
                        });
                    }
                });

            </script>

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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </body>
</html>