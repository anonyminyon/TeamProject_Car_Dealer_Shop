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
        <link rel="stylesheet" href="./assets/Staff/css/Table.css">


        <!----===== font awesome CSS ===== -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" />

        <link rel="stylesheet" href="./assets/Staff/css/main.css">

        <!-- Main CSS-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <title>Fee List</title>

        <c:set var="acc" value="${sessionScope.acc}" />

    </head>

    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item"><a href="carlist">Danh sách sản phẩm</a></li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">

                                    <div class="col">
                                        <a class="btn btn-add btn-sm" title="Thêm" id="show-emp" data-toggle="modal"
                                           data-target="#ModalUPAddFee"><i class="fas fa-plus"></i>
                                            Tạo mới phụ phí</a>
                                    </div>
                                </div>

                                <!--                                    <div class="col text-right">
                                                                        <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i
                                                                                class="fas fa-trash-alt"></i> Xóa tất cả </a>
                                                                    </div>-->


                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-6">
                                        <div class="length-box" >
                                            <label>Hiện 
                                                <select id="quantitySelect" name="length" onchange="changeQuantity(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                    <option value="5" <c:if test="${quantity == 5}">selected</c:if>>5</option>
                                                    <option value="10" <c:if test="${quantity == 10}">selected</c:if>>10</option>
                                                    <option value="25" <c:if test="${quantity == 25}">selected</c:if>>25</option>
                                                    <option value="50" <c:if test="${quantity == 50}">selected</c:if>>50</option>
                                                    </select> danh mục
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-sm-12 col-md-6 text-lg-right">
                                            <form action="policyfeelist" method="get">
                                                <div class="search-box">
                                                    <label>
                                                        Tìm kiếm:<input type="search" value="${name}" name="name" class="form-control form-control-sm" placeholder="" aria-controls="sampleTable">
                                                </label>
                                                <input type="hidden" name="index" value="1" />
                                                <input type="hidden" name="quantity" value="${quantity}" />
                                                <button type="submit" type="button" class="btn btn-success" fdprocessedid="wjwv0s">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                                                    </svg>
                                                </button>                                            
                                            </div>
                                        </form>

                                    </div>
                                </div>


                                <table class="table table-hover table-bordered" id="sampleTable">
                                    <thead>
                                        <tr>
                                            <th width="10"><input type="checkbox" id="all"></th>
                                            <th>Mã phí</th>
                                            <th>Tên phí</th>
                                            <th>Phí (VNĐ)</th>
                                                <c:if test="${acc != null && acc.getRoleID().getRoleID() == 1}">
                                                <th>Người tạo</th>
                                                <th>Ngày tạo</th>
                                                <th>Người đổi</th>
                                                <th>Ngày đổi</th>
                                                </c:if>
                                            <th>Chức năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="b" items="${FeeList}">
                                            <tr>
                                                <td width="10">
                                                    <input type="checkbox" name="checkedServiceID" value="${service.getServiceID()}">
                                                </td>
                                                <td>${b.getFeeID()}</td>
                                                <td>${b.getFeeName()}</td>
                                                <td>${b.getFee()}</td>
                                                <c:if test="${acc != null && acc.getRoleID().getRoleID() == 1}">
                                                    <td>${b.getCreatedBy().getAccName()}</td>
                                                    <td>${b.getCreatedOn()}</td>
                                                    <td>${b.getModifiedBy().getAccName()}</td>
                                                    <td>${b.getModifiedOn()}</td>
                                                </c:if>
                                                <td style="text-align: center">
                                                    <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                            data-target="#ModalUP${b.getFeeID()}"><i class="fas fa-edit"></i></button>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>

                                <div class="row">
                                    <div class="col-sm-12 col-md-5">
                                        <div class="dataTables_info" role="status" >

                                            <c:choose>

                                                <c:when test = "${count>0}">
                                                    Hiện ${(index-1)*quantity+1} đến 
                                                    ${((index*(quantity+1))>count) ? count : index*quantity}
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

                                                <li class="paginate_button page-item previous <c:if test="${index == 1}">disabled</c:if>" id="sampleTable_previous">
                                                    <a href="policyfeelist?index=${index -1}&name=${name}&quantity=${quantity}"class="page-link">Lùi</a>
                                                </li>

                                                <c:forEach begin="1" end="${endPage}" var="i">

                                                    <li class="paginate_button page-item ${index == i?"active":""}">
                                                        <a href="policyfeelist?index=${i}&name=${name}&quantity=${quantity}"class="page-link">${i}</a>
                                                    </li>

                                                </c:forEach>

                                                <li class="paginate_button page-item next <c:if test="${index == endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="policyfeelist?index=${index +1}&name=${name}&quantity=${quantity}" class="page-link">Tiếp</a>
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
            <c:forEach items="${FeeList}" var="b">
                <form  action="policyfeelist" method="POST">
                    <div class="modal fade" id="ModalUP${b.getFeeID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                         data-keyboard="false">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group  col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5>Chỉnh sửa thông tin phụ phí</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <input type="hidden" name="feeID" value="${b.getFeeID()}" />
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Tên phí</label>
                                            <input class="form-control" name="feeName" type="text" required value="${b.getFeeName()}">
                                        </div>

                                        <div class="form-group col-md-12">
                                            <label class="control-label">Phí (VNĐ)</label>
                                            <input class="form-control" name="fee" type="number" required value="${b.getFee()}">
                                        </div>

                                    </div>
                                    <BR>
                                    <button class="btn btn-save" type="submit">Lưu lại</button>
                                    <!<!-- send input action to servlet to know what action need -->
                                    <input type="hidden" name="action" value="UpdateFee"/>
                                    <a class="btn btn-cancel" data-dismiss="modal" href="#" onclick="confirmCancel(event);">Hủy bỏ</a>
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
            MODAL
            -->

            <form  action="policyfeelist" method="POST">
                <div class="modal fade" id="ModalUPAddFee" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                     data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group  col-md-12">
                                        <span class="thong-tin-thanh-toan">
                                            <h5>Chỉnh sửa thông tin phụ phí</h5>
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label class="control-label">Tên phí</label>
                                        <input class="form-control" name="feeName" type="text" required value="">
                                    </div>

                                    <div class="form-group col-md-12">
                                        <label class="control-label">Phí (VNĐ)</label>
                                        <input class="form-control" name="fee" type="number" required value="${b.getFee()}">
                                    </div>

                                </div>
                                <BR>
                                <button class="btn btn-save" type="submit">Lưu lại</button>
                                <!<!-- send input action to servlet to know what action need -->
                                <input type="hidden" name="action" value="AddFee"/>
                                <a class="btn btn-cancel" data-dismiss="modal" href="#" onclick="confirmCancel(event);">Hủy bỏ</a>
                                <BR>
                            </div>
                            <div class="modal-footer">
                            </div>
                        </div>
                    </div>
                </div>
            </form>
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
        <!-- Data table plugin-->
        <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>

        <script>
                                    function confirmCancel(event) {
                                        var confirmation = confirm("Bạn có chắc chắn muốn huỷ thay đổi hãng xe?");
                                        if (!confirmation) {
                                            event.preventDefault(); // Ngăn chặn điều hướng đến đường dẫn URL
                                        }
        </script>

        <script>
                function changeQuantity(selectElement) {
                    var selectedValue = selectElement.value;
                    // Create a form element
                    var form = document.createElement('form');
                    form.method = 'get';
                    form.action = 'policyfeelist'; // Set the URL of your servlet

                    // Create an input element for the quantity
                    var quantityInput = document.createElement('input');
                    quantityInput.type = 'hidden';
                    quantityInput.name = 'quantity';
                    quantityInput.value = selectedValue;
                    // Create an input element for the name
                    var nameInput = document.createElement('input');
                    nameInput.type = 'hidden';
                    nameInput.name = 'name'; // Set the name to 'name'
                    nameInput.value = '${name}'; // Set the value to the desired name

                    // Append all input elements to the form
                    form.appendChild(quantityInput);
                    form.appendChild(nameInput);

                    // Append the form to the body
                    document.body.appendChild(form);
                    // Submit the form
                    form.submit();
                }
        </script>

    </body>

</html>