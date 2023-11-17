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
        <script src="https://cdn.ckeditor.com/ckeditor5/39.0.2/classic/ckeditor.js"></script>
        <!-- Main CSS-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

        <!--Flatpickr-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

        <title>Danh sách đặt cọc xe</title>
    </head>
    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>
            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item">Danh sách đặt cọc</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">
                                </div>

                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-8">
                                        <div class="length-box" style="display: flex; justify-content: space-between; flex-wrap: wrap;">
                                            <label>Hiện 
                                                <select id="quantitySelect" name="length" onchange="changeQuantity(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                    <option value="10" <c:if test="${quantity == 10}">selected</c:if>>10</option>
                                                    <option value="25" <c:if test="${quantity == 25}">selected</c:if>>25</option>
                                                    <option value="50" <c:if test="${quantity == 50}">selected</c:if>>50</option>
                                                    <option value="100" <c:if test="${quantity == 100}">selected</c:if>>100</option>
                                                    </select> danh mục

                                                </label>
                                                <label> Thanh toán
                                                    <select id="quantitySelect" name="length" style="width: 150px" onchange="changePaymentType(this)" class="form-control form-control-sm">
                                                        <option value="">Chọn</option>
                                                        <option value="true" ${paymentType == 'true' ? 'selected' : ''}>VPAY</option>
                                                    <option value="false" ${paymentType == 'false' ? 'selected' : ''}>Chuyển khoản</option>
                                                </select> 
                                            </label>
                                            <label> Trạng thái
                                                <select id="quantitySelect" style="width: 150px" name="length" onchange="changeStatus(this)" class="form-control form-control-sm">
                                                    <option value="">Chọn</option>
                                                    <option value="2" ${status == '2' ? 'selected' : ''}>Bị hủy</option>
                                                    <option value="1" ${status == '1' ? 'selected' : ''}>Đã đặt cọc</option>
                                                    <option value="0" ${status == '0' ? 'selected' : ''}>Chờ xét duyệt</option>
                                                </select> 
                                            </label>
                                            <label> Từ
                                                <input style="width: 120px; text-align: center" onchange="startDate(this)" class="form-control form-control-sm flatpickr" data-input type="text" name="startDate" value="${startDate}">
                                                đến
                                                <input style="width: 120px; text-align: center" onchange="endDate(this)" class="form-control form-control-sm flatpickr" data-input type="text" name="endDate" value="${endDate}">
                                            </label>                                           
                                            <script>
                                                const flatpickrInputs = document.querySelectorAll(".flatpickr");


                                                flatpickrInputs.forEach((input) => {
                                                    flatpickr(input, {
                                                        enableTime: false,
                                                        dateFormat: "d-m-Y"
                                                    });
                                                });
                                            </script>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-4">
                                        <form action="carorderlist" method="post">
                                            <div class="search-box">
                                                <label>
                                                    Tìm kiếm:<input type="search" value="${search}" name="search" class="form-control form-control-sm" placeholder="" aria-controls="sampleTable">
                                                </label>
                                                <input type="hidden" name="index" value="1" />
                                                <input type="hidden" name="status" value="${status}"/>
                                                <input type="hidden" name="quantity" value="${quantity}" />
                                                <input type="hidden" name="paymentType" value="${paymentType}" />
                                                <input type="hidden" name="startDate" value="${startDate}" />
                                                <input type="hidden" name="endDate" value="${endDate}" />
                                                <button type="submit" type="button" class="btn btn-success" fdprocessedid="wjwv0s">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                                                    </svg>
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <form id="SendAllBlogIDChecked" action="carordercrud" method="POST">
                                    <table class="table table-hover table-bordered" id="sampleTable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center">ID</th>
                                                <th style="text-align: center">Mã đơn hàng</th>
                                                <th style="text-align: center">Tên khách hàng</th>
                                                <th style="text-align: center">Tên xe</th>
                                                <th style="text-align: center">Phương thức thanh toán</th>
                                                <th style="text-align: center">Số tiền phải trả</th>
                                                <th style="text-align: center">Số tiền đã trả</th>
                                                <th style="text-align: center">Số tiền còn thiếu</th>
                                                <th style="text-align: center">Ngày phải nôp</th>
                                                <th style="text-align: center">Mã voucher</th>
                                                <th style="text-align: center">Trạng thái</th>
                                                <th style="text-align: center">Chức Năng</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="i" items="${ListCarOrder}" varStatus="loop">
                                                <tr>

                                                    <td style="width: 20px; text-align: center">${i.carorderID}</td>
                                                    <td style="width: 100px;">${i.carorderCode}</td>
                                                    <td style="width: 150px;">${i.clientID.clientName}</td>
                                                    <td style="width: 150px;">${i.carID.carName}</td>
                                                    <td style="text-align: center;">${i.paymentType ? "VNPAY" : "Chuyển khoản"}</td>
                                                    <td style="width: 200px;text-align: center"><fmt:formatNumber value="${i.orderValue}" type="number" pattern="#,###" /> VNĐ</td>
                                                    <td style="width: 150px;text-align: center"><fmt:formatNumber value="${i.paid}" type="number" pattern="#,###" /> VNĐ</td>
                                                    <td style="width: 170px;text-align: center"><fmt:formatNumber value="${i.paymentRequired}" type="number" pattern="#,###" /> VNĐ</td>
                                                    <td style="text-align: center">
                                                        <fmt:parseDate var="parsedDate" value="${i.orderDate}" pattern="yyyy-MM-dd'T'HH:mm" />
                                                        <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy HH:mm a" />
                                                        ${formattedDate}                                                        
                                                    </td>
                                                    <td style="width: 100px;">${i.voucherID.voucherCode}</td>
                                                    <td style="text-align: center;">
                                                        <span class="badge
                                                              <c:choose>
                                                                  <c:when test = "${i.status == 0}">
                                                                      bg-warning
                                                                  </c:when>
                                                                  <c:when test = "${i.status == 1}">
                                                                      bg-success
                                                                  </c:when>
                                                                  <c:when test = "${i.status == 2}">
                                                                      bg-secondary
                                                                  </c:when>
                                                              </c:choose>
                                                              "><c:choose>
                                                                <c:when test = "${i.status == 0}">
                                                                    Chờ xét duyệt
                                                                </c:when>
                                                                <c:when test = "${i.status == 1}">
                                                                    Đã đặt cọc
                                                                </c:when>
                                                                <c:when test = "${i.status == 2}">
                                                                    Bị hủy
                                                                </c:when>
                                                            </c:choose>
                                                        </span>
                                                    </td>
                                                    <td style="text-align: center">
                                                        <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                                data-target="#ModalUP${i.carorderID}"><i class="fas fa-edit"></i></button>

                                                    </td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </form>

                                <div class="row">
                                    <div class="col-sm-12 col-md-5">
                                        <div class="dataTables_info" role="status" >
                                        </div>
                                    </div>
                                    <div class="col-sm-12 col-md-7">
                                        <div class="dataTables_paginate">
                                            <ul class="pagination">

                                                <li class="paginate_button page-item previous <c:if test="${currentIndex==1}">disabled</c:if>" id="sampleTable_previous">
                                                    <a href="carorderlist?index=${currentIndex -1}&search=${search}&quantity=${quantity}&status=${status}&paymentType=${paymentType}&startDate=${startDate}&endDate=${endDate}" class="page-link">Lùi</a>
                                                </li>

                                                <c:forEach begin="1" end="${endPage}" var="i">

                                                    <li class="paginate_button page-item ${index == i?" active ":" "}">
                                                        <a href="carorderlist?index=${i}&search=${search}&quantity=${quantity}&status=${status}&paymentType=${paymentType}&startDate=${startDate}&endDate=${endDate}" class="page-link">${i}</a>
                                                    </li>

                                                </c:forEach>

                                                <li class="paginate_button page-item next <c:if test="${currentIndex==endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="carorderlist?index=${currentIndex +1}&search=${search}&quantity=${quantity}&status=${status}&paymentType=${paymentType}&startDate=${startDate}&endDate=${endDate}" class="page-link">Tiếp</a>
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

            <c:forEach items="${ListCarOrder}" var="i">
                <form  action="carordercrud" method="POST">
                    <div class="modal fade" id="ModalUP${i.carorderID}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                         data-keyboard="false">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group  col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5>Chỉnh sửa thông tin car order</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <input type="hidden" name="carorderID" value="${i.carorderID}" />
                                        <input type="hidden" name="paymentType" value="${i.paymentType}" />
                                        <input type="hidden" name="orderValue" value="${i.orderValue}" />
                                        <input type="hidden" name="clientName" value="${i.clientID.clientName}" />
                                        <input type="hidden" name="clientID" value="${i.clientID.clientID}" />
                                        <input type="hidden" name="carName" value="${i.clientID.clientName}" />
                                        <input type="hidden" name="voucherCode" value="${i.voucherID.voucherCode}" />
                                        <input type="hidden" name="carorderCode" value="${i.carorderCode}" />
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Mã đơn hàng</label>
                                            <input class="form-control" type="text" required value="${i.carorderCode}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group col-md-6 ">
                                            <label for="exampleSelect1" class="control-label">Trạng thái</label>
                                            <select class="form-control" name="status">
                                                <c:choose>
                                                    <c:when test="${i.status != '2' && i.status != '1'}">
                                                        <option value="0" ${i.status == '0' ? 'selected' : ''}>Chờ xét duyệt</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${i.status != '2'}">
                                                        <option value="1" ${i.status == '1' ? 'selected' : ''}>Đã đặt cọc</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${i.status != '1'}">
                                                        <option value="2" ${i.status == '2' ? 'selected' : ''}>Bị hủy</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                    </c:otherwise>
                                                </c:choose>

                                            </select>
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Ngày đăng</label>
                                            <fmt:parseDate var="parsedDate" value="${i.createdOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                                            <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy hh:mm a" />
                                            <input class="form-control" type="text" required value="${formattedDate}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Ngày sửa gần nhất</label>
                                            <fmt:parseDate var="parsedDate" value="${i.modifiedOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                                            <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy hh:mm a" />
                                            <input class="form-control" type="text" required value="${formattedDate}" readonly style="cursor: not-allowed">
                                        </div> 
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">ID người sửa gần nhất</label>
                                            <input class="form-control" type="text" required value="${i.modifiedBy.accID}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Người sửa gần nhất</label>
                                            <input class="form-control" type="text" required value="${i.modifiedBy.accName}" readonly style="cursor: not-allowed">
                                        </div>
                                    </div>

                                    <BR>
                                    <button class="btn btn-save" type="submit">Lưu lại</button>
                                    <!<!-- send input action to servlet to know what action need -->
                                    <input type="hidden" name="user" value="${sessionScope.acc.accID}">
                                    <input type="hidden" name="action" value="UpdateCarOrder"/>
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
                                                function changeQuantity(selectElement) {
                                                    var selectedValue = selectElement.value;

                                                    // Create a form element
                                                    var form = document.createElement('form');
                                                    form.method = 'post';
                                                    form.action = 'carorderlist'; // Set the URL of your servlet

                                                    // Create an input element to hold the selected value
                                                    var input = document.createElement('input');
                                                    input.type = 'hidden'; // Hidden input field
                                                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                                                    input.value = selectedValue;

                                                    var searchInput = document.createElement('input');
                                                    searchInput.type = 'hidden';
                                                    searchInput.name = 'search'; // Set the name to 'name'
                                                    searchInput.value = '${search}';

                                                    var statusInput = document.createElement('input');
                                                    statusInput.type = 'hidden';
                                                    statusInput.name = 'status'; // Set the name to 'name'
                                                    statusInput.value = '${status}';

                                                    var paymentTypeInput = document.createElement('input');
                                                    paymentTypeInput.type = 'hidden';
                                                    paymentTypeInput.name = 'paymentType'; // Set the name to 'name'
                                                    paymentTypeInput.value = '${paymentType}';

                                                    var endDateInput = document.createElement('input');
                                                    endDateInput.type = 'hidden';
                                                    endDateInput.name = 'endDate'; // Name should match the parameter name in your servlet
                                                    endDateInput.value = document.querySelector('input[name="endDate"]').value;

                                                    // Create an input element to hold the current start date
                                                    var startDateInput = document.createElement('input');
                                                    startDateInput.type = 'hidden';
                                                    startDateInput.name = 'startDate'; // Name should match the parameter name in your servlet
                                                    startDateInput.value = document.querySelector('input[name="startDate"]').value;

                                                    // Append the input to the form and then the form to the body
                                                    form.appendChild(input);
                                                    form.appendChild(searchInput);
                                                    form.appendChild(statusInput);
                                                    form.appendChild(paymentTypeInput);
                                                    form.appendChild(startDateInput);
                                                    form.appendChild(endDateInput);
                                                    document.body.appendChild(form);

                                                    // Submit the form
                                                    form.submit();
                                                }

                                                function changePaymentType(selectElement) {
                                                    var selectedValue = selectElement.value;

                                                    // Create a form element
                                                    var form = document.createElement('form');
                                                    form.method = 'post';
                                                    form.action = 'carorderlist'; // Set the URL of your servlet

                                                    // Create an input element to hold the selected value
                                                    var input = document.createElement('input');
                                                    input.type = 'hidden'; // Hidden input field
                                                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                                                    input.value = '${quantity}';

                                                    var searchInput = document.createElement('input');
                                                    searchInput.type = 'hidden';
                                                    searchInput.name = 'search'; // Set the name to 'name'
                                                    searchInput.value = '${search}';

                                                    var statusInput = document.createElement('input');
                                                    statusInput.type = 'hidden';
                                                    statusInput.name = 'status'; // Set the name to 'name'
                                                    statusInput.value = '${status}';

                                                    var paymentTypeInput = document.createElement('input');
                                                    paymentTypeInput.type = 'hidden';
                                                    paymentTypeInput.name = 'paymentType'; // Set the name to 'name'
                                                    paymentTypeInput.value = selectedValue;
                                                    var endDateInput = document.createElement('input');
                                                    endDateInput.type = 'hidden';
                                                    endDateInput.name = 'endDate'; // Name should match the parameter name in your servlet
                                                    endDateInput.value = document.querySelector('input[name="endDate"]').value;

                                                    // Create an input element to hold the current start date
                                                    var startDateInput = document.createElement('input');
                                                    startDateInput.type = 'hidden';
                                                    startDateInput.name = 'startDate'; // Name should match the parameter name in your servlet
                                                    startDateInput.value = document.querySelector('input[name="startDate"]').value;

                                                    // Append the input to the form and then the form to the body
                                                    form.appendChild(input);
                                                    form.appendChild(searchInput);
                                                    form.appendChild(statusInput);
                                                    form.appendChild(paymentTypeInput);
                                                    form.appendChild(startDateInput);
                                                    form.appendChild(endDateInput);
                                                    document.body.appendChild(form);

                                                    // Submit the form
                                                    form.submit();
                                                }

                                                function changeStatus(selectElement) {
                                                    var selectedValue = selectElement.value;

                                                    // Create a form element
                                                    var form = document.createElement('form');
                                                    form.method = 'post';
                                                    form.action = 'carorderlist'; // Set the URL of your servlet

                                                    // Create an input element to hold the selected value
                                                    var input = document.createElement('input');
                                                    input.type = 'hidden'; // Hidden input field
                                                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                                                    input.value = '${quantity}';

                                                    var searchInput = document.createElement('input');
                                                    searchInput.type = 'hidden';
                                                    searchInput.name = 'search'; // Set the name to 'name'
                                                    searchInput.value = '${search}';

                                                    var statusInput = document.createElement('input');
                                                    statusInput.type = 'hidden';
                                                    statusInput.name = 'status'; // Set the name to 'name'
                                                    statusInput.value = selectedValue;

                                                    var paymentTypeInput = document.createElement('input');
                                                    paymentTypeInput.type = 'hidden';
                                                    paymentTypeInput.name = 'paymentType'; // Set the name to 'name'
                                                    paymentTypeInput.value = '${paymentType}';

                                                    var endDateInput = document.createElement('input');
                                                    endDateInput.type = 'hidden';
                                                    endDateInput.name = 'endDate'; // Name should match the parameter name in your servlet
                                                    endDateInput.value = document.querySelector('input[name="endDate"]').value;

                                                    // Create an input element to hold the current start date
                                                    var startDateInput = document.createElement('input');
                                                    startDateInput.type = 'hidden';
                                                    startDateInput.name = 'startDate'; // Name should match the parameter name in your servlet
                                                    startDateInput.value = document.querySelector('input[name="startDate"]').value;

                                                    // Append the input to the form and then the form to the body
                                                    form.appendChild(input);
                                                    form.appendChild(searchInput);
                                                    form.appendChild(statusInput);
                                                    form.appendChild(paymentTypeInput);
                                                    form.appendChild(startDateInput);
                                                    form.appendChild(endDateInput);
                                                    document.body.appendChild(form);

                                                    // Submit the form
                                                    form.submit();
                                                }



                                                function startDate(inputElement) {
                                                    var startDateValue = inputElement.value;

                                                    // Create a form element
                                                    var form = document.createElement('form');
                                                    form.method = 'post';
                                                    form.action = 'carorderlist'; // Set the URL of your servlet

                                                    var input = document.createElement('input');
                                                    input.type = 'hidden'; // Hidden input field
                                                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                                                    input.value = '${quantity}';

                                                    var searchInput = document.createElement('input');
                                                    searchInput.type = 'hidden';
                                                    searchInput.name = 'search'; // Set the name to 'name'
                                                    searchInput.value = '${search}';

                                                    var statusInput = document.createElement('input');
                                                    statusInput.type = 'hidden';
                                                    statusInput.name = 'status'; // Set the name to 'name'
                                                    statusInput.value = '${status}';

                                                    var paymentTypeInput = document.createElement('input');
                                                    paymentTypeInput.type = 'hidden';
                                                    paymentTypeInput.name = 'paymentType'; // Set the name to 'name'
                                                    paymentTypeInput.value = '${paymentType}';

                                                    // Create an input element to hold the selected start date
                                                    var startDateInput = document.createElement('input');
                                                    startDateInput.type = 'hidden';
                                                    startDateInput.name = 'startDate'; // Name should match the parameter name in your servlet
                                                    startDateInput.value = startDateValue;

                                                    // Create an input element to hold the current end date
                                                    var endDateInput = document.createElement('input');
                                                    endDateInput.type = 'hidden';
                                                    endDateInput.name = 'endDate'; // Name should match the parameter name in your servlet
                                                    endDateInput.value = document.querySelector('input[name="endDate"]').value;

                                                    // Append the input to the form and then the form to the body
                                                    form.appendChild(searchInput);
                                                    form.appendChild(statusInput);
                                                    form.appendChild(paymentTypeInput);
                                                    form.appendChild(startDateInput);
                                                    form.appendChild(endDateInput);
                                                    document.body.appendChild(form);

                                                    // Submit the form
                                                    form.submit();
                                                }

                                                function endDate(inputElement) {
                                                    var endDateValue = inputElement.value;

                                                    // Create a form element
                                                    var form = document.createElement('form');
                                                    form.method = 'post';
                                                    form.action = 'carorderlist'; // Set the URL of your servlet

                                                    var input = document.createElement('input');
                                                    input.type = 'hidden'; // Hidden input field
                                                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                                                    input.value = '${quantity}';

                                                    var searchInput = document.createElement('input');
                                                    searchInput.type = 'hidden';
                                                    searchInput.name = 'search'; // Set the name to 'name'
                                                    searchInput.value = '${search}';

                                                    var statusInput = document.createElement('input');
                                                    statusInput.type = 'hidden';
                                                    statusInput.name = 'status'; // Set the name to 'name'
                                                    statusInput.value = '${status}';

                                                    var paymentTypeInput = document.createElement('input');
                                                    paymentTypeInput.type = 'hidden';
                                                    paymentTypeInput.name = 'paymentType'; // Set the name to 'name'
                                                    paymentTypeInput.value = '${paymentType}';

                                                    // Create an input element to hold the selected end date
                                                    var endDateInput = document.createElement('input');
                                                    endDateInput.type = 'hidden';
                                                    endDateInput.name = 'endDate'; // Name should match the parameter name in your servlet
                                                    endDateInput.value = endDateValue;

                                                    // Create an input element to hold the current start date
                                                    var startDateInput = document.createElement('input');
                                                    startDateInput.type = 'hidden';
                                                    startDateInput.name = 'startDate'; // Name should match the parameter name in your servlet
                                                    startDateInput.value = document.querySelector('input[name="startDate"]').value;

                                                    // Append the input to the form and then the form to the body
                                                    form.appendChild(searchInput);
                                                    form.appendChild(statusInput);
                                                    form.appendChild(paymentTypeInput);
                                                    form.appendChild(startDateInput);
                                                    form.appendChild(endDateInput);
                                                    document.body.appendChild(form);

                                                    // Submit the form
                                                    form.submit();
                                                }

            </script>
    </body>

</html>