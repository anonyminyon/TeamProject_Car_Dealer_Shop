<%-- 
    Document   : ManageServiceClient
    Created on : Oct 15, 2023, 2:52:19 AM
    Author     : hieu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
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

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>
            <!-- Body-content-->
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
                                <div class="row element-button">
                                    <div class="col">
                                        <c:url var="addService" value="ServiceCRUD" >
                                            <c:param name="index" value="${index}" />
                                            <c:param name="search" value="${search}" />
                                            <c:param name="pageSize" value="${pageSize}" />
                                            <c:param name="action" value="addService" />
                                        </c:url>
                                    </div>

                                </div>
                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-6">
                                        <div class="length-box" >

                                            <form>
                                                <label>Hiện danh mục
                                                    <select id="quantitySelect" name="length" onchange="changeValues(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                        <option value="5" <c:if test="${pageSize == 5}">selected</c:if>>5</option>
                                                        <option value="10" <c:if test="${pageSize == 10}">selected</c:if>>10</option>
                                                        <option value="25" <c:if test="${pageSize == 25}">selected</c:if>>25</option>
                                                        <option value="50" <c:if test="${pageSize == 50}">selected</c:if>>50</option>
                                                        </select>
                                                    </label>
                                                    <label> Trạng thái hoạt động
                                                        <select id="changeStatus" name="serviceStatusFilter" onchange="changeValues(this)" class="form-control form-control-sm">
                                                            <option value="" >Chọn</option>
                                                            <option value="CHỜ DUYỆT ĐƠN" ${serviceStatusFilter == 'CHỜ DUYỆT ĐƠN'?'selected':''}>CHỜ DUYỆT ĐƠN</option>
                                                        <option value="ĐANG LÀM DỊCH VỤ" ${serviceStatusFilter == 'ĐANG LÀM DỊCH VỤ'?'selected':''}>ĐANG LÀM DỊCH VỤ</option>
                                                        <option value="DỊCH VỤ ĐÃ HOÀN THÀNH" ${serviceStatusFilter == 'DỊCH VỤ ĐÃ HOÀN THÀNH'?'selected':''}>DỊCH VỤ ĐÃ HOÀN THÀNH</option>
                                                    </select>
                                                </label>
                                            </form>

                                            <script>
                                                function changeValues(selectElement) {
                                                    var quantitySelect = document.getElementById('quantitySelect');
                                                    var changeStatus = document.getElementById('changeStatus');
                                                    var selectedQuantity = quantitySelect.value;
                                                    var selectedStatus = changeStatus.value;
                                                    // Create a form element
                                                    var form = document.createElement('form');
                                                    form.method = 'get';
                                                    form.action = 'manageserviceclient'; // Set the URL of your servlet

                                                    // Create an input element to hold the selected quantity
                                                    var quantityInput = document.createElement('input');
                                                    quantityInput.type = 'hidden'; // Hidden input field
                                                    quantityInput.name = 'pageSize'; // Name should match the parameter name in your servlet
                                                    quantityInput.value = selectedQuantity;
                                                    // Create an input element to hold the selected status
                                                    var statusInput = document.createElement('input');
                                                    statusInput.type = 'hidden'; // Hidden input field
                                                    statusInput.name = 'serviceStatusFilter'; // Name should match the parameter name in your servlet
                                                    statusInput.value = selectedStatus;
                                                    // Append the inputs to the form and then the form to the body
                                                    form.appendChild(quantityInput);
                                                    form.appendChild(statusInput);
                                                    document.body.appendChild(form);
                                                    // Submit the form
                                                    form.submit();
                                                }
                                            </script>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-6">
                                        <form action="manageserviceclient" method="get">
                                            <div class="search-box">
                                                <label>
                                                    Tìm kiếm:<input type="search" value="${search}" required = "required" name="search" class="form-control form-control-sm" placeholder="" aria-controls="sampleTable">
                                                </label>
                                                <input type="hidden" name="index" value="1" />
                                                <input type="hidden" name="pageSize" value="${pageSize}"/>
                                                <input type="hidden" name="serviceStatusFilter" value="${serviceStatusFilter}" />
                                                <button type="submit" class="btn btn-outline-success"><i class="fa-solid fa-search"></i></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <form id="SendAllServiceIDChecked" action="manageserviceclient" method="POST">
                                    <table class="table table-hover table-bordered" id="sampleTable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center" >ID</th>
                                                <th style="text-align: center">ID Khách Hàng/Tên khách hàng</th>
                                                <th style="text-align: center">ID Dịch Vụ/Kiểu dịch vụ</th>
                                                <th style="text-align: center">Biển Số XE</th>
                                                <th style="text-align: center">Ngày Hẹn Sử Dụng Dịch Vụ</th>
                                                <th style="text-align: center">Trạng Thái Lịch Đặt</th>
                                                <th style="text-align: center">Chức năng</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="clientService" items="${clientServiceList}" >
                                                <tr>
                                                    <td style="text-align: center">${clientService.getClientServiceID()}</td>
                                                    <td style="text-align: left">${clientService.getClientID().getClientID()}/${clientService.getClientID().getClientName()}</td>
                                                    <td style="text-align: left">${clientService.getServiceID().getServiceID()}/${clientService.getServiceID().getServiceType()}</td> 
                                                    <td style="text-align: center">${clientService.getNumberPlate()}</td>
                                                    <fmt:formatDate value="${clientService.getDateService()}" pattern="dd-MM-yyyy" var="formattedDate" />                                                                                  
                                                    <td style="text-align: center">${formattedDate}</td>
                                                    <td style="text-align: center">
                                                        <c:choose>
                                                            <c:when test="${clientService.getStatus() eq 'CHỜ DUYỆT ĐƠN'}">
                                                                <span class="badge bg-danger">${clientService.getStatus()}</span>
                                                            </c:when>
                                                            <c:when test="${clientService.getStatus() eq 'ĐANG LÀM DỊCH VỤ'}">
                                                                <span class="badge bg-warning">${clientService.getStatus()}</span>
                                                            </c:when>
                                                            <c:when test="${clientService.getStatus() eq 'DỊCH VỤ ĐÃ HOÀN THÀNH'}">
                                                                <span class="badge bg-success">${clientService.getStatus()}</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td style="text-align: center">
                                                        <button class="btn btn-primary btn-sm edit" type="button" title="Xem thông tin đơn" id="show-emp" data-toggle="modal"
                                                                data-target="#ModalUP${clientService.getClientServiceID()}"><i class="fas fa-edit"></i></button>
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </form>

                                <div class="row">
                                    <div class="col-sm-12 col-md-5">
                                        <div class="dataTables_info" role="status" >
                                            <c:choose>
                                                <c:when test = "${count>0}">
                                                    Hiện ${(index-1)*pageSize+1} đến 
                                                    ${((index*pageSize)>count)?count:index*pageSize}
                                                    của ${count} danh mục
                                                </c:when>

                                                <c:otherwise>
                                                    Không có sản phẩm!
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-7">
                                        <div class="dataTables_paginate">
                                            <ul class="pagination">

                                                <li class="paginate_button page-item previous <c:if test="${index==1}">disabled</c:if>" id="sampleTable_previous">
                                                    <a href="manageserviceclient?index=${index -1}&search=${search}&pageSize=${pageSize}&serviceStatusFilter=${serviceStatusFilter}" class="page-link">Lùi</a>
                                                </li>

                                                <c:forEach begin="1" end="${endPage}" var="i">

                                                    <li class="paginate_button page-item ${index == i?" active ":" "}">
                                                        <a href="manageserviceclient?index=${i}&search=${search}&pageSize=${pageSize}&serviceStatusFilter=${serviceStatusFilter}" class="page-link">${i}</a>
                                                    </li>

                                                </c:forEach>

                                                <li class="paginate_button page-item next <c:if test="${index==endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="manageserviceclient?index=${index +1}&search=${search}&pageSize=${pageSize}&serviceStatusFilter=${serviceStatusFilter}" class="page-link">Tiếp</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--            
            MODAL FOR CHỜ DUYỆT ĐƠN
            -->     
            <c:forEach var="clientService" items="${clientServiceList}">
                <form action="manageserviceclient" method="POST" >
                    <div class="modal fade" id="ModalUP${clientService.getClientServiceID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                         data-keyboard="false" >
                        <div class="modal-dialog modal-dialog-centered"  role="document">
                            <div class="modal-content" >
                                <div class="modal-body" >
                                    <div class="row">
                                        <div class="form-group  col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5>Xem thông tin cơ bản của dịch vụ</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row" >
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Tên khách hàng</label>
                                            <input class="form-control" type="text" type="number" required value="${clientService.getClientID().getClientName()}" readonly>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Dịch Vụ</label>
                                            <input class="form-control" type="text" required value="${clientService.getServiceID().getServiceType()}" readonly>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Số điện thoại</label>
                                            <input class="form-control" type="text" required value="${clientService.getClientID().getPhoneNumber()}" readonly>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Biển số xe</label>
                                            <input class="form-control" type="text" required value="${clientService.getNumberPlate()}" readonly>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Ngày Hẹn đến làm dịch vụ</label>
                                            <fmt:formatDate value="${clientService.getDateService()}" pattern="dd-MM-yyyy" var="formattedDate" />                                      
                                            <input class="form-control" type="text" required value=" ${formattedDate}" readonly>
                                        </div>                                      
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Email</label>
                                            <input class="form-control" type="text" required value="${clientService.getClientID().getEmail()}" readonly>
                                        </div> 
                                        <div class="form-group col-md-6 ">
                                            <label for="exampleSelect1" class="control-label">Trạng thái dịch vụ</label>
                                            <!-- Add an id to the <select> element for easy access -->
                                            <select id="clientServiceStatus" class="form-control" name="ClientServiceStatus" disabled>
                                                <option value="CHỜ DUYỆT ĐƠN" ${clientService.getStatus() eq 'CHỜ DUYỆT ĐƠN' ? 'selected' : ''}>CHỜ DUYỆT ĐƠN</option>
                                                <option value="ĐANG LÀM DỊCH VỤ" ${clientService.getStatus() eq 'ĐANG LÀM DỊCH VỤ' ? 'selected' : ''}>ĐANG LÀM DỊCH VỤ</option>
                                                <option value="DỊCH VỤ ĐÃ HOÀN THÀNH" ${clientService.getStatus() eq 'DỊCH VỤ ĐÃ HOÀN THÀNH' ? 'selected' : ''}>DỊCH VỤ ĐÃ HOÀN THÀNH</option>
                                            </select>
                                        </div>                                      
                                        <input id="clientServiceID" class="form-control" type="hidden" value="${clientService.getClientServiceID()}" name="clientServiceID" />
                                    </div>
                                    <BR>
                                    <BR>
                                    <BR>
                                    <!<!-- this function using to check status of a clientService then when click button Duyệt đơn it will send action different  -->
                                    <c:choose>
                                        <c:when test="${clientService.getStatus() eq 'CHỜ DUYỆT ĐƠN'}">
                                            <input type="hidden" name="action" value="SendMailConfirmAppointment" />
                                        </c:when>
                                        <c:when test="${clientService.getStatus() eq 'ĐANG LÀM DỊCH VỤ'}">
                                            <input type="hidden" name="action" value="CompleteServiceAppointment" />
                                            <input type="hidden" name="isCompleteInvoice" value="false" />
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Set a default action if neither condition is met -->
                                            <input type="hidden" name="action" value="ViewFeedback" />
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="hidden" name="index" value="${index}" />
                                    <input type="hidden" name="pageSize" value="${pageSize}" />
                                    <input type="hidden" name="search" value="${search}" />

                                    <!-- The "serviceStatusFilter" input field (hidden) -->
                                    <input type="hidden" name="serviceStatusFilter" value="${serviceStatusFilter}"/>

                                    <c:choose>
                                        <c:when test="${clientService.status eq 'CHỜ DUYỆT ĐƠN'}">
                                            <button class="btn btn-save" type="submit">Duyệt đơn</button>
                                        </c:when>
                                        <c:when test="${clientService.status eq 'ĐANG LÀM DỊCH VỤ'}">
                                            <button class="btn btn-save" type="submit">Tạo hóa đơn</button>
                                        </c:when>                                      
                                        <c:otherwise>
                                            <!-- Default button text if status doesn't match any case -->
                                            <button class="btn btn-save" type="submit">Xem Phản Hồi</button>
                                        </c:otherwise>
                                    </c:choose>

                                    <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                                    <BR>

                                </div>
                                <div class="modal-footer">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </c:forEach>
            <!--
            MODAL MESS
            -->
            <c:choose>
                <c:when test="${not empty mess}">
                    <div class="modal fade" id="exampleModal" tabindex="-1" 
                         aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <p style="font-weight: bold; font-size: 32px; line-height: 1.2; letter-spacing: 1px;">${mess}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>

            </c:choose>
            <!--
           MODAL FOR ĐANG LÀM DỊCH VỤ
            -->
            <!--            <style>
                            .border-box {
                                border: 1px solid #ccc; /* Set your desired border style, color, and width */
                                padding: 10px; /* Add padding to create some space between the content and the border */
                                border-radius: 5px; /* Optional: Add rounded corners */
                            }
                        </style>
            <c:choose>
                <c:when test="${not empty activeModalInvoice}">
                     Display the modal if modalValue has a value 
                    <div class="modal fade show" id="myModal" tabindex="-1" role="dialog" style="display: block;">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content custom-modal" style="width: 147%; /* You can adjust this value as needed */
                                 max-width: 800px; /* You can adjust this value as needed */">
                                <div class="modal-header">
                                    <h5 class="modal-title">Modal Title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()"><span aria-hidden="true">&times;</span></button>
                                </div>
                                <div class="modal-body">
                                     show thông tin của dịch vụ mà khách hàng đang làm đang làm dịch vụ 
                                    <div class="row" >
                                        <div class="form-group col-md-12 ">
                                            <h1>HÓA ĐƠN DỊCH VỤ</h1>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Trạng thái dịch vụ:</label>
                                            <label>${clientService.getStatus()}</label>
                                        </div>

                                        <div class="form-group col-md-12 border-box">
                                            <h4>THÔNG TIN KHÁCH HÀNG</h4>
                                            <table>
                                                <tr>
                                                    <td>
                                                        <label class="control-label">Tên khách hàng:</label>
                                                    </td>
                                                    <td>
                                                        <label>${clientService.getClientID().getClientName()}</label>
                                                    </td>
                                                    <td>
                                                        <label class="control-label">Dịch Vụ:</label>
                                                    </td>
                                                    <td>
                                                        <label>${clientService.getServiceID().getServiceType()}</label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label class="control-label">Số điện thoại:</label>
                                                    </td>
                                                    <td>
                                                        <label>${clientService.getClientID().getPhoneNumber()}</label>
                                                    </td>
                                                    <td>
                                                        <label class="control-label">Biển số xe:</label>
                                                    </td>
                                                    <td>
                                                        <label>${clientService.getNumberPlate()}</label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label class="control-label">Ngày Hẹn đến làm dịch vụ:</label>
                                                    </td>
                                                    <td>
                                                        <label>${clientService.getDateService()}</label>
                                                    </td>
                                                    <td>
                                                        <label class="control-label">Email:</label>
                                                    </td>
                                                    <td>
                                                        <label>${clientService.getClientID().getEmail()}</label>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                     form gửi 4 trường data để add vào clientService 2 trường là nhân viên làm xe cho khách và hóa đơn
                                    <form id="modalForm" method="POST" action="manageserviceclient">
                                         cái này giúp đẩy id của clientService xuống để đến khi update thì biết update thằng nào
                                        <input id="clientServiceID" class="form-control" type="hidden" value="${clientService.getClientServiceID()}" name="clientServiceID" />
                                        <div class="form-group">
                                            <label for="dropdown">Chọn nhân viên thợ máy:</label>
                                            <select class="form-control" id="listMechanicInfo">
                    <c:forEach items="${listMechanicInfo}" var="mechanic">
                         cái này đẩy id của 1 thằng employee (thợ máy xuống để biết thằng nào làm dịch vụ cho khách) 
                        <option value="${mechanic.getEmployeeID()}">${mechanic.getFirstName()} ${mechanic.getLastName()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-12">
                <h4>THÔNG TIN ĐƠN HÀNG</h4>
            </div>
            <p class="text-right"><label>Ngày chốt đơn:  ${currentDateTime}</label></p>
            -------------------------AJAX-------------------------

            <div class="form-group col-md-12 border-box row">
                <div class="col-md-12">
                    <label for="type" style="width: 100px;">Loại hàng:</label>
                    <select name="type" id="type" onchange="typeSelect()">
                        <option value="service">Service</option>
                        <option value="auto-part">Auto Part</option>
                        <option value="other">Other</option>
                    </select>
                </div>
                 For 'Other' option 
                <div class="col-md-12" id="productType" style="display: none;">
                    <label style="width: 100px;">Tên hàng:</label>
                    <input type="text" id="product" name="product" placeholder="Nhập tên hàng">
                </div>

                <div class="col-md-12" id="otherPrice" style="display: none;">
                    <label style="width: 100px;">Đơn giá:</label>
                    <input type="text" id="unitPrice" name="unitPrice" placeholder="Nhập đơn giá">
                </div>

                 For 'Service' option 
                <div class="col-md-12" id="serviceType" style="display: none;">
                    <label style="width: 100px;">Tên hàng:</label>
                    <select name="serviceSelected" id="serviceSelected" onchange="changeService(this)">
                    <c:forEach var="service" items="${serviceList}">
                        <option value="${service.getServiceID()}" data-service-price="${service.servicePrice}">${service.getServiceType()}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-12" id="servicePrice" style="display: none;">
                <label style="width: 100px;">Đơn giá:</label>
                <input type="text" id="serviceUnitPrice" name="unitPrice" value="" readonly>
            </div>

            <script>
                function changeService(selectElement) {
                    var selectedOption = selectElement.options[selectElement.selectedIndex];
                    var servicePrice = selectedOption.getAttribute('data-service-price');

                    // Set the value of the unitPrice input field with the extracted servicePrice
                    var unitPriceInput = document.querySelector('#serviceUnitPrice');
                    unitPriceInput.value = servicePrice;
                }
            </script>

             For 'Auto Part' option 
            <div class="col-md-12" id="autopart" style="display: none;">
                <label style="width: 100px;">Tên hàng:</label>
                <select name="autoPartSelected" id="autoPartSelected" onchange="changeAutoPart(this)">
                    <c:forEach var="autopart" items="${listAutoPart}">
                        <option value="${autopart.getAutoPartID()}" data-auto-part-price="${autopart.autoPartPrice}">${autopart.getPartName()}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-12" id="autoPartPrice" style="display: none;">
                <label style="width: 100px;">Đơn giá:</label>
                <input type="text" id="autoPartUnitPrice" name="unitPrice" value="" readonly>
            </div>

            <script>
                function changeAutoPart(selectElement) {
                    var selectedOption = selectElement.options[selectElement.selectedIndex];
                    var autoPartPrice = selectedOption.getAttribute('data-auto-part-price');

                    // Set the value of the unitPrice input field with the extracted autoPartPrice
                    var unitPriceInput = document.querySelector('#autoPartUnitPrice');
                    unitPriceInput.value = autoPartPrice;
                }
            </script>

            <script>
                function typeSelect() {
                    var typeSelect = document.querySelector('#type').value;

                    // Hide all elements first
                    document.querySelector('#productType').style.display = 'none';
                    document.querySelector('#otherPrice').style.display = 'none';
                    document.querySelector('#serviceType').style.display = 'none';
                    document.querySelector('#servicePrice').style.display = 'none';
                    document.querySelector('#autopart').style.display = 'none';
                    document.querySelector('#autoPartPrice').style.display = 'none';

                    // Show the elements based on the selected type
                    if (typeSelect === 'other') {
                        document.querySelector('#productType').style.display = 'block';
                        document.querySelector('#otherPrice').style.display = 'block';
                    } else if (typeSelect === 'service') {
                        document.querySelector('#serviceType').style.display = 'block';
                        document.querySelector('#servicePrice').style.display = 'block';
                    } else if (typeSelect === 'auto-part') {
                        document.querySelector('#autopart').style.display = 'block';
                        document.querySelector('#autoPartPrice').style.display = 'block';
                    }
                }
            </script>
            <div class="col-md-12">
                <label for="quantity" style="width: 100px;">Số lượng:</label>
                <input type="number" id="quantity" name="quantity" placeholder="Enter quantity" step="1">
            </div>

            <div class="col-md-12">
                <button onclick="addRow()">Add</button> 
            </div>

                                                        <script>
                                                            let productList = [];
            
                                                            function addRow() {
            
                                                                // Lấy thông tin từ form
                                                                var selectedType = $("#type").val();
                                                                var productName, unitPrice, serviceType, serviceUnitPrice, autoPartType, autoPartUnitPrice, quantity;
            
                                                                if (selectedType === "other") {
                                                                    productName = $("#product").val();
                                                                    unitPrice = parseFloat($("#unitPrice").val());
                                                                } else if (selectedType === "service") {
                                                                    productName = $("#serviceSelected").val();
                                                                    unitPrice = parseFloat($("#serviceUnitPrice").val());
                                                                } else if (selectedType === "auto-part") {
                                                                    productName = $("#autoPartSelected").val();
                                                                    unitPrice = parseFloat($("#autoPartUnitPrice").val());
                                                                }
                                                                quantity = parseInt($("#quantity").val());
            
            
                                                                // Tạo một đối tượng chứa thông tin mới
                                                                var InFoInvoiceDetail = {
                                                                    productName: productName,
                                                                    unitPrice: unitPrice,
                                                                    quantity: quantity
                                                                };
            
                                                                productList.push(InFoInvoiceDetail); // Add to productList
            
                                                                updateTable(); // Update the table
                                                            }
                                                            function updateTable() {
                                                                var table = document.getElementById("tableInvoice");
                                                                var currentTotal = 0; // Initialize total to 0
            
            
                                                                for (var i = 0; i < productList.length; i++) {
                                                                    var InFoInvoiceDetail = productList[i];
            
                                                                    var row = table.insertRow(-1);
                                                                    var cell1 = row.insertCell(0);
                                                                    var cell2 = row.insertCell(1);
                                                                    var cell3 = row.insertCell(2);
                                                                    var cell4 = row.insertCell(3);
                                                                    var cell5 = row.insertCell(4);
                                                                    var cell6 = row.insertCell(5);
            
                                                                    cell1.innerHTML = i + 1; // Row number
                                                                    cell2.innerHTML = InFoInvoiceDetail.productName;
                                                                    cell3.innerHTML = InFoInvoiceDetail.unitPrice;
                                                                    cell4.innerHTML = InFoInvoiceDetail.quantity;
                                                                    var totalAmount = InFoInvoiceDetail.unitPrice * InFoInvoiceDetail.quantity;
                                                                    cell5.innerHTML = totalAmount;
                                                                    cell6.innerHTML = '<a href="#" onclick="deleteRow(' + i + ')">Delete</a>';
            
                                                                    currentTotal += totalAmount;
                                                                }
            
                                                                document.getElementById("totalAmount").textContent = currentTotal.toFixed(2);
                                                            }
                                                            function deleteRow(index) {
                                                                productList.splice(index, 1); // Remove item from productList
                                                                updateTable(); // Update the table after removing a row
                                                            }
            
                                                        </script>
            <script>
                let productList = [];
                // Add an event listener to the form's submit event
                document.getElementById('myForm').addEventListener('submit', function (event) {
                    event.preventDefault(); // Prevent the default form submission
                    addRow();
                });
                function addRow() {


                    // Lấy thông tin từ form
                    var selectedType = $("#type").val();
                    var productName, unitPrice, serviceType, serviceUnitPrice, autoPartType, autoPartUnitPrice, quantity;

                    if (selectedType === "other") {
                        productName = $("#product").val();
                        unitPrice = parseFloat($("#unitPrice").val());
                    } else if (selectedType === "service") {
                        productName = $("#serviceSelected").val();
                        unitPrice = parseFloat($("#serviceUnitPrice").val());
                    } else if (selectedType === "auto-part") {
                        productName = $("#autoPartSelected").val();
                        unitPrice = parseFloat($("#autoPartUnitPrice").val());
                    }
                    quantity = parseInt($("#quantity").val());

                    if (productName && !isNaN(unitPrice) && !isNaN(quantity)) {
                        const newRow = document.getElementById("invoiceTable").insertRow(1);
                        const cell1 = newRow.insertCell(0);
                        const cell2 = newRow.insertCell(1);
                        const cell3 = newRow.insertCell(2);
                        const cell4 = newRow.insertCell(3);
                        const cell5 = newRow.insertCell(4);

                        cell1.innerHTML = productName;
                        cell2.innerHTML = unitPrice;
                        cell3.innerHTML = quantity;
                        const total = unitPrice * quantity;
                        cell4.innerHTML = total;
                        cell5.innerHTML = '<button onclick="deleteRow(this)">Delete</button>';

                        const detail = {productName, price, quantity, total};
                        productList.push(detail);

                        // Recalculate the total
                        calculateTotal();
                    }
                    function deleteRow(btn) {
                        const row = btn.parentNode.parentNode;
                        const index = row.rowIndex - 1;
                        productList.splice(index, 1);
                        document.getElementById("invoiceTable").deleteRow(row.rowIndex);
                        calculateTotal();
                    }

                    function calculateTotal() {
                        let total = 0;
                        for (const detail of productList) {
                            total += detail.total;
                        }
                        document.getElementById("totalAmount").textContent = total;
                    }
                }

            </script>

            <table id="invoiceTable" border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>
            </table>
            <p>Total: <span id="totalAmount">0</span></p>

        </div>

        -------------------------AJAX-------------------------
        cái này để gửi action
        <input type="hidden" name="action" value="CompleteServiceAppointment"/>
         cái này để biết là nhân viên đã điền invoice và hóa đơn sau đó chạy vào logic gửi mail thông báo khách hoàn thành dịch vụ 
        <input type="hidden" name="isCompleteInvoice" value="true" />
    </form>
</div>
<div class="modal-footer">
    <a href="manageserviceclient" class="btn btn-secondary" >Cancel</a>
    <a href="javascript:void(0);" class="btn btn-primary" onclick="submitFormInvoice()">Submit</a>
</div>
</div>
</div>
</div>
                </c:when>
                <c:otherwise>
                     No modal to display 
                </c:otherwise>
            </c:choose>
            <script>
                function submitFormInvoice() {
                    // Submit the form using JavaScript
                    document.getElementById("modalForm").submit();
                }
            </script>-->

            <!--
           MODAL
            -->
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
