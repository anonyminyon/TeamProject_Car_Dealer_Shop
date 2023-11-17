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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <title>Manager Employee Page</title>
    </head>
    <body>
        <div class="wrapper">
            <!--    Navbar-->
            <%@ include file="./NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item">Danh sách nhân viên</li>
                    </ul>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">
                                    <div class="col text-left">
                                        <button style="margin-bottom: 15px;" class="btn btn-primary show" type="button" title="show" id="show-emp" data-toggle="modal"
                                                data-target="#ModalADD">Thêm nhân viên</button> 
                                        <br>

                                        <form action="employeelist" method="get" id="filterForm">
                                            <input type="hidden" name="action" value="filter">
                                            <label for="filterName">Filter by:</label>
                                            <select name="filterName" id="filterName" style="border: black solid 2px;" onchange="submitFilterForm()">
                                                <option  disabled selected>Gioi tính</option>
                                                <option value="1">Nam</option>
                                                <option value="0">Nu</option>
                                            </select>
                                        </form>
                                    </div>
                                    <div class="col text-right">
                                        <a class="btn btn-add btn-sm" href="employeelist" title="Hiển thị tất cả">
                                            Hiển thị tất cả</a>
                                    </div>
                                </div>
                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-6">
                                        <div class="length-box" >
                                            <form action="employeelist" id="submitForm" method="get">
                                                <label>Hiện 
                                                    <select name="numberEmployeeDisplay" aria-controls="sampleTable" class="form-control form-control-sm" onchange="submitForm()">
                                                        <option ${numberEmployeeDisplay == 10?"selected":""} value="10" >10</option>
                                                        <option ${numberEmployeeDisplay == 25?"selected":""} value="25">25</option>
                                                        <option ${numberEmployeeDisplay == 50?"selected":""} value="50">50</option>
                                                        <option ${numberEmployeeDisplay == 100?"selected":""} value="100">100</option>
                                                    </select> danh mục
                                                </label>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-6">
                                        <form action="employeelist" id="formSearchClient">
                                            <input type="hidden" name="action" value="search">
                                            <div class="search-box">
                                                <label>Tìm kiếm:<input name="search" style="height: 40px; margin-right: 5px;" value="${search}" type="search" class="form-control form-control-sm" placeholder="Nhập thông tin tìm kiếm" aria-controls="sampleTable">
                                                    <button onclick="submitFormSearchEmployee()" type="button" class="btn btn-success" fdprocessedid="wjwv0s">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                                                        </svg>
                                                    </button></label>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <table class="table table-hover table-bordered" id="sampleTable">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th >Họ và tên</th>
                                            <th>Anh dai diên</th>
                                            <th>Giới tính</th>
                                            <th>Ngày sinh</th>
                                            <th >Email</th>
                                            <th>Chức Năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="i" items="${listEmployee}" >
                                            <tr>
                                                <td>${i.employeeID}</td>
                                                <td >${i.firstName} ${i.lastName}</td>
                                                <td><img src="./img/EmployeeIMG/${i.employeeID}/${i.img}" width="100" height="100" alt="avarta"/></td>
                                                <td >${i.gender==true?"Nam":"Nu"}</td>
                                                <td ><fmt:formatDate value="${i.DOB}" pattern="dd/MM/yyyy" /></td>
                                                <td >${i.email}</td>
                                                <td>
                                                    <button style="margin-left: 30px;" class="btn btn-primary show" type="button" title="show" id="show-emp" data-toggle="modal"
                                                            data-target="#ModalUP${i.employeeID}"><i class="fas fa-eye"></i></button>
                                                    <button style="margin-left: 30px;" class="btn btn-primary show" type="button" title="show" id="show-emp" data-toggle="modal"
                                                            data-target="#ModalEDIT${i.employeeID}"><i class="fas fa-edit"></i></button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <div class="row">
                                    <div class="col-sm-12 col-md-7">
                                        <div class="dataTables_paginate">
                                            <ul class="pagination">
                                                <li class="paginate_button page-item previous <c:if test="${currentPage == 1}">disabled</c:if>" id="sampleTable_previous">
                                                    <a href="employeelist?page=${currentPage -1}&numberEmployeeDisplay=${numberEmployeeDisplay}&search=${search}" class="page-link">Lùi</a>
                                                </li>
                                                <c:forEach var="i" begin="1" end="${endPage}">
                                                    <li class="paginate_button page-item <c:if test="${page == i}">active</c:if>">
                                                        <a href="employeelist?page=${i}&numberEmployeeDisplay=${numberEmployeeDisplay}&search=${search}"class="page-link">${i}</a>
                                                    </li>
                                                </c:forEach>
                                                <li class="paginate_button page-item next <c:if test="${currentPage == endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="employeelist?page=${currentPage +1}&numberEmployeeDisplay=${numberEmployeeDisplay}&search=${search}" class="page-link">Tiếp</a>
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
      MODAL UP
            -->
            <c:forEach var="i" items="${listEmployee}" >
                <div class="modal fade" id="ModalUP${i.employeeID}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                     data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group  col-md-12">
                                        <span class="thong-tin-thanh-toan">
                                            <h5>Thông tin nhân viên</h5>
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label class="control-label">Ảnh đại diện:</label>
                                        <img src="./img/EmployeeIMG/${i.employeeID}/${i.img}" width="100" height="100" alt="avarta" style="margin-left: 100px;"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label class="control-label">Chức vụ:</label>
                                        <input readonly class="form-control" type="text" value="${i.role.roleName}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Họ:</label>
                                        <input readonly class="form-control" type="text" value="${i.firstName}">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Tên:</label>
                                        <input readonly class="form-control" type="text" value=" ${i.lastName}">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Giới tính:</label>
                                        <input readonly class="form-control" type="text" value="${i.gender==true?"Nam":"Nu"}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group  col-md-6">
                                        <label class="control-label">Ngày sinh:</label>
                                        <input readonly class="form-control" type="text" value="${i.DOB}">
                                    </div>
                                    <div class="form-group col-md-6 ">
                                        <label class="control-label">Email:</label>
                                        <input readonly class="form-control" type="text" value="${i.email}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Số điện thoại:</label>
                                        <input readonly class="form-control" type="text" value="${i.phoneNumber}">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">CMND:</label>
                                        <input readonly class="form-control" type="text" value="${i.IDNo}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Ðia chỉ:</label>
                                        <input readonly class="form-control" type="text" value="${i.address}">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Ngày bắt đầu làm:</label>
                                        <input readonly class="form-control" type="text" value="${i.startDate}">
                                    </div>
                                </div>
                                <BR>
                                <a class="btn btn-cancel" data-dismiss="modal">Ðóng</a>
                                <BR>
                            </div>
                            <div class="modal-footer">
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!--
            MODAL
            -->
            <!--
            MODAL EDIT
            -->
            <c:forEach var="i" items="${listEmployee}" >
                <div class="modal fade" id="ModalEDIT${i.employeeID}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                     data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group  col-md-12">
                                        <span class="thong-tin-thanh-toan">
                                            <h5>Chỉnh sửa thông tin nhân viên</h5>
                                        </span>
                                    </div>
                                </div>
                                <form action="employeelist" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="employeeID" value="${i.employeeID}">
                                    <input type="hidden" name="img1" value="${i.img}">
                                    <div class="row">
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Ảnh đại diện:</label>
                                            <input  class="form-control" type="file" name="img" accept=".png, .jpg">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Chức vụ:</label>
                                            <select class="form-control" name="roleId">
                                                <c:forEach var="a" items="${listRole}">
                                                    <option ${i.role.roleName == a.roleName?"selected":""} value="${a.roleID}">${a.roleName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-3">
                                            <label class="control-label">Họ:</label>
                                            <input required class="form-control" type="text" name="firstName" value="${i.firstName}">
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label class="control-label">Tên:</label>
                                            <input required class="form-control" type="text" name="lastName" value=" ${i.lastName}">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Giới tính:</label>
                                            <br>
                                            <input  type="radio" name="gender" ${i.gender==true?"checked":""} value="1"> Nam
                                            <input  type="radio" name="gender" ${i.gender==false?"checked":""} value="0"> Nu
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Ngày sinh:</label>
                                            <input required class="form-control" type="date" name="DOB" value="${i.DOB}">
                                        </div>
                                        <div class="form-group col-md-6 ">
                                            <label class="control-label">Email:</label>
                                            <input required class="form-control" type="text" name="email" value="${i.email}">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Số điện thoại:</label>
                                            <input required class="form-control" type="text" name="phoneNumber" value="${i.phoneNumber}">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">CMND:</label>
                                            <input required class="form-control" type="text" name="IDNo" value="${i.IDNo}">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Ðia chỉ:</label>
                                            <input required class="form-control" type="text" name="address" value="${i.address}">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Ngày bắt đầu làm:</label>
                                            <input required class="form-control" type="date" name="startDate" value="${i.startDate}">
                                        </div>
                                    </div>
                                    <BR>
                                    <input class="btn btn-save" type="submit" value="Lưu lại">
                                    <a class="btn btn-cancel" data-dismiss="modal">Ðóng</a>
                                    <BR>
                                </form>
                            </div>
                            <div class="modal-footer">
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!--
            MODAL ADD EMPLOYEE
            -->
            <!--
            MODAL ADD EMPLOYEE
            -->
            <div class="modal fade" id="ModalADD" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                 data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div class="row">
                                <div class="form-group  col-md-12">
                                    <span class="thong-tin-thanh-toan">
                                        <h5>Thêm nhân viên</h5>
                                    </span>
                                </div>
                            </div>
                            <form action="employeelist" method="post"  enctype="multipart/form-data">
                                <input type="hidden" name="action" value="add">
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Anh dai diên</label>
                                        <input required class="form-control" type="file" name="img" accept=".png, .jpg">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Chức vụ:</label>
                                        <select class="form-control" name="roleId">
                                                <c:forEach var="a" items="${listRole}">
                                                    <option value="${a.roleID}">${a.roleName}</option>
                                                </c:forEach>
                                            </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Tên tài khoản:</label>
                                        <input required class="form-control" type="text" name="accountName">
                                        <label class="control-label">Email:</label>
                                        <input required class="form-control" type="text" name="email">
                                        <label class="control-label">IDNo:</label>
                                        <input required class="form-control" type="text" name="IDNo" placeholder="Nhap CMND">
                                        <label class="control-label">Ngày sinh</label>
                                        <input required class="form-control" type="date" name="DOB" max="2023-10-14">

                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Ho:</label>
                                        <input required class="form-control" type="text" name="firstName">
                                        
                                        <label class="control-label">Tên:</label>
                                        <input required class="form-control" type="text" name="lastName">
                                        
                                        <label class="control-label">So đien thoai:</label>
                                        <input required="Ban chua nhâp sô diên thoai" class="form-control" type="text" name="phoneNumber" 
                                               pattern="0[0-9]{9}" title="Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số" placeholder="Vui lòng nhap sô diên thoai">
                                        
                                        <label class="control-label">Ðia chi:</label>
                                        <input required class="form-control" type="text" name="address" placeholder="Nhap dia chi">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Ngày bat dau làm:</label>
                                        <input required="Ban chua nhap ngày sinh" class="form-control" type="date" name="startDate">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Gioi tính</label>
                                            <br>
                                            <input checked type="radio" name="gender" value="1"> Nam
                                            <input style="margin-left: 50px; " type="radio" name="gender" value="0"> Nu
                                        </div>
                                    </div>
                                </div>
                                <BR>
                                <input class="btn btn-save" type="submit" value="Lưu lại">
                                <a class="btn btn-cancel" data-dismiss="modal">Ðóng</a>
                                <BR>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- Essential javascripts for application to work-->
                <script>
                    function submitFilterForm() {
                        document.getElementById("filterForm").submit();
                    }
                    function submitForm() {
                        document.getElementById("submitForm").submit();
                    }
                    function submitFormSearchEmployee() {
                        document.getElementById("formSearchClient").submit();
                    }
//                    
                </script>
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
                <!-- Data table plugin-->
                <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
                <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
                </body>

                </html>