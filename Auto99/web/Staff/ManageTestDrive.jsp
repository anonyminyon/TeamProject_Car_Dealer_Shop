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
                        <li class="breadcrumb-item">Quản lý dịch vụ lái thử</li>
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
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-6">
                                        <form action="managetestdrive" method="get">
                                            <div class="search-box">
                                                <label>
                                                    Tìm kiếm:<input type="search" value="${search}" required = "required" name="search" class="form-control form-control-sm" placeholder="" aria-controls="sampleTable">
                                                </label>                              
                                                <button type="submit" class="btn btn-outline-success"><i class="fa-solid fa-search"></i></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <form id="SendAllServiceIDChecked" action="managetestdrive" method="POST">
                                    <table class="table table-hover table-bordered" id="sampleTable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center" >ID</th>
                                                <th style="text-align: center">ID Khách Hàng/Tên khách hàng</th>                   
                                                <th style="text-align: center">XE</th>
                                                <th style="text-align: center">Ngày Hẹn Sử Dụng Dịch Vụ</th>
                                                <th style="text-align: center">Trạng Thái Lịch Đặt</th>
                                                <th style="text-align: center">Chức năng</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="service" items="${TestDriveServiceList}" >
                                                <tr>
                                                    <td style="text-align: center">${service.getTestDriveServiceID()}</td>
                                                    <td style="text-align: left">${service.getClientID().getClientID()}/${service.getClientID().getClientName()}</td>
                                                    <td style="text-align: left">${service.getCarID().getCarID()}/${service.getCarID().getCarName()}</td> 
                                                    
                                                    <fmt:formatDate value="${service.getDateService()}" pattern="dd-MM-yyyy HH:mm:ss" var="formattedDate" />                                                                                  
                                                    <td style="text-align: center">${formattedDate}</td>
                                                    <td style="text-align: center">
                                                        <c:choose>
                                                            <c:when test="${service.getStatus() eq 'CHỜ DUYỆT ĐƠN'}">
                                                                <span class="badge bg-danger">${service.getStatus()}</span>
                                                            </c:when>
                                                            <c:when test="${service.getStatus() eq 'ĐANG LÀM DỊCH VỤ'}">
                                                                <span class="badge bg-warning">${service.getStatus()}</span>
                                                            </c:when>
                                                            <c:when test="${service.getStatus() eq 'DỊCH VỤ ĐÃ HOÀN THÀNH'}">
                                                                <span class="badge bg-success">${service.getStatus()}</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td style="text-align: center">
                                                        <button class="btn btn-primary btn-sm edit" type="button" title="Xem thông tin đơn" id="show-emp" data-toggle="modal"
                                                                data-target="#ModalUP${service.getTestDriveServiceID()}"><i class="fas fa-edit"></i></button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </form>                               
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--            
            MODAL FOR CHỜ DUYỆT ĐƠN
            -->     
            <c:forEach var="service" items="${TestDriveServiceList}">
                <form action="managetestdrive" method="POST" >
                    <div class="modal fade" id="ModalUP${service.getTestDriveServiceID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
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
                                            <input class="form-control" type="text" type="number" required value="${service.getClientID().getClientName()}" readonly>
                                        </div>
                                        
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Số điện thoại</label>
                                            <input class="form-control" type="text" required value="${service.getClientID().getPhoneNumber()}" readonly>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">xe</label>
                                            <input class="form-control" type="text" required value="${service.getCarID().getCarName()}" readonly>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Ngày Hẹn đến làm dịch vụ</label>

                                            <fmt:formatDate value="${service.getDateService()}" pattern="dd-MM-yyyy" var="formattedDate" />                                      
                                            <input class="form-control" type="text" required value=" ${formattedDate}" readonly>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Email</label>
                                            <input class="form-control" type="text" required value="${service.getClientID().getEmail()}" readonly>
                                        </div>
                                        <div class="form-group col-md-6 ">
                                            <label for="exampleSelect1" class="control-label">Trạng thái dịch vụ</label>
                                            <!-- Add an id to the <select> element for easy access -->
                                            <select id="TestdriveStatus" class="form-control" name="ClientServiceStatus" disabled>
                                                <option value="CHỜ DUYỆT ĐƠN" ${service.getStatus() eq 'CHỜ DUYỆT ĐƠN' ? 'selected' : ''}>CHỜ DUYỆT ĐƠN</option>
                                                <option value="ĐANG LÀM DỊCH VỤ" ${service.getStatus() eq 'ĐANG LÀM DỊCH VỤ' ? 'selected' : ''}>ĐANG LÀM DỊCH VỤ</option>
                                                <option value="DỊCH VỤ ĐÃ HOÀN THÀNH" ${service.getStatus() eq 'DỊCH VỤ ĐÃ HOÀN THÀNH' ? 'selected' : ''}>DỊCH VỤ ĐÃ HOÀN THÀNH</option>
                                            </select>
                                        </div> 
                                        <input id="TestdriveID" class="form-control" type="hidden" value="${service.getTestDriveServiceID()}" name="TestDriveServiceID" />
                                    </div>
                                    <BR>
                                    <BR>
                                    <BR>
                                    <!<!-- this function using to check status of a Testdrive then when click button Duyệt đơn it will send action different  -->
                                    <c:choose>
                                        <c:when test="${service.getStatus() eq 'CHỜ DUYỆT ĐƠN'}">
                                            <input type="hidden" name="action" value="SendMailConfirmAppointment" />
                                        </c:when>
                                        <c:when test="${service.getStatus() eq 'ĐANG LÀM DỊCH VỤ'}">
                                            <input type="hidden" name="action" value="CompleteServiceAppointment" />
                                            <input type="hidden" name="isCompleteInvoice" value="false" />
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Set a default action if neither condition is met -->
                                            <input type="hidden" name="action" value="SomeDefaultAction" />
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    <input type="hidden" name="search" value="${search}" />


                                    <c:choose>
                                        <c:when test="${service.getStatus() eq 'CHỜ DUYỆT ĐƠN'}">
                                            <button class="btn btn-save" type="submit">Duyệt đơn</button>
                                        </c:when>
                                        <c:when test="${service.getStatus() eq 'ĐANG LÀM DỊCH VỤ'}">
                                            <button class="btn btn-save" type="submit">Tạo hóa đơn</button>
                                        </c:when>
                                        <c:when test="${service.getStatus() eq 'DỊCH VỤ ĐÃ HOÀN THÀNH'}">
                                            <button class="btn btn-save" type="submit">Xem phản hồi</button>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Default button text if status doesn't match any case -->
                                            <button class="btn btn-save" type="submit">Duyệt đơn</button>
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
