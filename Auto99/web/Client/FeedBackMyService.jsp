<%-- 
    Document   : FeedBackMyService
    Created on : Oct 31, 2023, 10:47:18 AM
    Author     : Hieuht
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--this taglib using for function jstl change string to float-->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
            
        </style>
    </head>
    <body>

        <!-- Header -->
        <%@include file="../Client/Header.jsp" %>
        <!-- Header Ends Hear -->
        <div class="container-fluid" style="padding: 5rem;
             background-image: url('./img/Blog/7-xu-huong-tac-dong-toi-nganh-dich-vu-tai-chinh-hau-covid-19-53-.3839.jpg');
             background-size: cover; background-position: center; background-repeat: no-repeat;">
            <section class="h-100 h-custom" >
                <div class="container py-5 h-100">
                    <div class="row d-flex justify-content-center align-items-center h-100">
                        <div class="col-lg-8 col-xl-6">
                            <div class="card border-top border-bottom border-3" style="border-color: red !important;">
                                <div class="card-body p-5">
                                    <p class="lead fw-bold mb-5" style="color: red;">HÓA ĐƠN DỊCH VỤ</p>
                                    <div class="row">
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Tên khách hàng:<p>${historyServiceInvoice.getClientServiceID().getClientID().getClientName()}</p></p>                                           
                                        </div>
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Dịch Vụ:</p>
                                            <p >${historyServiceInvoice.getClientServiceID().getServiceID().getServiceType()}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Số điện thoại:</p>
                                            <p >${historyServiceInvoice.getClientServiceID().getClientID().getPhoneNumber()}</p>
                                        </div>
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Biển số xe:</p>
                                            <p >${historyServiceInvoice.getClientServiceID().getNumberPlate()}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Ngày Hẹn đến làm dịch vụ:</p>
                                            <p >${historyServiceInvoice.getClientServiceID().getDateService()}</p>
                                        </div>
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Email:</p>
                                            <p >${historyServiceInvoice.getClientServiceID().getClientID().getEmail()}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Nhân Viên Duyệt đơn:</p>
                                            <p >${historyServiceInvoice.getClientServiceID().getEmployeeID().getLastName()}</p>
                                        </div>
                                        <div class="col mb-3">
                                            <p class="small text-muted mb-1">Trưởng thợ máy:</p>
                                            <p >${historyServiceInvoice.getClientServiceID().getCrewChiefID().getFirstName()}</p>
                                        </div>
                                    </div>
                                    <p class="text-right"><label>Ngày chốt đơn:  ${historyServiceInvoice.getDateComplete()}</label></p>
                                    <p class="lead fw-bold mb-5" style="color: red;">THÔNG TIN ĐƠN HÀNG</p>
                                    <div class="mx-n5 px-5 py-4" style="background-color: #f2f2f2;">
                                        <div class="row">
                                            <div class="col-md-8 col-lg-9">
                                                <p style="font-weight: bold;">Tên sản phẩm x số lượng x giá</p>
                                            </div>
                                            <div class="col-md-4 col-lg-3">
                                                <p style="font-weight: bold;">Tổng</p>
                                            </div>
                                        </div>
                                        <c:forEach items="${listProduct}" var="product" varStatus="loopStatus">
                                            <div class="row">
                                                <div class="col-md-8 col-lg-9">
                                                    <p>${product.getProductName()} x ${fn:replace(product.getQuantity(), ',', '')} x ${fn:replace(product.getUnitPrice(), ',', '')} vnd</p>
                                                </div>
                                                <div class="col-md-4 col-lg-3">
                                                    <p><fmt:formatNumber value="${(product.unitPrice.replace(',', '') * product.quantity)}" type="currency" /> vnd</p>
                                                </div>
                                            </div>
                                        </c:forEach>

                                    </div>

                                    <div class="row my-4">
                                        <div class="col-md-4 offset-md-8 col-lg-3 offset-lg-9">
                                            <p class="lead fw-bold mb-0" style="color: red;"></p>
                                        </div>
                                    </div>

                                    <p class="lead fw-bold mb-4 pb-2" style="color: red;">comment</p>


                                    <p class="mt-4 pt-2 mb-0">Want any help? <a href="#!" style="color: red;">Please contact
                                            us</a></p>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="background-color: #eee;border-radius: 2rem;">
                            <div class="col-lg-12">
                                <div class="horizontal-timeline">
                                    <form action="myserviceform" method="post">
                                        <label for="comment">Comment:</label>
                                        <br>
                                        <!-- Use the rows and cols attributes to set the size of the text area -->
                                        <textarea id="comment" name="comment" rows="5" cols="50"></textarea>
                                        <br>
                                        <input type="submit" value="Submit Comment"><span><a class="btn-danger" style="border-radius: 5px;"href="javascript:history.back()">Go Back</a></span>
                                        <input type="hidden" name="action" value="updateFeedBack"/>
                                        <input type="hidden" name="serviceInvoiceID" value="${historyServiceInvoice.getServiceInvoiceID()}"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section> 

        </div>

        <!--MODAL-->
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <p id="modalMessage">Your message here</p>
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

