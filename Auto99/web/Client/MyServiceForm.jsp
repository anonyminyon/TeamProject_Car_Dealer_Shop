<%-- 
    Document   : MyServiceForm
    Created on : Oct 31, 2023, 10:05:23 AM
    Author     : Hieuht
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="dal.EmployeeProfileDAO" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">

        <!--Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <title>My Service Form</title>
        <!--Additional CSS Files -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="assets/css/HomePage.css">
        <link rel="stylesheet" href="assets/css/Header.css">
        <link rel="stylesheet" href="assets/css/owl.css">
        <link rel="stylesheet" href="assets/css/Footer.css">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
        <style>
            .card-deck {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-around;
                margin-bottom: 20px;
            }

            .card {
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
                width: 100%;
            }

            .card-body {
                padding: 20px;
            }

            .card-title {
                font-size: 1.5rem;
                font-weight: bold;
                margin-bottom: 10px;
            }

            .card-text {
                line-height: 1.6;
            }

            .btn-info {
                background-color: #17a2b8;
                color: #fff;
            }

            .btn-info:hover {
                background-color: #138496;
            }

            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.7);
                z-index: 1;
            }

            .modal-content {
                background-color: #fff;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                border-radius: 5px;
                max-width: 80%;
                text-align: center;
                position: relative;
            }

            .close {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 20px;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <%@include file="../Client/Header.jsp" %>
        <!-- Header Ends Hear -->
        <div class="container" style="padding-top: 5rem">
            <h1 style="color: #333; text-align: center; font-weight: bold; margin-bottom: 20px;">History Service Invoice</h1>
            <div class="row d-flex justify-content-around" >
                <c:forEach items="${historyInvoice}" var="invoice">
                    <div class="col-md-6">
                        <div class="card-deck">
                            <div class="card data-card" style="box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); margin-bottom: 20px;">
                                <div class="card-body">
                                    <h5 class="card-title">Mã hóa đơn dịch: ${invoice.serviceInvoiceID}</h5><!--Service Invoice ID-->
                                    <p class="card-text">
                                        <!--<strong>:</strong> ${invoice.getClientServiceID().getClientServiceID()}<br>Client Service ID-->
                                        <!--<strong>:</strong> ${invoice.getClientServiceID().getClientID()}<br><!--Client ID -->
                                        <strong>Tên dịch vụ:</strong> ${invoice.getClientServiceID().getServiceID().getServiceType()}<br><!--Service ID -->
                                        <strong>Biển số xe của xe làm dịch vụ:</strong> ${invoice.getClientServiceID().getNumberPlate()}<br><!--Number Plate -->
                                        <strong>Ngày đăng ký làm dịch vụ:</strong> ${invoice.getClientServiceID().getDateService()}<br><!--Date Service -->
                                        <strong>Hãng xe đăng ký làm dịch vụ:</strong> ${invoice.getClientServiceID().getBrandID().getBrandName()}<br><!--Brand name -->
                                        <strong>Trạng thái dịch vụ:</strong> ${invoice.getClientServiceID().getStatus()}<br><!--Status -->

                                        <strong>Tên nhân viên tạo hóa đơn:</strong> ${invoice.getClientServiceID().getEmployeeID().getFirstName()}<br><!--Employee name -->
                                        <strong>Tên trưởng nhân viên thợ máy làm dịch vụ:</strong> ${invoice.getClientServiceID().getCrewChiefID().getFirstName()}<br><!--Crew Chief name -->
                                        <strong>Ngày hoàn thành và tạo hóa đơn dịch vụ:</strong> ${invoice.dateComplete}<br><!--Date Complete -->                                        
                                        
                                        <!-- ... existing code ... -->
                                        <strong>Feedback:</strong>
                                        <p style="margin: 10px 0; padding: 10px; border: 1px solid #ddd; border-radius: 5px; background-color: #f9f9f9;">
                                            ${invoice.feedback}
                                        </p>
                                    <a href="javascript:void(0);" class="btn btn-info" onclick="sendAction('${invoice.serviceInvoiceID}', 'viewFeedback')">View & Feedback</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <script>
                    function sendAction(serviceInvoiceID, action) {
                        var form = document.createElement("form");
                        form.setAttribute("method", "post");
                        form.setAttribute("action", "myserviceform"); //  URL của servlet 

                        var serviceInvoiceIDInput = document.createElement("input");
                        serviceInvoiceIDInput.setAttribute("type", "hidden");
                        serviceInvoiceIDInput.setAttribute("name", "serviceInvoiceID");
                        serviceInvoiceIDInput.setAttribute("value", serviceInvoiceID);

                        var actionInput = document.createElement("input");
                        actionInput.setAttribute("type", "hidden");
                        actionInput.setAttribute("name", "action");
                        actionInput.setAttribute("value", action);

                        form.appendChild(serviceInvoiceIDInput);
                        form.appendChild(actionInput);

                        document.body.appendChild(form);
                        form.submit();
                    }
                </script>
            </div>
        </div>
        <!--MODAL-->
        <div id="myModal" class="modal" style="display: none; position: fixed; z-index: 1; padding-top: 100px; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4);">
            <div class="modal-content" style="margin: auto; padding: 20px; border: 1px solid #888; width: 50%; background-color: #fff;">
                <span class="close" style="position: absolute; top: 0; right: 0; padding: 10px; font-size: 24px; font-weight: bold; cursor: pointer;" onclick="closeModal()">&times;</span>
                <p id="modalMessage" style="font-weight: bold; font-size: 32px; line-height: 1.2; letter-spacing: 1px; font-family: sans-serif;"></p>
            </div>
        </div>
        <style>
            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.7);
                z-index: 1;
            }

            .modal-content {
                background-color: #fff;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                border-radius: 5px;
                max-width: 80%;
                text-align: center;
                position: relative;
            }

            .close {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 20px;
                cursor: pointer;
            }
        </style>
        <script>
            // Function to open the modal with a message
            function openModal(message) {
                var modal = document.getElementById("myModal");
                var modalMessage = document.getElementById("modalMessage");
                modalMessage.innerHTML = message;
                modal.style.display = "block";
            }

            // Function to close the modal
            function closeModal() {
                var modal = document.getElementById("myModal");
                modal.style.display = "none";

            }

            // Close the modal if the user clicks outside of it
            window.onclick = function (event) {
                var modal = document.getElementById("myModal");
                if (event.target === modal) {
                    modal.style.display = "none";

                }
            }
        </script>
        <c:if test="${not empty message}">
            <script>
                openModal("${message}");
            </script>
        </c:if>
        <!--MODAL-->


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
