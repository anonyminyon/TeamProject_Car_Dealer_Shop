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
                        <li class="breadcrumb-item"><a href="autopart">Danh sách sản phẩm</a></li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <div class="row element-button">

                                    <div class="col">
                                        <a class="btn btn-add btn-sm" href="partcrud?action=add" title="Thêm"><i class="fas fa-plus"></i>
                                            Tạo mới sản phẩm</a>
                                    </div>

                                    <!--                                    <div class="col text-right">
                                                                            <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i
                                                                                    class="fas fa-trash-alt"></i> Xóa tất cả </a>
                                                                        </div>-->

                                </div>

                                <div class="row search-length">
                                    <div class="col-sm-12 col-md-6">
                                        <div class="length-box" >
                                            <label>Hiện 
                                                <form>
                                                    <select id="quantitySelect" name="length" onchange="changeQuantity(this)" aria-controls="sampleTable" class="form-control form-control-sm">
                                                        <option value="5" <c:if test="${quantity == 5}">selected</c:if>>5</option>
                                                        <option value="10" <c:if test="${quantity == 10}">selected</c:if>>10</option>
                                                        <option value="25" <c:if test="${quantity == 25}">selected</c:if>>25</option>
                                                        <option value="50" <c:if test="${quantity == 50}">selected</c:if>>50</option>
                                                        </select> danh mục
                                                    </form>
                                                </label>
                                                <script>
                                                    function changeQuantity(selectElement) {
                                                        var selectedValue = selectElement.value;

                                                        // Create a form element
                                                        var form = document.createElement('form');
                                                        form.method = 'get';
                                                        form.action = 'autopart'; // Set the URL of your servlet

                                                        // Create an input element to hold the selected value
                                                        var input = document.createElement('input');
                                                        input.type = 'hidden'; // Hidden input field
                                                        input.name = 'quantity'; // Name should match the parameter name in your servlet
                                                        input.value = selectedValue;

                                                        // Append the input to the form and then the form to the body
                                                        form.appendChild(input);
                                                        document.body.appendChild(form);

                                                        // Submit the form
                                                        form.submit();
                                                    }
                                                </script>

                                            </div>
                                        </div>
                                        <div class="col-sm-12 col-md-6">
                                            <form action="autopart" method="get">
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
                                                <button type="submit" class="btn btn-outline-danger">Search</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>


                                <table class="table table-hover table-bordered" id="sampleTable">
                                    <thead>
                                        <tr>
                                            <th width="10"><input type="checkbox" id="all"></th>
                                            <th>Mã phụ tùng</th>
                                            <th>Tên phụ tùng</th>
                                            <th>Ảnh</th>
                                            <th>Trạng thái</th>
                                            <th>Giá tiền</th>
                                            <th>Chức năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="c" items="${autoPartList}">
                                            <tr>
                                                <td width="10">
                                                    <input type="checkbox">
                                                </td>
                                                <td>${c.getAutoPartID()}</td>
                                                <td>${c.getPartName()}</td>
                                                <td><img src="./img/AutoPart/${c.getAutoPartID()}/${c.getPartIMG()}" alt="" width="120px;"></td>
                                                <td style="text-align: center;"><span class="badge
                                                                                      <c:choose>
                                                                                          <c:when test = "${c.isStatus() == true}">
                                                                                              bg-success
                                                                                          </c:when>
                                                                                          <c:otherwise>
                                                                                              bg-danger
                                                                                          </c:otherwise>
                                                                                      </c:choose>
                                                                                      ">${c.isStatus() ? "Còn sản phẩm" : "Hết sản phẩm"}</span></td>
                                                <td>${c.getPrice()} VNĐ</td>  
                                                <td>
                                                    <!--                                                    <button class="btn btn-primary btn-sm trash" type="button" title="Xóa"
                                                                                                                onclick="myFunction(this)" data-autopartid="${c.autoPartID}"><i class="fas fa-trash-alt"></i> 
                                                                                                        </button>-->
                                                    <!--                                                    <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                                                                                data-target="#ModalUP" data-autopartid="${c.autoPartID}"><i class="fas fa-edit"></i></button> -->
                                                    <button style="margin-left: 30px;" class="btn btn-primary show" type="button" title="show" id="show-emp" data-toggle="modal"
                                                            data-target="#ModalUP${c.getAutoPartID()}"><i class="fas fa-eye"></i></button>
                                                    <button style="margin-left: 30px;" class="btn btn-primary show" type="button" title="show" id="show-emp" data-toggle="modal"
                                                            data-target="#ModalEDIT${c.getAutoPartID()}"><i class="fas fa-edit"></i></button>        
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
                                                    <a href="autopart?index=${index -1}&name=${name}&quantity=${quantity}"class="page-link">Lùi</a>
                                                </li>

                                                <c:forEach begin="1" end="${endPage}" var="i">

                                                    <li class="paginate_button page-item ${index == i?"active":""}">
                                                        <a href="autopart?index=${i}&name=${name}&quantity=${quantity}"class="page-link">${i}</a>
                                                    </li>

                                                </c:forEach>

                                                <li class="paginate_button page-item next <c:if test="${index == endPage}">disabled</c:if>" id="sampleTable_next">
                                                    <a href="autopart?index=${index +1}&name=${name}&quantity=${quantity}" class="page-link">Tiếp</a>
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
            <c:forEach var="c" items="${autoPartList}">

                <div class="modal fade" id="ModalUP${c.getAutoPartID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                     data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group  col-md-12">
                                        <span class="thong-tin-thanh-toan">
                                            <h5>Xem thông tin phụ tùng</h5>
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label class="control-label">Tên phụ tùng</label>
                                        <input class="form-control" readonly type="text" required value="${c.getPartName()}">
                                    </div>
                                    <div class="form-group  col-md-6">
                                        <label class="control-label">Trạng thái</label>
                                        <input class="form-control" readonly type="text" required value="${c.isStatus()?"Còn sản phẩm":"Hết sản phẩm"}">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Giá bán</label>
                                        <input class="form-control" readonly type="text" value="${c.getPrice()}" name="price">
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
                                </div>
                                <BR>
                                <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
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
            <c:forEach var="c" items="${autoPartList}">                
                <form  action="autopart" method="POST">

                    <div class="modal fade" id="ModalEDIT${c.getAutoPartID()}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                         data-keyboard="false">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group  col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5>Chỉnh sửa thông tin phụ tùng</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <input type="hidden" name="autoPartID" value="${c.getAutoPartID()}" />
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Tên phụ tùng</label>
                                            <input class="form-control" readonly type="text" required value="${c.getPartName()}">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Tình Trạng</label>
                                            <select class="form-control" name="Status">
                                                <option value="${c.isStatus()}">${c.isStatus()?"Còn sản phẩm":"Hết sản phẩm"}</option>
                                                <option value="${!c.isStatus()}">${!c.isStatus()?"Còn sản phẩm":"Hết sản phẩm"}</option>
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
                                            <input class="form-control" type="text" required value="${c.getCreatedOn()}" readonly style="cursor: not-allowed">
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

                                    </div>
                                    <BR>
                                    <a href="partcrud?action=update&autoPartID=${c.getAutoPartID()}" 
                                       style="float: right;
                                       font-weight: 600;
                                       color: #ea0000;">Chỉnh sửa sản phẩm nâng cao</a>
                                    <BR>
                                    <BR>
                                    <button class="btn btn-save" type="submit">Lưu lại</button>
                                    <!-- send input action to servlet to know what action need -->
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

            <!--            <script>
                                                    jQuery(function () {
                                                        jQuery(".trash").click(function () {
                                                            var autoPartId = $(this).data("autopartid");// Assuming you're storing autoPartID as a data attribute
                                                            swal({
                                                                title: "Cảnh báo",
                                                                text: "Bạn có chắc chắn là muốn xóa sản phẩm này?",
                                                                buttons: ["Hủy bỏ", "Đồng ý"],
                                                            })
                                                                    .then((willDelete) => {
                                                                        if (willDelete) {
                                                                            // Redirect to your servlet first
                                                                            window.location.href = 'partcrud?autoPartID=' + autoPartId + '&action=partdel';
                                                                            var deleteSuccess = 'true';
                                                                            if (deleteSuccess === 'true') {
                                                                                swal("Đã xóa thành công!", {
                                                                                    icon: "success",
                                                                                });
                                                                            } else {
                                                                                swal("Xóa thất bại!", {
                                                                                    icon: "error",
                                                                                });
                                                                            }
                                                                        }
                                                                    });
                                                        });
                                                    });
                        </script>-->
            <!--            <script>
                            jQuery(function () {
                                jQuery(".edit").click(function () {
                                    var autoPartId = $(this).data("autopartid");// Assuming you're storing autoPartID as a data attribute
                                    swal({
                                        title: "Thông báo",
                                        text: "Bạn muốn sửa sản phẩm này?",
                                        buttons: ["Hủy bỏ", "Đồng ý"],
                                    })
                                            .then((willDelete) => {
                                                if (willDelete) {
                                                    // Redirect to your servlet first
                                                    window.location.href = 'partcrud?autoPartID=' + autoPartId + '&action=update';
                                                }
                                            });
                                });
                            });
                        </script>-->
            <!--            <script>
                            function sendSelectedautoPartID() {
                                // Create a hidden input element to add an attribute
                                var hiddenInput = document.createElement("input");
                                hiddenInput.type = "hidden";
                                hiddenInput.name = "action"; // Change to the desired attribute name
                                hiddenInput.value = "deleteAllByautoPartID"; // Change to the desired attribute value
            
                                // Append the hidden input element to the form
                                var form = document.getElementById('SendAllServiceIDChecked');
                                form.appendChild(hiddenInput);
            
                                // Submit the form
                                form.submit();
                            }
                        </script>-->

    </body>

</html>