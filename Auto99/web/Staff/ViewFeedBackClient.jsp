<%-- 
    Document   : ViewFeedBackClient
    Created on : Nov 16, 2023, 2:50:39 AM
    Author     : Hieu
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--this taglib using for function jstl change string to float-->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta charset="UTF-8">    
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="./assets/Staff/css/AdminPage.css">
        <link rel="stylesheet" href="assets/Staff/css/Table.css">


        <!----===== font awesome CSS ===== -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>

        <link rel="stylesheet" href="./assets/Staff/css/main.css">

        <!-- Main CSS-->
        <!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">-->

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <title>Service appointment List</title>
    </head>
    <body>
        <div class="wrapper">
            <!-- Header -->
            <%@include file="NavbarForStaffPage.jsp" %>
            <!-- Header Ends Hear -->

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item"><a href="servicemanagement">Danh sách dịch vụ</a></li>
                        <li class="breadcrumb-item">Quản lý dịch vụ</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
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
                    </div>
                </div>
            </div>

        </div>

        <!-- Essential javascripts for application to work-->
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="src/jquery.table2excel.js"></script>
        <script src="js/main.js"></script>
        <!-- The javascript plugin to display page loading on top-->
        <script src="js/plugins/pace.min.js"></script>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
    </body>
</html>


