<%-- 
    Document   : ServiceManagement
    Created on : Sep 23, 2023, 9:55:50 PM
    Author     : hieuHT
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="dal.EmployeeProfileDAO" %>
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
        <title>Service List</title>
    </head>
    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item">Danh sách dịch vụ</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">
                                    <div class="col">
                                        <c:url var="addService" value="servicecrud" >
                                            <c:param name="index" value="${index}" />
                                            <c:param name="search" value="${search}" />
                                            <c:param name="pageSize" value="${pageSize}" />
                                            <c:param name="action" value="addService" />
                                        </c:url>
                                        <a class="btn btn-add btn-sm" href="${addService}" title="Thêm"><i class="fas fa-plus"></i>
                                            Tạo mới dịch vụ</a>
                                        <a class="btn btn-add btn-sm" href="manageserviceclient" title="Quản lý đơn dịch vụ"><i class="fas fa-cog"></i>
                                            Quản lý dịch vụ</a>
                                        <a class="btn btn-add btn-sm" href="managetestdrive" title="Quản lý đơn lái thử"><i class="fas fa-cog"></i>
                                            Quản lý đơn lái thử xe</a>

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
                                                            <option value="true" ${serviceStatusFilter == 'true'?'selected':''}>Hoạt động</option>
                                                        <option value="false" ${serviceStatusFilter == 'false'?'selected':''}>Ngừng hoạt động</option>
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
                                                    form.action = 'servicemanagement'; // Set the URL of your servlet

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
                                        <form action="servicemanagement" method="get">
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
                                <form id="SendAllServiceIDChecked" action="servicecrud" method="POST">
                                    <table class="table table-hover table-bordered" id="sampleTable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center" >ID Service</th>
                                                <th style="text-align: left">Tên Dịch Vụ</th>
                                                <th style="text-align: left">Nội Dung Dịch Vụ</th>
                                                <th style="text-align: left">Trạng Thái Dịch Vụ</th>
                                                <th style="text-align: center">Chức Năng</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="service" items="${serviceList}" >
                                                <tr>
                                                    <td style="text-align: center">${service.getServiceID()}</td>
                                                    <td style="text-align: left">${service.getServiceType()}</td> 
                                                    <td style="text-align: left">${service.getServiceContent()}</td>
                                                    <td style="text-align: left">
                                                        <span class="badge
                                                              <c:choose>
                                                                  <c:when test = "${service.isServiceStatus() == true}">
                                                                      bg-success
                                                                  </c:when>
                                                                  <c:otherwise>
                                                                      bg-danger
                                                                  </c:otherwise>
                                                              </c:choose>
                                                              ">${service.isServiceStatus() ? "Hoạt động" : "Ngừng hoạt động"}
                                                        </span>
                                                    </td>

                                                    <td style="text-align: center">
                                                        <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                                data-target="#ModalUP${service.getServiceID()}"><i class="fas fa-edit"></i></button>
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
                                                    <a href="servicemanagement?index=${index -1}&search=${search}&pageSize=${pageSize}&serviceStatusFilter=${serviceStatusFilter}" class="page-link">Lùi</a>
                                                </li>

                                                <c:forEach begin="1" end="${endPage}" var="i">

                                                    <li class="paginate_button page-item ${index == i?" active ":" "}">
                                                        <a href="servicemanagement?index=${i}&search=${search}&pageSize=${pageSize}&serviceStatusFilter=${serviceStatusFilter}" class="page-link">${i}</a>
                                                    </li>

                                                </c:forEach>

                                                <li class="paginate_button page-item next <c:if test="${index==endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="servicemanagement?index=${index +1}&search=${search}&pageSize=${pageSize}&serviceStatusFilter=${serviceStatusFilter}" class="page-link">Tiếp</a>
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
            <c:forEach var="service" items="${serviceList}">
                <form action="servicecrud" method="POST" >
                    <div class="modal fade" id="ModalUP${service.getServiceID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                         data-keyboard="false">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group  col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5>Xem thông tin cơ bản của dịch vụ</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Mã dịch vụ</label>
                                            <!--đẩy cái serviceID xuông để update trạng thái bằng id-->
                                            <input class="form-control" name="serviceID" type="number" value="${service.getServiceID()}" readonly>
                                        </div>
                                        <div class="form-group col-md-6 ">
                                            <label for="exampleSelect1" class="control-label">Trạng thái dịch vụ</label>
                                            <select class="form-control" name="serviceStatus">
                                                <option value="${service.isServiceStatus()}">${service.isServiceStatus() ? "Hoạt động" : "Ngưng hoạt động"}</option>
                                                <option value="${!service.isServiceStatus()}">${!service.isServiceStatus() ? "Hoạt động" : "Ngưng hoạt động"}</option>
                                            </select>
                                        </div>                            

                                        <div class="form-group  col-md-6">
                                            <label class="control-label">ID người tạo dịch vụ</label>
                                            <input class="form-control" type="text" required value="${service.getCreated_by()}" placeholder="" readonly>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">ID người sửa gần nhất</label>
                                            <input class="form-control" type="text" required value="${service.getModified_by()}" placeholder="" readonly>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Ngày tạo dịch vụ</label>
                                            <fmt:formatDate value="${service.getCreated_on()}" pattern="dd-MM-yyyy" var="formattedCreatedOn" />
                                            <input class="form-control" type="text" required value="${formattedCreatedOn}" readonly>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Ngày sửa dịch vụ gần nhất</label>
                                            <fmt:formatDate value="${service.getModified_on()}" pattern="dd-MM-yyyy" var="formattedModifiedOn" />
                                            <input class="form-control" type="text" required value="${formattedModifiedOn}" readonly>
                                        </div>



                                    </div>
                                    <BR>

                                    <c:url var="serviceCRUDUrl" value="servicecrud" >
                                        <c:param name="index" value="${index}" />
                                        <c:param name="search" value="${search}" />
                                        <c:param name="pageSize" value="${pageSize}" />
                                        <c:param name="serviceStatusFilter" value="${serviceStatusFilter}" />
                                        <c:param name="serviceID" value="${service.getServiceID()}" />
                                        <c:param name="action" value="updateServiceAdvanced" />
                                    </c:url>
                                    <a href="${serviceCRUDUrl}"
                                       style="float: right; font-weight: 600; color: #ea0000;">Chỉnh sửa dịch vụ chi tiết
                                    </a>


                                    <BR>
                                    <BR>
                                    <button class="btn btn-save" type="submit">Lưu lại</button>

                                    <!-- gửi hành động cho serviceCrUD -->
                                    <input type="hidden" name="action" value="UpdateStatusAService"/>
                                    <!--gửi cả dữ liệu cho paging-->
                                    <input type="hidden" name="index" value="${index}"/>
                                    <input type="hidden" name="pageSize" value="${pageSize}"/>
                                    <input type="hidden" name="search" value="${search}"/>

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
            MODAL
            -->

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
            <script>
                                                                    function sendServiceID() {
                                                                        // Check if at least one checkbox is checked
                                                                        var checkboxes = document.querySelectorAll('input[name="checkedServiceID"]:checked');

                                                                        if (checkboxes.length > 0) {
                                                                            // Create a hidden input element to add an attribute
                                                                            var hiddenInput = document.createElement("input");
                                                                            hiddenInput.type = "hidden";
                                                                            hiddenInput.name = "action"; // Change to the desired attribute name
                                                                            hiddenInput.value = "deleteAllByServiceID"; // Change to the desired attribute value

                                                                            // Append the hidden input element to the form
                                                                            var form = document.getElementById('SendAllServiceIDChecked');
                                                                            form.appendChild(hiddenInput);

                                                                            // Submit the form
                                                                            form.submit();
                                                                        } else {
                                                                            // No checkboxes are checked, so prevent the button from doing anything
                                                                            alert("Please select at least one row to delete.");
                                                                        }
                                                                    }

            </script>

            <script>
                function deleteRow(r) {
                    var i = r.parentNode.parentNode.rowIndex;
                    document.getElementById("myTable").deleteRow(i);
                }
                jQuery(function () {
                    jQuery(".trash").click(function () {
                        swal({
                            title: "Cảnh báo",
                            text: "Bạn có chắc chắn là muốn xóa sản phẩm này?",
                            buttons: ["Hủy bỏ", "Đồng ý"],
                        })
                                .then((willDelete) => {
                                    if (willDelete) {
                                        swal("Đã xóa thành công.!", {

                                        });
                                    }
                                });
                    });
                });
            </script>
    </body>

</html>