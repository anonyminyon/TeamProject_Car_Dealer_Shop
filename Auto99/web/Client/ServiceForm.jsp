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
        <!--thu vien cho date-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

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
                font:  700 25px/20px 'Toyota Type', sans-serif; /* Make the text bold */
                padding: 0;
            }
            #Tieu-de {
                font:  554 36px/50px 'Toyota Type', sans-serif; /* Make the text bold */
            }
        </style>

        <style>
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
        </style>
    </head>

    <body>


        <!-- Header -->
        <%@include file="../Client/Header.jsp" %>

        <!-- Header Ends Hear -->

        <div class="container-fluid">
            <!--Content Page-->
            <!--              <div class="row d-flex justify-content-around" style=" padding-top: 5rem; overflow: hidden; width: 100%; height: 100vh;">
                                    <video autoplay muted loop style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: -1; object-fit: cover">
                                        <source src="./img/Background-service.mp4" type="video/mp4">
                                    </video>-->
            <div class="row d-flex justify-content-around" style="padding-top: 10rem; padding-bottom: 10rem; background: url('./img/backgroundService.jpg') no-repeat top center fixed; background-size: cover;">
                <div class="box" style="background-color: white; padding: 2rem; width: 50%; margin: 0.5rem auto; font-size: 1.5rem; border-radius: 1rem;">
                    <!-- Content of the box -->
                    <h1 id="Tieu-de">ĐẶT LỊCH HẸN DỊCH VỤ</h1>
                    <ul>
                        <form action="serviceform" method="POST" name="Lhebook" id="Lhebook" autocomplete="off">
                            <li>
                                <label for="serviceID">Dịch vụ (*)</label>
                                <select name="serviceID" id="serviceID" tabindex="3">
                                    <option value="">Chọn kiểu dịch vụ</option> <!-- Placeholder option -->
                                    <c:forEach items="${ListServiceAvailable}" var="service">
                                        <option value="${service.getServiceID()}">${service.getServiceType()}</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <div id="errorServiceID" class="error-text" style="display: none;">Please choose service</div>
                            <hr />
                            <li>
                                <label for="brandID">Hãng xe (*)</label>
                                <select name="brandID" id="brandID" tabindex="3">
                                    <option value="" >Chọn hãng xe của bạn</option> <!-- Placeholder option -->
                                    <c:forEach items="${ListCarBrand}" var="brand">
                                        <option value="${brand.getBrandID()}">${brand.getBrandName()}</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <div id="errorBrandID" class="error-text" style="display: none;">Please choose your car brand</div>
                            <hr />
                            <li>
                                <label>Biển số xe (*)</label><br>
                                <input name="numberPlate" type="text" id="numberPlate" placeholder="VD: 59 D1 - 05494" value="" tabindex="3">

                            </li>
                            <div id="errorNumberPlate" class="error-text" style="display: none;">Please fill text and valid number plate</div>
                            <hr/>
                            <li>
                                <label>Họ và tên (*)</label><br>
                                <input name="clientName" type="text" id="clientName" placeholder="VD: Nguyễn Văn A" value="" tabindex="3">

                            </li>
                            <div id="errorClientName" class="error-text" style="display: none;">Please fill text and valid name</div>
                            <hr/>
                            <li>
                                <label>Số điện thoại (*)</label><br>
                                <input name="phoneNumber" type="text" id="phoneNumber" placeholder="VD: 090 325 9295" value="" minlength="10" maxlength="10" tabindex="3">

                            </li>
                            <div id="errorPhoneNumber" class="error-text" style="display: none;">Please fill text and valid phone number </div>
                            <hr/>
                            <li>
                                <label>Email (for send notification)</label><br>
                                <input name="email" type="text" id="email" placeholder="something@gmail.com" value="" tabindex="3">

                            </li>
                            <div id="errorEmail" class="error-text" style="display: none;">Please fill text and valid email</div>
                            <hr/>
                            <li> 
                                <label>Ngày dự kiến (*)</label><br>
                                <input name="dateService" type="text" id="dateService" value="" tabindex="3" readonly>
                            </li>

                            <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    const dateServiceInput = document.getElementById("dateService");

                                    // Calculate the date 7 days from today
                                    const today = new Date();
                                    const sevenDaysLater = new Date(today);
                                    sevenDaysLater.setDate(today.getDate() + 7);

                                    flatpickr(dateServiceInput, {
                                        altInput: true, // Display the formatted date in the input
                                        altFormat: "d-m-Y", // Set the desired formatted date format (dd-mm-yyyy)
                                        minDate: "today", // Minimum date is today
                                        maxDate: sevenDaysLater, // Maximum date is 7 days from today
                                    });
                                });
                            </script>

                            <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
                            <hr/>
                            <ul>
                                <li class="checkbox-list">
                                    <div class="checkbox">
                                        <div><i class="far fa-square"></i>Tôi xác nhận cung cấp thông tin cá nhân để liên hệ với Auto99.</div>
                                        <input name="txtAgree1" type="hidden" id="txtAgree5" value="0">
                                    </div>
                                </li>
                                <li class="checkbox-list">
                                    <div class="checkbox">
                                        <div><i class="far fa-square"></i>Tôi đã đọc và đồng ý với các quy định và chính sách của Auto99.</div>
                                        <input name="txtAgree2" type="hidden" id="txtAgree6" value="0">
                                    </div>
                                </li>
                            </ul>


                            <script>
                                // Get all checkbox elements
                                const checkboxes = document.querySelectorAll('.checkbox');

                                // Add a click event listener to each checkbox
                                checkboxes.forEach(checkbox => {
                                    checkbox.addEventListener('click', function () {
                                        const icon = this.querySelector('i');
                                        const hiddenInput = this.querySelector('input');

                                        // Toggle the checked state
                                        if (hiddenInput.value === '0') {
                                            hiddenInput.value = '1';
                                            icon.classList.remove('far', 'fa-square');
                                            icon.classList.add('fas', 'fa-check-square');
                                            this.classList.add('checked');
                                        } else {
                                            hiddenInput.value = '0';
                                            icon.classList.remove('fas', 'fa-check-square');
                                            icon.classList.add('far', 'fa-square');
                                            this.classList.remove('checked');
                                        }
                                    });
                                });
                            </script>
                            <input tabindex="3" type="button" name="button" onclick="LheSubmit();" value="Xác nhận đặt lịch hẹn">
                            <li class="last">
                                <script>
                                    function LheSubmit() {
                                        // Get the form element
                                        const form = document.getElementById('Lhebook');

                                        // You can access the checkbox values like this:
                                        // Get the values of the checkboxes
                                        const txtAgree1 = document.getElementById('txtAgree5').value;
                                        const txtAgree2 = document.getElementById('txtAgree6').value;

                                        // Check if the checkboxes are checked
                                        if (txtAgree1 === '1' && txtAgree2 === '1') {
                                            // You can access individual form elements and their values like this:
                                            const serviceID = form.querySelector('#serviceID').value;
                                            const brandID = form.querySelector('#brandID').value;
                                            const numberPlate = form.querySelector('#numberPlate').value;
                                            const clientName = form.querySelector('#clientName').value;
                                            const phoneNumber = form.querySelector('#phoneNumber').value;
                                            const email = form.querySelector('#email').value;

                                            // You can also get the corresponding error message elements
                                            const errorServiceID = document.getElementById('errorServiceID');
                                            const errorBrandID = document.getElementById('errorBrandID');
                                            const errorNumberPlate = document.getElementById('errorNumberPlate');
                                            const errorClientName = document.getElementById('errorClientName');
                                            const errorPhoneNumber = document.getElementById('errorPhoneNumber');
                                            const errorEmail = document.getElementById('errorEmail');

                                            // Hide all error messages initially
                                            errorServiceID.style.display = 'none';
                                            errorBrandID.style.display = 'none';
                                            errorNumberPlate.style.display = 'none';
                                            errorClientName.style.display = 'none';
                                            errorPhoneNumber.style.display = 'none';
                                            errorEmail.style.display = 'none';

                                            let hasErrors = false;

                                            if (serviceID == "" || serviceID === null) {
                                                // Display an error message under the input field
                                                errorServiceID.style.display = 'block';
                                                hasErrors = true;
                                            }
                                            if (brandID == "" || brandID === null) {
                                                // Display an error message under the input field
                                                errorBrandID.style.display = 'block';
                                                hasErrors = true;
                                            }
                                            if (numberPlate == "" || numberPlate === null || !isValidNumberPlate(numberPlate)) {
                                                // Display an error message under the input field
                                                errorNumberPlate.style.display = 'block';
                                                hasErrors = true;
                                            }
                                            if (clientName == "" || clientName === null) {
                                                // Display an error message under the input field
                                                errorClientName.style.display = 'block';
                                                hasErrors = true;
                                            }
                                            if (phoneNumber == "" || phoneNumber === null || !isValidPhoneNumber(phoneNumber)) {
                                                // Display an error message under the input field
                                                errorPhoneNumber.style.display = 'block';
                                                hasErrors = true;
                                            }
                                            if (email == "" || email === null || !isValidEmail(email)) {
                                                // Display an error message under the input field
                                                errorEmail.style.display = 'block';
                                                hasErrors = true;
                                            }

                                            if (hasErrors) {
                                                // There are validation errors, do not submit the form
                                                return;
                                            }
                                            // If all checks pass, submit the form
                                            form.submit();
                                        } else {
                                            alert('Please agree with the policy.');
                                        }
                                    }


                                    // Helper function to validate  numbers plate
                                    function isValidNumberPlate(numberPlate) {

                                        return true;
                                    }

                                    // Helper function to validate phone numbers 
                                    function isValidPhoneNumber(phoneNumber) {

                                        return /^[0-9\s]+$/.test(phoneNumber);
                                    }

                                    // Helper function to validate email addresses 
                                    function isValidEmail(email) {
                                        // For simplicity, we're checking if it contains an "@" symbol
                                        return /@/.test(email);
                                    }

                                    document.addEventListener("DOMContentLoaded", function () {
                                        // Get the value of ${mess} from your server-side code
                                        const messValue = "${mess}"; // Replace with the actual value

                                        // Check if ${mess} has a value
                                        if (messValue.trim() !== "") {
                                            // Show the modal
                                            const modal = new bootstrap.Modal(document.getElementById("exampleModal"));
                                            modal.show();
                                        }
                                    });

                                </script>
                                <style>
                                    .error-text{
                                        color: red;
                                    }
                                </style>
                            </li>
                        </form>

                    </ul>
                </div>
            </div>
        </div>    

        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" 
             aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <p style="font-weight: bold; font-size: 32px; line-height: 1.2; letter-spacing: 1px; font-family: sans-serif;">${mess}</p>
                    </div>
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

    </body>
</html>