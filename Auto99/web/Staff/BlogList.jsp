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

        <title>Blog List</title>
    </head>
    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>
            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item">Danh sách blog</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">

                                    <div class="col">
                                        <a class="btn btn-add btn-sm" href="blogcrud?action=InsertBlog" title="Thêm"><i class="fas fa-plus"></i>
                                            Tạo mới blog</a>
                                    </div>

                                </div>

                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-8">
                                        <div class="length-box" style="display: flex; justify-content: space-between;">
                                            <label>Hiện 
                                                <select id="quantitySelect" name="length" onchange="changeQuantity(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                    <option value="10" <c:if test="${quantity == 10}">selected</c:if>>10</option>
                                                    <option value="25" <c:if test="${quantity == 25}">selected</c:if>>25</option>
                                                    <option value="50" <c:if test="${quantity == 50}">selected</c:if>>50</option>
                                                    <option value="100" <c:if test="${quantity == 100}">selected</c:if>>100</option>
                                                    </select> danh mục
                                                </label>
                                                <label> Thể loại

                                                    <select id="quantitySelect" name="length" style="width: 150px" onchange="changeParent(this)" class="form-control form-control-sm">
                                                        <option value="0">Chọn</option>
                                                    <c:forEach items="${ListBSC}" var="c">
                                                        <option value="${c.parentID}" <c:if test="${parent == c.parentID}">selected</c:if>>${c.title}</option>
                                                    </c:forEach>
                                                </select> 
                                            </label>
                                            <label> Trạng thái
                                                <select id="quantitySelect" style="width: 150px" name="length" onchange="changeStatus(this)" class="form-control form-control-sm">
                                                    <option value="">Chọn</option>
                                                    <option value="true" ${status == 'true' ? 'selected' : ''}>Hoạt động</option>
                                                    <option value="false" ${status == 'false' ? 'selected' : ''}>Ngừng hoạt động</option>
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
                                        <form action="bloglist" method="post">
                                            <div class="search-box">
                                                <label>
                                                    Tìm kiếm:<input type="search" value="${search}" name="search" class="form-control form-control-sm" placeholder="" aria-controls="sampleTable">
                                                </label>
                                                <input type="hidden" name="index" value="1" />
                                                <input type="hidden" name="quantity" value="${quantity}" />
                                                <input type="hidden" name="parent" value="${parent}" />
                                                <input type="hidden" name="status" value="${status}" />
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
                                <form id="SendAllBlogIDChecked" action="blogcrud" method="POST">
                                    <table class="table table-hover table-bordered" id="sampleTable">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center">BlogID</th>
                                                <th style="text-align: center">Tiêu đề</th>
                                                <th style="text-align: center">Trạng thái</th>
                                                <th style="text-align: center">Ngày đăng</th>
                                                <th style="text-align: center">Chức Năng</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="i" items="${ListBlog}" varStatus="loop">
                                                <tr>

                                                    <td style="width: 80px; text-align: center">${i.blogID}</td>
                                                    <td style="width: 1000px;">${i.title}</td>
                                                    <td style="text-align: center;"><span class="badge
                                                                                          <c:choose>
                                                                                              <c:when test = "${i.status == true}">
                                                                                                  bg-success
                                                                                              </c:when>
                                                                                              <c:otherwise>
                                                                                                  bg-danger
                                                                                              </c:otherwise>
                                                                                          </c:choose>
                                                                                          ">${i.status ? "Hoạt động" : "Ngừng hoạt động"}</span></td>
                                                    <td style="text-align: center">
                                                        <fmt:parseDate var="parsedDate" value="${i.createdOn}" pattern="yyyy-MM-dd'T'HH:mm" />
                                                        <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="dd-MM-yyyy hh:mm a" />
                                                        ${formattedDate}                                                        
                                                    </td>
                                                    <td style="text-align: center">
                                                        <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                                data-target="#ModalUP${i.blogID}"><i class="fas fa-edit"></i></button>

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
                                                    <a href="bloglist?index=${currentIndex -1}&search=${search}&quantity=${quantity}&status=${status}&parent=${parent}&startDate=${startDate}&endDate=${endDate}" class="page-link">Lùi</a>
                                                </li>

                                                <c:forEach begin="1" end="${endPage}" var="i">

                                                    <li class="paginate_button page-item ${index == i?" active ":" "}">
                                                        <a href="bloglist?index=${i}&search=${search}&quantity=${quantity}&status=${status}&parent=${parent}&startDate=${startDate}&endDate=${endDate}" class="page-link">${i}</a>
                                                    </li>

                                                </c:forEach>

                                                <li class="paginate_button page-item next <c:if test="${currentIndex==endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="bloglist?index=${currentIndex +1}&search=${search}&quantity=${quantity}&status=${status}&parent=${parent}&startDate=${startDate}&endDate=${endDate}" class="page-link">Tiếp</a>
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

            <c:forEach items="${ListBlog}" var="i">
                <form  action="blogcrud" method="POST"  enctype="multipart/form-data">
                    <div class="modal fade" id="ModalUP${i.blogID}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                         data-keyboard="false">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group  col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5>Chỉnh sửa thông tin blog cơ bản</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <input type="hidden" name="blogID" value="${i.blogID}" />

                                        <div class="form-group col-md-6">
                                            <label class="control-label">Tiêu đề blog</label>
                                            <input class="form-control" type="text" required value="${i.title}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group col-md-6 ">
                                            <label for="exampleSelect1" class="control-label">Tình trạng blog</label>
                                            <select class="form-control" name="status">
                                                <option value="${i.status}">${i.status ? "Hoạt động" : "Ngưng hoạt động"}</option>
                                                <option value="${!i.status}">${!i.status ? "Hoạt động" : "Ngưng hoạt động"}</option>
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
                                            <label class="control-label">ID Người đăng</label>
                                            <input class="form-control" type="text" required value="${i.createdBy.accID}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">ID Người sửa gần nhất</label>
                                            <input class="form-control" type="text" required value="${i.modifiedBy.accID}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Người đăng</label>
                                            <input class="form-control" type="text" required value="${i.createdBy.accName}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Người sửa gần nhất</label>
                                            <input class="form-control" type="text" required value="${i.modifiedBy.accName}" readonly style="cursor: not-allowed">
                                        </div>
                                    </div>
                                    <BR>
                                    <a href="blogcrud?action=AdvancedUpdateBlog&blogID=${i.blogID}" style="float: right;
                                       font-weight: 600;
                                       color: #ea0000;">Chỉnh sửa sản phẩm nâng cao</a>
                                    <BR>
                                    <BR>
                                    <button class="btn btn-save" type="submit">Lưu lại</button>
                                    <!<!-- send input action to servlet to know what action need -->
                                    <input type="hidden" name="user" value="${sessionScope.acc.accID}">
                                    <input type="hidden" name="action" value="UpdateBlog"/>
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
                                                document.addEventListener("DOMContentLoaded", function () {
                                                    // Lấy ra checkbox "Tất cả" và tất cả các checkbox trong bảng
                                                    var selectAllCheckbox = document.getElementById('all');
                                                    var checkboxes = document.querySelectorAll('input[name="checkedBlogID"]');

                                                    // Thêm sự kiện nghe cho checkbox "Tất cả"
                                                    selectAllCheckbox.addEventListener('change', function () {
                                                        // Nếu checkbox "Tất cả" được chọn
                                                        if (selectAllCheckbox.checked) {
                                                            // Đặt checked = true cho tất cả các checkbox trong bảng
                                                            checkboxes.forEach(function (checkbox) {
                                                                checkbox.checked = true;
                                                            });
                                                        } else {
                                                            // Đặt checked = false cho tất cả các checkbox trong bảng
                                                            checkboxes.forEach(function (checkbox) {
                                                                checkbox.checked = false;
                                                            });
                                                        }
                                                    });
                                                });
            </script>
            <script>
                function changeQuantity(selectElement) {
                    var selectedValue = selectElement.value;

                    // Create a form element
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = 'bloglist'; // Set the URL of your servlet

                    // Create an input element to hold the selected value
                    var input = document.createElement('input');
                    input.type = 'hidden'; // Hidden input field
                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                    input.value = selectedValue;

                    var searchInput = document.createElement('input');
                    searchInput.type = 'hidden';
                    searchInput.name = 'search'; // Set the name to 'name'
                    searchInput.value = '${search}';

                    var parentInput = document.createElement('input');
                    parentInput.type = 'hidden';
                    parentInput.name = 'parent';
                    parentInput.value = '${parent}';

                    var statusInput = document.createElement('input');
                    statusInput.type = 'hidden';
                    statusInput.name = 'status'; // Set the name to 'name'
                    statusInput.value = '${status}';

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
                    form.appendChild(parentInput);
                    form.appendChild(statusInput);
                    form.appendChild(startDateInput);
                    form.appendChild(endDateInput);
                    document.body.appendChild(form);

                    // Submit the form
                    form.submit();
                }

                function changeParent(selectElement) {
                    var selectedValue = selectElement.value;

                    // Create a form element
                    var form = document.createElement('form');
                    form.method = 'post';
                    form.action = 'bloglist'; // Set the URL of your servlet

                    // Create an input element to hold the selected value
                    var input = document.createElement('input');
                    input.type = 'hidden'; // Hidden input field
                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                    input.value = ${quantity};

                    var searchInput = document.createElement('input');
                    searchInput.type = 'hidden';
                    searchInput.name = 'search'; // Set the name to 'name'
                    searchInput.value = '${search}';

                    var parentInput = document.createElement('input');
                    parentInput.type = 'hidden';
                    parentInput.name = 'parent'; // Set the name to 'name'
                    parentInput.value = selectedValue;

                    var statusInput = document.createElement('input');
                    statusInput.type = 'hidden';
                    statusInput.name = 'status'; // Set the name to 'name'
                    statusInput.value = '${status}';

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
                    form.appendChild(parentInput);
                    form.appendChild(statusInput);
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
                    form.action = 'bloglist'; // Set the URL of your servlet

                    // Create an input element to hold the selected value
                    var input = document.createElement('input');
                    input.type = 'hidden'; // Hidden input field
                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                    input.value = '${quantity}';

                    var searchInput = document.createElement('input');
                    searchInput.type = 'hidden';
                    searchInput.name = 'search'; // Set the name to 'name'
                    searchInput.value = '${search}';

                    var parentInput = document.createElement('input');
                    parentInput.type = 'hidden';
                    parentInput.name = 'parent'; // Set the name to 'name'
                    parentInput.value = '${parent}';

                    var statusInput = document.createElement('input');
                    statusInput.type = 'hidden';
                    statusInput.name = 'status'; // Set the name to 'name'
                    statusInput.value = selectedValue;

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
                    form.appendChild(parentInput);
                    form.appendChild(statusInput);
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
                    form.action = 'bloglist'; // Set the URL of your servlet

                    var input = document.createElement('input');
                    input.type = 'hidden'; // Hidden input field
                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                    input.value = '${quantity}';

                    var searchInput = document.createElement('input');
                    searchInput.type = 'hidden';
                    searchInput.name = 'search'; // Set the name to 'name'
                    searchInput.value = '${search}';

                    var parentInput = document.createElement('input');
                    parentInput.type = 'hidden';
                    parentInput.name = 'parent'; // Set the name to 'name'
                    parentInput.value = '${parent}';

                    var statusInput = document.createElement('input');
                    statusInput.type = 'hidden';
                    statusInput.name = 'status'; // Set the name to 'name'
                    statusInput.value = '${status}';

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
                    form.appendChild(parentInput);
                    form.appendChild(statusInput);
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
                    form.action = 'bloglist'; // Set the URL of your servlet

                    var input = document.createElement('input');
                    input.type = 'hidden'; // Hidden input field
                    input.name = 'quantity'; // Name should match the parameter name in your servlet
                    input.value = '${quantity}';

                    var searchInput = document.createElement('input');
                    searchInput.type = 'hidden';
                    searchInput.name = 'search'; // Set the name to 'name'
                    searchInput.value = '${search}';

                    var parentInput = document.createElement('input');
                    parentInput.type = 'hidden';
                    parentInput.name = 'parent'; // Set the name to 'name'
                    parentInput.value = '${parent}';

                    var statusInput = document.createElement('input');
                    statusInput.type = 'hidden';
                    statusInput.name = 'status'; // Set the name to 'name'
                    statusInput.value = '${status}';

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
                    form.appendChild(parentInput);
                    form.appendChild(statusInput);
                    form.appendChild(startDateInput);
                    form.appendChild(endDateInput);
                    document.body.appendChild(form);

                    // Submit the form
                    form.submit();
                }

            </script>
    </body>

</html>