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
        <title>Car List</title>
    </head>
    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item">Danh sách khách hàng</li>
                    </ul>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">
<!--                                    <div class="col ">
                                        <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i
                                                class="fas fa-trash-alt"></i> Xóa tất cả </a>
                                    </div>-->
                                    <div class="col text-right">
                                        <a class="btn btn-add btn-sm" href="clientlist" title="Hiển thị tất cả">
                                            Hiển thị tất cả</a>
                                    </div>
                                </div>
                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-6">
                                        <div class="length-box" >
                                            <form action="clientlist" id="submitForm">
                                                <label>Hiện 
                                                    <select name="numberClientDisplay" aria-controls="sampleTable" class="form-control form-control-sm" onchange="submitForm()">
                                                        <option ${numberClientDisplay == 10?"selected":""} value="10" >10</option>
                                                        <option ${numberClientDisplay == 25?"selected":""} value="25">25</option>
                                                        <option ${numberClientDisplay == 50?"selected":""} value="50">50</option>
                                                        <option ${numberClientDisplay == 100?"selected":""} value="100">100</option>
                                                    </select> danh mục
                                                </label>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-6">
                                        <form action="clientlist" id="formSearchClient">
                                            <div class="search-box">
                                                <label>Tìm kiếm:<input name="search" style="height: 40px; margin-right: 5px;" value="${search}" type="search" class="form-control form-control-sm" placeholder="Nhập thông tin tìm kiếm" aria-controls="sampleTable">
                                                    <button onclick="submitFormSearchClient()" type="button" class="btn btn-success" fdprocessedid="wjwv0s">
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
                                            <th>Giới tính</th>
                                            <th>Ngày sinh</th>
                                            <th >Email</th>
                                            <th>Số điện thoại</th>
                                            <th>Chức Năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="i" items="${listClient}" >
                                            <tr>
                                                <td>${i.clientID}</td>
                                                <td >${i.clientName}</td>
                                                <td >${i.gender==true?"Nam":"Nu"}</td>
                                                <td >${i.dateOfBrith}</td>
                                                <td >${i.email}</td>
                                                <td>${i.phoneNumber}</td>
                                                <td>
                                                    <button style="margin-left: 30px;" class="btn btn-primary show" type="button" title="show" id="show-emp" data-toggle="modal"
                                                            data-target="#ModalUP${i.clientID}"><i class="fas fa-eye"></i></button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <div class="row">
                                    <div class="col-sm-12 col-md-5">
                                        <div class="dataTables_info" role="status" >
                                            Hiện 1 đến 6 của 6 danh mục
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-7">
                                        <div class="dataTables_paginate">
                                            <ul class="pagination">
                                                <li class="paginate_button page-item previous <c:if test="${currentPage == 1}">disabled</c:if>" id="sampleTable_previous">
                                                    <a href="clientlist?page=${currentPage -1}&numberClientDisplay=${numberClientDisplay}&search=${search}" class="page-link">Lùi</a>
                                                </li>
                                                <c:forEach var="i" begin="1" end="${endPage}">
                                                    <li class="paginate_button page-item <c:if test="${page == i}">active</c:if>">
                                                        <a href="clientlist?page=${i}&numberClientDisplay=${numberClientDisplay}&search=${search}"class="page-link">${i}</a>
                                                    </li>
                                                </c:forEach>
                                                <li class="paginate_button page-item next <c:if test="${currentPage == endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="clientlist?page=${currentPage +1}&numberClientDisplay=${numberClientDisplay}&search=${search}" class="page-link">Tiếp</a>
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
      MODAL
            -->
            <c:forEach var="i" items="${listClient}" >
                <div class="modal fade" id="ModalUP${i.clientID}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                     data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group  col-md-12">
                                        <span class="thong-tin-thanh-toan">
                                            <h5>Thông tin khách hàng</h5>
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Tên khách hàng </label>
                                        <input readonly class="form-control" type="text" value="${i.clientName}">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Gioi tính</label>
                                        <input readonly class="form-control" type="text" value="${i.gender==true?"Nam":"Nu"}">
                                    </div>
                                    <div class="form-group  col-md-6">
                                        <label class="control-label">Ngày sinh</label>
                                        <input readonly class="form-control" type="text" value="${i.dateOfBrith}">
                                    </div>
                                    <div class="form-group col-md-6 ">
                                        <label class="control-label">email</label>
                                        <input readonly class="form-control" type="text" value="${i.email}">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Số diện thoại</label>
                                        <input class="form-control" type="text" value="${i.phoneNumber}">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Số CCCD/CMND</label>
                                        <input class="form-control" type="text" value="${i.noID}">
                                    </div>
                                    <!--                                <div class="form-group col-md-6">
                                                                        <label for="exampleSelect1" class="control-label">Danh mục</label>
                                                                        <select class="form-control" id="exampleSelect1">
                                                                            <option>Bàn ăn</option>
                                                                            <option>Bàn thông minh</option>
                                                                            <option>Tủ</option>
                                                                            <option>Ghế gỗ</option>
                                                                            <option>Ghế sắt</option>
                                                                            <option>Giường người lớn</option>
                                                                            <option>Giường trẻ em</option>
                                                                            <option>Bàn trang điểm</option>
                                                                            <option>Giá đỡ</option>
                                                                        </select>
                                                                    </div>-->
                                </div>
                                <BR>
                                <!--<button class="btn btn-save" type="button">Lưu lại</button>-->
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

            <!-- Essential javascripts for application to work-->
            <script>
                function submitForm() {
                    document.getElementById("submitForm").submit();
                }
                function submitFormSearchClient() {
                    document.getElementById("formSearchClient").submit();
                }
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