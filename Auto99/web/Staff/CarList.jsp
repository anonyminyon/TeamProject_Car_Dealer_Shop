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
        <title>Car List</title>
    </head>

    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>
            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item">Danh sách sản phẩm</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">

                                    <div class="col">
                                        <a class="btn btn-add btn-sm" href="carcrud?action=AddCar" title="Thêm"><i class="fas fa-plus"></i>
                                            Tạo mới sản phẩm</a>
                                        <a class="btn btn-add btn-sm" href="brandlist" title="Thêm"><i class="fas fa-plus"></i>
                                            Xem danh sách hãng xe</a>
                                        <a class="btn btn-add btn-sm" href="designlist" title="Thêm"><i class="fas fa-plus"></i>
                                            Xem danh sách thiết kế</a>
                                    </div>

                                </div>

                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-8 text-lg-left">
                                        <div class="length-box">
                                            <label>Hiện 
                                                <select id="quantitySelect" name="length" onchange="changeQuantity(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                    <option value="5" <c:if test="${quantity == 5}">selected</c:if>>5</option>
                                                    <option value="10" <c:if test="${quantity == 10}">selected</c:if>>10</option>
                                                    <option value="25" <c:if test="${quantity == 25}">selected</c:if>>25</option>
                                                    <option value="50" <c:if test="${quantity == 50}">selected</c:if>>50</option>
                                                    </select> danh mục
                                                </label>

                                                <label>Hãng 
                                                    <select id="quantitySelect" name="length" onchange="changeBrand(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                        <option value="0" <c:if test="${brand == 0}">selected</c:if>>Chọn</option>
                                                    <c:forEach var="b" items="${BrandList}">
                                                        <option value="${b.getBrandID()}" <c:if test="${brand == b.getBrandID()}">selected</c:if>>${b.getBrandName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </label>

                                            <label>Thiết Kế 
                                                <select id="quantitySelect" name="length" onchange="changeDesign(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                    <option value="0" <c:if test="${design == 0}">selected</c:if>>Chọn</option>
                                                    <c:forEach var="d" items="${DesignList}">
                                                        <option value="${d.getDesignID()}" <c:if test="${design == d.getDesignID()}">selected</c:if>>${d.getDesign()}</option>
                                                    </c:forEach>
                                                </select>
                                            </label>

                                            <label>Trạng Thái
                                                <select id="quantitySelect" name="length" onchange="changeStatus(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                    <option value="0" <c:if test="${status == 0}">selected</c:if>>Chọn</option>
                                                    <c:forEach var="s" items="${StatusList}">
                                                        <option value="${s.getStatusID()}" <c:if test="${status == s.getStatusID()}">selected</c:if>>${s.getStatusName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </label>
                                        </div>

                                        <div class="length-box">
                                            <form action="carlist" method="get">
                                                <div class="price-input">
                                                    <div class="field">
                                                        <span>Min</span>
                                                        <fmt:formatNumber value="${minPrice}" type="number" pattern="#,###" var="MinformattedValue" />
                                                        <input type="text" name="minPrice" class="input-min" value="${MinformattedValue} ₫">
                                                        <span> VNĐ</span>
                                                    </div>
                                                    <div class="separator">-</div>
                                                    <div class="field">
                                                        <span>Max</span>
                                                        <fmt:formatNumber value="${maxPrice}" type="number" pattern="#,###" var="MaxformattedValue" />
                                                        <input type="text" name="maxPrice" class="input-max" value="${MaxformattedValue} ₫">
                                                        <span> VNĐ</span>
                                                    </div>
                                                    <div class="separator">   
                                                        <button type="submit" class="btn btn-outline-success">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                                                            </svg>
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="slider">
                                                    <div class="progress"></div>
                                                </div>
                                                <div class="range-input">
                                                    <input type="range" class="range-min" min="0" max="${MAXCarPrice}" value="${minPrice}" step="1000000">
                                                    <input type="range" class="range-max" min="0" max="${MAXCarPrice}" value="${maxPrice}" step="1000000">
                                                </div>
                                                <input type="hidden" name="search" value="${name}" />
                                                <input type="hidden" name="index" value="1" />
                                                <input type="hidden" name="quantity" value="${quantity}" />
                                                <input type="hidden" name="brand" value="${brand}" />
                                                <input type="hidden" name="design" value="${design}" />
                                                <input type="hidden" name="status" value="${status}" />
                                                <input type="hidden" name="action" value="FilterPrice" />
                                            </form>
                                        </div>

                                    </div>
                                    <div class="col-sm-12 col-md-4">
                                        <form action="carlist" method="get">
                                            <div class="search-box">
                                                <label>
                                                    Tìm kiếm:<input type="search" value="${name}" required = "required" name="name" class="form-control form-control-sm" placeholder="" aria-controls="sampleTable">                                                
                                                    <button type="submit" type="button" class="btn btn-success" fdprocessedid="wjwv0s">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                                                        </svg>
                                                    </button>
                                                </label>
                                                <input type="hidden" name="index" value="1" />
                                                <input type="hidden" name="quantity" value="${quantity}" />
                                                <input type="hidden" name="brand" value="${brand}" />
                                                <input type="hidden" name="design" value="${design}" />
                                                <input type="hidden" name="status" value="${status}" />
                                                <input type="hidden" name="minPrice" value="${minPrice}" />
                                                <input type="hidden" name="maxPrice" value="${maxPrice}" />
                                            </div>
                                        </form>

                                    </div>
                                </div>


                                <table class="table table-hover table-bordered" id="sampleTable">
                                    <thead>
                                        <tr>
                                            <th width="10"><input type="checkbox" id="all"></th>
                                            <th>ID Xe</th>
                                            <th>Tên Xe</th>
                                            <th>Hãng</th>
                                            <th>Thiết Kế</th>
                                            <th>Tình Trạng</th>
                                            <th>Giá Tiền</th>
                                            <th>Chức Năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="c" items="${CarList}">
                                            <tr>
                                                <td width="10"><input type="checkbox" name="check1" value="1"></td>
                                                <td>${c.getCarID()}</td>
                                                <td>${c.getCarName()}</td>
                                                <td>${c.brandID.getBrandName()}</td>
                                                <td>${c.design.getDesign()}</td>

                                                <td><span class="badge
                                                          <c:choose>
                                                              <c:when test = "${c.status.getStatusID()==1}">
                                                                  bg-success
                                                              </c:when>
                                                              <c:when test = "${c.status.getStatusID()==2}">
                                                                  bg-danger
                                                              </c:when>
                                                              <c:otherwise>
                                                                  bg-secondary
                                                              </c:otherwise>
                                                          </c:choose>
                                                          ">${c.status.statusName}</span></td>

                                                <td><fmt:formatNumber value="${c.getPrice()}" type="number" pattern="#,###" /> VNĐ</td>
                                                <td style="text-align: center">
                                                    <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                            data-target="#ModalUP${c.getCarID()}"><i class="fas fa-edit"></i></button>

                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>

                                <div class="row">
                                    <div class="col-sm-12 col-md-5">
                                        <div class="dataTables_info" role="status">

                                            <c:choose>

                                                <c:when test="${count>0}">
                                                    Hiện ${(index-1)*quantity+1} đến ${((index*quantity)>count)?count:index*quantity} của ${count} danh mục
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
                                                    <a href="carlist?index=${index -1}&name=${name}&quantity=${quantity}&status=${status}&brand=${brand}&design=${design}&minPrice=${minPrice}&maxPrice=${maxPrice}" class="page-link">Lùi</a>
                                                </li>

                                                <c:forEach begin="1" end="${endPage}" var="i">

                                                    <li class="paginate_button page-item ${index == i?"active":""}">
                                                        <a href="carlist?index=${i}&name=${name}&quantity=${quantity}&status=${status}&brand=${brand}&design=${design}&minPrice=${minPrice}&maxPrice=${maxPrice}" class="page-link">${i}</a>
                                                    </li>

                                                </c:forEach>

                                                <li class="paginate_button page-item next <c:if test="${index==endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="carlist?index=${index +1}&name=${name}&quantity=${quantity}&status=${status}&brand=${brand}&design=${design}&minPrice=${minPrice}&maxPrice=${maxPrice}" class="page-link">Tiếp</a>
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
            <c:forEach items="${CarList}" var="c">
                <form  action="carlist" method="POST">

                    <div class="modal fade" id="ModalUP${c.getCarID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                         data-keyboard="false">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group  col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5>Chỉnh sửa thông tin xe cơ bản</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <input type="hidden" name="carID" value="${c.getCarID()}" />
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Tên Xe</label>
                                            <input class="form-control" name="carName" type="text" required value="${c.getCarName()}" readonly style="cursor: not-allowed">
                                        </div>

                                        <div class="form-group col-md-6">
                                            <label class="control-label">Tình Trạng</label>
                                            <select class="form-control" name="statusID">
                                                <c:forEach items="${StatusList}" var="s">
                                                    <option value="${s.getStatusID()}" ${(s.getStatusID()==c.status.getStatusID())?"selected":""}>${s.getStatusName()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-md-6">
                                            <label class="control-label">Giá bán</label>
                                            <input class="form-control" type="text" value="${c.getPrice()}" name="price" require ="require"
                                                   pattern="([0-9]*(\.)?)[0-9]*" 
                                                   title="Xin hãy nhập 1 số thực">
                                        </div>

                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Ngày tạo</label>
                                            <input class="form-control" type="text" required value="${c.getCreatedOn()}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Ngày sửa gần nhất</label>
                                            <input class="form-control" type="text" required value="${c.getModifiedOn()}" readonly style="cursor: not-allowed">
                                        </div>    
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Người tạo</label>
                                            <input class="form-control" type="text" required value="${c.getCreatedBy().getAccName()}" readonly style="cursor: not-allowed">
                                        </div>
                                        <div class="form-group  col-md-6">
                                            <label class="control-label">Người sửa gần nhất</label>
                                            <input class="form-control" type="text" required value="${c.getModifiedBy().getAccName()}" readonly style="cursor: not-allowed">
                                        </div>

                                        <input type="hidden" name="index" value="${index}">
                                        <input type="hidden" name="name" value="${name}">
                                        <input type="hidden" name="quantity" value="${quantity}">
                                        <input type="hidden" name="status" value="${status}">
                                        <input type="hidden" name="brand" value="${brand}">
                                        <input type="hidden" name="design" value="${design}">
                                        <input type="hidden" name="minPrice" value="${minPrice}">
                                        <input type="hidden" name="maxPrice" value="${maxPrice}">                                                   

                                    </div>
                                    <BR>
                                    <a href="carcrud?action=EditCar&carID=${c.getCarID()}" 
                                       style="float: right;
                                       font-weight: 600;
                                       color: #ea0000;">Chỉnh sửa sản phẩm nâng cao</a>
                                    <BR>
                                    <BR>
                                    <button class="btn btn-save" type="submit">Lưu lại</button>
                                    <!<!-- send input action to servlet to know what action need -->
                                    <!--                                    <input type="hidden" name="action" value="UpdateCar"/>-->
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
                                            var confirmation = confirm("Bạn có chắc chắn muốn huỷ thay đổi xe?");
                                            if (!confirmation) {
                                                event.preventDefault(); // Ngăn chặn điều hướng đến đường dẫn URL
                                            }
                                        }

        </script>


        <script>
//            const rangeInput = document.querySelectorAll(".range-input input"),
//                    priceInput = document.querySelectorAll(".price-input input"),
//                    range = document.querySelector(".slider .progress");
//            let priceGap = 0;
//            priceInput.forEach(input => {
//                input.addEventListener("input", e => {
//                    let minPrice = parseInt(priceInput[0].value),
//                            maxPrice = parseInt(priceInput[1].value);
//
//                    if ((maxPrice - minPrice >= priceGap) && maxPrice <= rangeInput[1].max) {
//                        if (e.target.className === "input-min") {
//                            rangeInput[0].value = minPrice;
//                            range.style.left = ((minPrice / rangeInput[0].max) * 100) + "%";
//                        } else {
//                            rangeInput[1].value = maxPrice;
//                            range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
//                        }
//                    }
//                });
//            });
//            rangeInput.forEach(input => {
//                input.addEventListener("input", e => {
//                    let minVal = parseInt(rangeInput[0].value),
//                            maxVal = parseInt(rangeInput[1].value);
//                    if ((maxVal - minVal) < priceGap) {
//                        if (e.target.className === "range-min") {
//                            rangeInput[0].value = maxVal - priceGap;
//                        } else {
//                            rangeInput[1].value = minVal + priceGap;
//                        }
//                    } else {
//                        priceInput[0].value = minVal;
//                        priceInput[1].value = maxVal;
//                        range.style.left = ((minVal / rangeInput[0].max) * 100) + "%";
//                        range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
//                    }
//                });
//            });

            const rangeInput = document.querySelectorAll(".range-input input"),
                    priceInput = document.querySelectorAll(".price-input input"),
                    range = document.querySelector(".slider .progress");
            let priceGap = 0;

// Function to get and set the cursor position in an input element
            function setCursor(input, start, end) {
                input.setSelectionRange(start, end);
            }

// Function to prevent input exceeding the maximum
            function preventInputExceedingMax(input, max) {
                input.addEventListener("input", (e) => {
                    if (parseFloat(e.target.value.replace(/\D/g, '')) > max) {
                        e.target.value = formatCurrency(max);
                        setCursor(e.target, e.target.selectionStart, e.target.selectionEnd);
                    }
                });
            }

            priceInput.forEach((input, index) => {
                preventInputExceedingMax(input, rangeInput[1].max);

                input.addEventListener("input", (e) => {
                    let minPrice = parseFloat(priceInput[0].value.replace(/\D/g, ''));
                    let maxPrice = parseFloat(priceInput[1].value.replace(/\D/g, ''));

                    if (maxPrice - minPrice >= priceGap && maxPrice <= rangeInput[1].max) {
                        if (e.target.className === "input-min") {
                            rangeInput[0].value = minPrice;
                            range.style.left = (minPrice / rangeInput[0].max) * 100 + "%";
                        } else {
                            rangeInput[1].value = maxPrice;
                            range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
                        }

                        // Format minPrice and maxPrice as VNĐ
                        priceInput[0].value = formatCurrency(minPrice);
                        priceInput[1].value = formatCurrency(maxPrice);

                        // Restore cursor position
                        setCursor(priceInput[index], e.target.selectionStart, e.target.selectionEnd);
                    }
                });
            });

            rangeInput.forEach((input) => {
                input.addEventListener("input", (e) => {
                    let minVal = parseInt(rangeInput[0].value);
                    let maxVal = parseInt(rangeInput[1].value);
                    if (maxVal - minVal < priceGap) {
                        if (e.target.className === "range-min") {
                            rangeInput[0].value = maxVal - priceGap;
                        } else {
                            rangeInput[1].value = minVal + priceGap;
                        }
                    } else {
                        priceInput[0].value = formatCurrency(minVal);
                        priceInput[1].value = formatCurrency(maxVal);

                        // Restore cursor position
                        setCursor(priceInput[0], e.target.selectionStart, e.target.selectionEnd);
                    }

                    range.style.left = (minVal / rangeInput[0].max) * 100 + "%";
                    range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
                });
            });

// Function to format a number as VNĐ
            function formatCurrency(number) {
                const formatter = new Intl.NumberFormat("vi-VN", {
                    style: "currency",
                    currency: "VND",
                });
                return formatter.format(number);
            }
        </script>

        <script>
            function changeBrand(selectElement) {
                var selectedValue = selectElement.value;
                // Get minPrice and maxPrice values
                var minPriceInput = document.querySelector(".input-min");
                var maxPriceInput = document.querySelector(".input-max");
                var minPrice = minPriceInput.value;
                var maxPrice = maxPriceInput.value;
// Create a form element
                var form = document.createElement('form');
                form.method = 'post';
                form.action = 'carlist'; // Set the URL of your servlet

// Create an input element for the quantity
                var quantityInput = document.createElement('input');
                quantityInput.type = 'hidden';
                quantityInput.name = 'quantity';
                quantityInput.value = '${quantity}';
// Create an input element for the name
                var nameInput = document.createElement('input');
                nameInput.type = 'hidden';
                nameInput.name = 'name'; // Set the name to 'name'
                nameInput.value = '${name}'; // Set the value to the desired name

// Create an input element for the brand
                var brandInput = document.createElement('input');
                brandInput.type = 'hidden';
                brandInput.name = 'brand'; // Set the name to 'brand'
                brandInput.value = selectedValue; // Set the value to the desired brand

// Create an input element for the design
                var designInput = document.createElement('input');
                designInput.type = 'hidden';
                designInput.name = 'design'; // Set the name to 'design'
                designInput.value = '${design}'; // Set the value to the desired design

// Create an input element for the status
                var statusInput = document.createElement('input');
                statusInput.type = 'hidden';
                statusInput.name = 'status'; // Set the name to 'status'
                statusInput.value = '${status}'; // Set the value to the desired status

                // Create input elements for minPrice and maxPrice
                var minPriceInput = document.createElement('input');
                minPriceInput.type = 'hidden';
                minPriceInput.name = 'minPrice';
                minPriceInput.value = minPrice;

                var maxPriceInput = document.createElement('input');
                maxPriceInput.type = 'hidden';
                maxPriceInput.name = 'maxPrice';
                maxPriceInput.value = maxPrice;

// Append all input elements to the form
                form.appendChild(quantityInput);
                form.appendChild(nameInput);
                form.appendChild(brandInput);
                form.appendChild(designInput);
                form.appendChild(statusInput);
                form.appendChild(minPriceInput);
                form.appendChild(maxPriceInput);
// Append the form to the body
                document.body.appendChild(form);
// Submit the form
                form.submit();
            }
        </script>

        <script>
            function changeDesign(selectElement) {
                var selectedValue = selectElement.value;
                // Get minPrice and maxPrice values
                var minPriceInput = document.querySelector(".input-min");
                var maxPriceInput = document.querySelector(".input-max");
                var minPrice = minPriceInput.value;
                var maxPrice = maxPriceInput.value;
// Create a form element
                var form = document.createElement('form');
                form.method = 'post';
                form.action = 'carlist'; // Set the URL of your servlet

// Create an input element for the quantity
                var quantityInput = document.createElement('input');
                quantityInput.type = 'hidden';
                quantityInput.name = 'quantity';
                quantityInput.value = '${quantity}';
// Create an input element for the name
                var nameInput = document.createElement('input');
                nameInput.type = 'hidden';
                nameInput.name = 'name'; // Set the name to 'name'
                nameInput.value = '${name}'; // Set the value to the desired name

// Create an input element for the brand
                var brandInput = document.createElement('input');
                brandInput.type = 'hidden';
                brandInput.name = 'brand'; // Set the name to 'brand'
                brandInput.value = '${brand}'; // Set the value to the desired brand

// Create an input element for the design
                var designInput = document.createElement('input');
                designInput.type = 'hidden';
                designInput.name = 'design'; // Set the name to 'design'
                designInput.value = selectedValue; // Set the value to the desired design

// Create an input element for the status
                var statusInput = document.createElement('input');
                statusInput.type = 'hidden';
                statusInput.name = 'status'; // Set the name to 'status'
                statusInput.value = '${status}'; // Set the value to the desired status

                // Create input elements for minPrice and maxPrice
                var minPriceInput = document.createElement('input');
                minPriceInput.type = 'hidden';
                minPriceInput.name = 'minPrice';
                minPriceInput.value = minPrice;

                var maxPriceInput = document.createElement('input');
                maxPriceInput.type = 'hidden';
                maxPriceInput.name = 'maxPrice';
                maxPriceInput.value = maxPrice;

// Append all input elements to the form
                form.appendChild(quantityInput);
                form.appendChild(nameInput);
                form.appendChild(brandInput);
                form.appendChild(designInput);
                form.appendChild(statusInput);
                form.appendChild(minPriceInput);
                form.appendChild(maxPriceInput);
// Append the form to the body
                document.body.appendChild(form);
// Submit the form
                form.submit();
            }
        </script>

        <script>
            function changeStatus(selectElement) {
                var selectedValue = selectElement.value;
                // Get minPrice and maxPrice values
                var minPriceInput = document.querySelector(".input-min");
                var maxPriceInput = document.querySelector(".input-max");
                var minPrice = minPriceInput.value;
                var maxPrice = maxPriceInput.value;
// Create a form element
                var form = document.createElement('form');
                form.method = 'post';
                form.action = 'carlist'; // Set the URL of your servlet

// Create an input element for the quantity
                var quantityInput = document.createElement('input');
                quantityInput.type = 'hidden';
                quantityInput.name = 'quantity';
                quantityInput.value = '${quantity}';
// Create an input element for the name
                var nameInput = document.createElement('input');
                nameInput.type = 'hidden';
                nameInput.name = 'name'; // Set the name to 'name'
                nameInput.value = '${name}'; // Set the value to the desired name

// Create an input element for the brand
                var brandInput = document.createElement('input');
                brandInput.type = 'hidden';
                brandInput.name = 'brand'; // Set the name to 'brand'
                brandInput.value = '${brand}'; // Set the value to the desired brand

// Create an input element for the design
                var designInput = document.createElement('input');
                designInput.type = 'hidden';
                designInput.name = 'design'; // Set the name to 'design'
                designInput.value = '${design}'; // Set the value to the desired design

// Create an input element for the status
                var statusInput = document.createElement('input');
                statusInput.type = 'hidden';
                statusInput.name = 'status'; // Set the name to 'status'
                statusInput.value = selectedValue; // Set the value to the desired status

                // Create input elements for minPrice and maxPrice
                var minPriceInput = document.createElement('input');
                minPriceInput.type = 'hidden';
                minPriceInput.name = 'minPrice';
                minPriceInput.value = minPrice;

                var maxPriceInput = document.createElement('input');
                maxPriceInput.type = 'hidden';
                maxPriceInput.name = 'maxPrice';
                maxPriceInput.value = maxPrice;

// Append all input elements to the form
                form.appendChild(quantityInput);
                form.appendChild(nameInput);
                form.appendChild(brandInput);
                form.appendChild(designInput);
                form.appendChild(statusInput);
                form.appendChild(minPriceInput);
                form.appendChild(maxPriceInput);
// Append the form to the body
                document.body.appendChild(form);
// Submit the form
                form.submit();
            }
        </script>

        <script>
            // Function to format the price input with commas
            function formatPriceInput(input) {
                let value = input.value.replace(/\D/g, ''); // Remove non-numeric characters
                value = new Intl.NumberFormat().format(value); // Format with commas
                input.value = value;
            }

            // Function to validate that the input is a valid number
            function validatePriceInput(input) {
                let value = input.value.replace(/\D/g, ''); // Remove non-numeric characters
                if (isNaN(value)) {
                    input.value = ''; // Clear the input if it's not a valid number
                }
            }

            // Add event listeners to the input fields for formatting and validation
            const minPriceInput = document.getElementById('minPrice');
            minPriceInput.addEventListener('input', () => {
                formatPriceInput(minPriceInput);
            });
            minPriceInput.addEventListener('blur', () => {
                validatePriceInput(minPriceInput);
            });

            const maxPriceInput = document.getElementById('maxPrice');
            maxPriceInput.addEventListener('input', () => {
                formatPriceInput(maxPriceInput);
            });
            maxPriceInput.addEventListener('blur', () => {
                validatePriceInput(maxPriceInput);
            });
        </script>

    </body>

</html>